package com.shawood.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class App {
    public static void main(String[] args) {
        System.out.println("开始远程提交...");
        try{
            System.out.println("开始远程提交 Kafka Source ...");
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment(
                    "localhost",8081,"/Users/apple/shawood/github/flink/flink-kafka-source/target/flink-kafka-source-1.0-SNAPSHOT.jar");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("提交成功");
        }

        try{
            System.out.println("开始远程提交 Kafka Sink ...");
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment(
                    "localhost",8081,"/Users/apple/shawood/github/flink/flink-kafka-sink/target/flink-kafka-sink-1.0-SNAPSHOT.jar");
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("提交成功");
        }

    }
}