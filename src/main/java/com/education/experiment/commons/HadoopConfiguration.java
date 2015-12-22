package com.education.experiment.commons;

import org.apache.hadoop.conf.Configuration;

public class HadoopConfiguration {
	private static final Configuration conf = new Configuration();

	public static synchronized Configuration getConfiguration() {
		conf.set("fs.default.name", "hdfs://192.168.100.141:9000");
		conf.set("mapred.job.tracker", "http://192.168.100.141:9001");
		return conf;
	}
}
