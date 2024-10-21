package org.example.demos.topn;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TopN {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.execute();
    }
}
