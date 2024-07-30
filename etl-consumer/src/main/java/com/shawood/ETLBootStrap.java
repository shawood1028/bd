package com.shawood;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class ETLBootStrap
{
    public static void main( String[] args ) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取kafka参数
        Properties properties = new Properties();
        // kafka test环境地址
        properties.setProperty("bootstrap.servers", "172.31.12.22:9092");
        properties.setProperty("group.id", "test-shawood-flink");
        // 设置从最早的offset读取
        properties.setProperty("auto.offset.reset","earliest");
        String inputTopic = "events-client-topic";

        // kafka source
        DataStreamSource<String> kafkaData = env.addSource(new FlinkKafkaConsumer<>(inputTopic, new SimpleStringSchema(), properties)).setParallelism(1);

        // 打印获取的数据
        kafkaData.print().setParallelism(1);

        env.execute();
    }
}
