package org.example.kafka.producer;

import org.apache.flink.streaming.api.datastream.DataStream;

public class KafkaProducerDemo {
    public static void main(String[] args) {
    }

    public static class Person {
        public String name;
        public Integer age;
        public Person() {}

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return this.name.toString() + ": age " + this.age.toString();
        }
    }
}
