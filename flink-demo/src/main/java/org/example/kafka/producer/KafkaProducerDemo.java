package org.example.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class KafkaProducerDemo {
    public static void main(String[] args) throws Exception {
        // 自定义flink source
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
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
        long numberOfRecord = 10000000;

        DataGeneratorSource<String> source = new DataGeneratorSource<>(generatorFunction, numberOfRecord, RateLimiterStrategy.perSecond(10), Types.STRING);

        DataStreamSource<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Generator Source");

        stream.print();
        env.execute();
    }
}
