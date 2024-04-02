package org.example;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Random;

/**
 * DataStream API 简单示例1
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 已有数据
        DataStream<Person> persons = env.fromData(
                new Person("Fred", 35),
                new Person("Wilma", 18),
                new Person("Bob", 2),
                new Person().genPerson()
                );

        DataStream<Person> adults = persons.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.age >=18;
            }
        });

        adults.print();

        env.execute();
    }

    public static class Person {
        public String name;
        public Integer age;
        public Person() {}

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Person genPerson() {
            Person p = new Person("aaaa", (int)(Math.random()*100+18));
            System.out.println(p);
            return p;
        }

        public String toString() {
            return this.name + ": age" + this.age.toString();
        }
    }


}
