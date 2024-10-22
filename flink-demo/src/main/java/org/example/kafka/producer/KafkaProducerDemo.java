package org.example.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaProducerDemo {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerDemo.class);

    public static void main(String[] args) throws Exception {
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /*
         * 创建远程环境，远程提交flink任务。
        */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 数据生成器source
        GeneratorFunction<Long, String> generatorFunction = new GeneratorFunction<Long, String>() {
            @Override
            public String map(Long aLong) throws Exception {
                User user = new User();
                ObjectMapper mapper = new ObjectMapper();
                // 序列化
                String json = mapper.writeValueAsString(user.genUser());
                return json;
            }
        };
        long numberOfRecord = 10000;

        DataGeneratorSource<String> source = new DataGeneratorSource<>(generatorFunction, numberOfRecord, RateLimiterStrategy.perSecond(100), Types.STRING);

        DataStreamSource<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Generator Source");

        // 设置kafka参数
        String kafkaBroker = "localhost:9192";
        String kafkaSinkTopic = "test-input-topic";
        KafkaSink<String> sink = KafkaSink.<String>builder()
                .setBootstrapServers(kafkaBroker)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(kafkaSinkTopic)
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
//                .setProperty("transaction.timeout.ms",1000*60*5+"")
                .build();
        stream.print();
        stream.sinkTo(sink);
        env.execute();
    }
}
