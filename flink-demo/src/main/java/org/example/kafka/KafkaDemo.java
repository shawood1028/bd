package org.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.kafka.producer.User;

public class KafkaDemo {
    public static void main(String[] args) throws Exception {
        /*
         * 创建远程环境，远程提交flink任务。
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9192")
                .setTopics("test-input-topic")
                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        DataStream<User> dataStream = env.fromSource(kafkaSource,WatermarkStrategy.noWatermarks(), "Kafka Source")
                .map(new MapFunction<String, User>() {
                    @Override
                    public User map(String s) throws Exception {
                        ObjectMapper objectMapper = new ObjectMapper();
                        User user = objectMapper.readValue(s, User.class);
                        return user;
                    }
                });
        dataStream.print();

        env.execute();
    }
}
