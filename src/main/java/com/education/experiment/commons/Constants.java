package com.education.experiment.commons;

import java.io.File;

public class Constants {
	public static final String HOMEPATH = System.getProperty("user.home");
	public static final String PROJECTNAME = "zcloud-education";
	/**
	 * eg. /home/hadoop/zcloud-education
	 */
	public static final String PROJECTPATH = HOMEPATH+File.separator+PROJECTNAME;
	public static final String WEATHER_PATH = PROJECTPATH+File.separator+"weather";
	public static final String BOOK_PATH =  PROJECTPATH+File.separator+"book";
	public static final String WEIXIN_PATH =  PROJECTPATH+File.separator+"weixin";
	public static final String LOCAL_BOOK_INDEXES =  PROJECTPATH+File.separator+"indexes";
	/**
	 * eg. /home/hadoop/zcloud-education/indexes/index
	 */
	public static final String LOCAL_BOOK_IDNEX = LOCAL_BOOK_INDEXES + File.separator+"index";
	/**
	 * eg. /home/hadoop/zcloud-education/indexes/tmp
	 */
	public static final String LOCAL_BOOK_TMP = LOCAL_BOOK_INDEXES + File.separator+"tmp";
	/**
	 * eg. /tomcat/experiment/librarycloud/indexes/
	 */
	public static final String HDFS_BOOK_INDEXES = "/tomcat/experiment/librarycloud/indexes/";
	/**
	 * eg. /tomcat/experiment/librarycloud/uploaddata/
	 */
	public static final String HDFS_BOOK_UPLOADDATA = "/tomcat/experiment/librarycloud/uploaddata/";
	/**
	 * eg. /tomcat/experiment/weathercloud/uploaddata/
	 */
	public static final String HDFS_WEATHER_UPLOADDATA = "/tomcat/experiment/weathercloud/uploaddata/";
	/**
	 * eg. /tomcat/experiment/weathercloud/results/
	 */
	public static final String HDFS_WEATHER_RESULTS = "/tomcat/experiment/weathercloud/results/";
	/**
	 * eg. /tomcat/experiment/weixincloud/uploadparsing/
	 */
	public static final String HDFS_WEIXIN_UPLOADPARSING = "/tomcat/experiment/weixincloud/uploadparsing/";
	/**
	 * eg. /tomcat/experiment/weixincloud/uploaddata/
	 */
	public static final String HDFS_WEIXIN_UPLOADDATA = "/tomcat/experiment/weixincloud/uploaddata/";
	/**
	 * eg. /tomcat/experiment/weixincloud/uploaddata/
	 */
	public static final String HDFS_WEIXIN_RESULTS = "/tomcat/experiment/weixincloud/results/";
	/**
	 * eg. /tomcat/experiment/expresscloud/uploaddata/
	 */
	public static final String HDFS_EXPRESS_UPLOADDATA = "/tomcat/experiment/expresscloud/uploaddata/";
	/**
	 * eg. /home/hadoop/zcloud-education/education.jar
	 */
	public static final String JAR_HOME = PROJECTPATH + File.separator + "education.jar";
	/**
	 * eg. /tomcat/experiment/expresscloud/results/
	 */
	public static final String HDFS_EXPRESS_RESULTS = "/tomcat/experiment/expresscloud/results/";

	static{
		mkdir(PROJECTPATH);
		mkdir(LOCAL_BOOK_INDEXES);
		mkdir(LOCAL_BOOK_IDNEX);
		mkdir(LOCAL_BOOK_TMP);
	} 
	
	public static void mkdir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
