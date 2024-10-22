/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shawood;

import com.shawood.utils.User;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class KafkaSourceDemo {

	public static void main(String[] args) throws Exception {

//		System.out.println("开始远程提交 Kafka Source Demo ...");
//		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment(
//				"localhost",8081,"/Users/apple/shawood/github/flink/flink-kafka-source/target/flink-kafka-source-1.0-SNAPSHOT.jar");
//
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setParallelism(1);
		// gen source
		GeneratorFunction<Long,String> generatorFunction = l -> {
            User user = new User();
            ObjectMapper mapper = new ObjectMapper();
            // 序列化
            String json = mapper.writeValueAsString(user.genUser());
            return json;
        };

		long numberOfRecord = 10000;

		DataGeneratorSource<String> source = new DataGeneratorSource<>(generatorFunction,numberOfRecord, RateLimiterStrategy.perSecond(100), Types.STRING);

		DataStreamSource<String> sourceStream = env.fromSource(source, WatermarkStrategy.noWatermarks(),"DATAGEN random user");

		sourceStream.print();
		KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
				.setBootstrapServers("localhost:9192, localhost:9292, localhost:9392")
				.setRecordSerializer(KafkaRecordSerializationSchema.builder()
						.setTopic("test-input-topic")
						.setValueSerializationSchema(new SimpleStringSchema())
						.build()
				)
				.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
				.build();

		sourceStream.sinkTo(kafkaSink);

		// 设置kafka参数
//		String kafkaBroker = "localhost:9192,localhost:9292,localhost:9392";
/*		String kafkaBroker = "localhost:9192";
		String kafkaSinkTopic = "test-input-topic";
		KafkaSink<String> sink = KafkaSink.<String>builder()
				.setBootstrapServers(kafkaBroker)
				.setRecordSerializer(KafkaRecordSerializationSchema.builder()
						.setTopic(kafkaSinkTopic)
						.setValueSerializationSchema(new SimpleStringSchema())
						.build()
				)
				.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
				.build();
		sourceStream.sinkTo(sink);*/
		env.execute("Flink Kafka Source Demo");
	}
}
