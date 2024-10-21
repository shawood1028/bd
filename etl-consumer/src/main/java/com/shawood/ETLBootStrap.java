package com.shawood;

import akka.stream.Client;
import com.shawood.utils.ClientData;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
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

        kafkaData.print();
        // 打印获取的数据
        DataStream<String> dataStream = kafkaData.map(new MapFunction<String, String>() {
                    @Override
                    public String map(String s) throws Exception {
                        return s.replace("$", "");
                    }
                }).keyBy(new KeySelector<String, Object>() {
                    @Override
                    public Object getKey(String s) throws Exception {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
                        ClientData clientData = new ClientData();
                        clientData = objectMapper.readValue(s, ClientData.class);
                        return clientData.event;
                    }
                })
                .reduce(new ReduceFunction<String>() {
            @Override
            public String reduce(String s, String t1) throws Exception {
                System.out.println(s);
                return s;
            }
        });
        dataStream.print();

        env.execute();
    }
}
