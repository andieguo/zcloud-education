package com.education.experiment.commons;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;

import com.education.experiment.util.PropertiesUtil;

public class HadoopConfiguration {
	private static final Configuration conf = new Configuration();

	public static synchronized Configuration getConfiguration() {
		Properties properties = PropertiesUtil.loadFromInputStream(BaseDao.class.getClassLoader().getResourceAsStream("/config.properties"));
		String fsHostName = properties.getProperty("fs.default.name.hostname");
		String fsPort = properties.getProperty("fs.default.name.port");
		String jobPort = properties.getProperty("mapred.job.tracker.port");
		conf.set("fs.default.name", "hdfs://"+fsHostName+":"+fsPort);
		conf.set("mapred.job.tracker", "http://"+fsHostName+":"+jobPort);
		return conf;
	}
}
