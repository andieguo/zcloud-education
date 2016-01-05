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
	public static final String INDEXES_PATH =  PROJECTPATH+File.separator+"indexes";
	/**
	 * eg. /home/hadoop/zcloud-education/indexes/index
	 */
	public static final String IDNEX_PATH = INDEXES_PATH + File.separator+"index";
	/**
	 * eg. /home/hadoop/zcloud-education/indexes/tmp
	 */
	public static final String INDEX_TMP_PATH = INDEXES_PATH + File.separator+"tmp";
	/**
	 * eg. /tomcat/experiment/librarycloud/indexes/
	 */
	public static final String HDFS_BOOK_INDEX = "/tomcat/experiment/librarycloud/indexes/";
	/**
	 * eg. /tomcat/experiment/librarycloud/uploaddata/
	 */
	public static final String HDFS_BOOK_HOME = "/tomcat/experiment/librarycloud/uploaddata/";
	/**
	 * eg. /home/hadoop/zcloud-education/education.jar
	 */
	public static final String JAR_HOME = PROJECTPATH + File.separator + "education.jar";;
	
	static{
		mkdir(PROJECTPATH);
		mkdir(INDEXES_PATH);
		mkdir(IDNEX_PATH);
		mkdir(INDEX_TMP_PATH);
	} 
	
	public static void mkdir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
