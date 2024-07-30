package com.shawood;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream = env
                .fromElements("Apache", "DROP", "Flink", "IGNORE","DROP", "Flink", "IGNORE")
                .keyBy(x->x);
        dataStream.print();
        env.execute();
    }
}
