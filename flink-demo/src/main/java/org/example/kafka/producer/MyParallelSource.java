package org.example.kafka.producer;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class MyParallelSource implements SourceFunction<String> {
    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {

    }

    @Override
    public void cancel() {

    }
}
