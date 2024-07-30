package com.shawood;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.cdc.connectors.mysql.source.MySqlSource;
import org.apache.flink.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Hello world!
 *
 */
public class MysqlCDC
{
    public static void main( String[] args ) throws Exception {
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("localhost")
                .port(3306)
                .databaseList("test-cdc")
                .tableList("test-cdc.t_table_01")
                .username("root")
                .password("abcd1234!")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("localhost",8081,"D:\\code\\github\\flink\\mysql2hive\\target\\mysql2hive-1.0-SNAPSHOT.jar");

        // enable checkpoint
        env.enableCheckpointing(3000);

        env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                // set 4 parallel source tasks
                .setParallelism(2)
                .print().setParallelism(1);

        env.execute("cdc-test");
    }
}
