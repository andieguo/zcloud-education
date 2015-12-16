
    #创建数据库
	CREATE DATABASE IF NOT EXISTS db_education CHARACTER SET utf8 COLLATE utf8_general_ci;
    
	#创建用户表
    CREATE TABLE USER_INFO (
    	ID VARCHAR(20) NOT NULL,
    	PASSWD VARCHAR(50) NOT NULL,
    	NAME VARCHAR(20) NOT NULL,
    	CLOUDSIZE BIGINT NOT NULL,
    	PHONENUM VARCHAR(20),
    	REMARK VARCHAR(200),
    	primary key(ID)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
    
	#创建WEIXIN表
    CREATE TABLE WEIXIN_INFO (
    	ID INT NOT NULL,
    	NAME VARCHAR(50) NOT NULL,
    	AGE INT NOT NULL,
    	SEX INT NOT NULL,
    	VOCATION VARCHAR(20),
    	RELATION VARCHAR(200),
    	primary key(ID)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
    
	#JDBC访问URL
    URL = "jdbc:mysql://localhost:3306/db_education?useUnicode=true&amp;characterEncoding=utf-8";

	#MVN构建项目
    mvn archetype:generate -DgroupId=com.andieguo.hadoop -DartifactId=zcloud-education -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false -Dar
    chetypeCatalog=local