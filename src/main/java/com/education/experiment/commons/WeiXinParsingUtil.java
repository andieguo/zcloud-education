package com.education.experiment.commons;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.education.experiment.cloudwechat.WeixinParsingBean;
import com.education.experiment.cloudwechat.WeixinUserBean;

public class WeiXinParsingUtil {
	
	public static Map<String, WeixinParsingBean> genParsingMap(FileSystem fs){
		Map<String, WeixinParsingBean> parsingMap = new HashMap<String, WeixinParsingBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 开始读取分析数据文件
			FileStatus[] uploadparsing = fs.listStatus(new Path("/tomcat/experiment/weixincloud/uploadparsing"));
			for (FileStatus ele : uploadparsing) {
				FSDataInputStream fsdis = fs.open(ele.getPath());
				String fileName = ele.getPath().getName().split("\\.")[0];
				BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
				String line = null;
				// 以下数据为上传的分析条件数据
				// timePoint:2013-03-05 13:57:40 durationTime:56
				// gender:男男
				// isFriend:否 ageSpan:22至22 vocation:null
				// communicationPlace:北京市海淀区，北京市朝阳区 keywords:我爱你
				try {
					while ((line = br.readLine()) != null) {
						WeixinParsingBean wpb = new WeixinParsingBean();
						String[] original = line.split("\t");
						if (original.length == 8) {
							if (!original[0].contains("null")) {
								wpb.setTimePoint(sdf.parse(original[0].substring(original[0].indexOf(":") + 1)).getTime());
							}

							if (!original[1].split(":")[1].equals("null")) {
								wpb.setDurationTime(Integer.parseInt(original[1].split(":")[1]));
							}

							String gender = original[2].split(":")[1];
							if (!gender.equals("null")) {
								if (gender != null) {
									if (gender.equals("男男")) {
										wpb.setGender("11");
									} else if (gender.equals("女女")) {
										wpb.setGender("00");
									} else if (gender.equals("异性")) {
										wpb.setGender("01");
									} else {
										wpb.setGender("ALL");
									}
								}
							}

							if (!original[3].split(":")[1].equals("null")) {
								if (original[3].split(":")[1].equals("是")) {
									wpb.setFriend("true");
								} else if (original[3].split(":")[1].equals("否")) {
									wpb.setFriend("false");
								} else {
									wpb.setFriend("ALL");
								}
							}

							String ageSpan = original[4].split(":")[1];
							if (!ageSpan.equals("null")) {
								wpb.setMinAge(Integer.parseInt(ageSpan.split("至")[0]));
								wpb.setMaxAge(Integer.parseInt(ageSpan.split("至")[1]));
							}

							if (!"null".equals(original[5].split(":")[1])) {
								wpb.setVocations(original[5].split(":")[1]);
							}
							if (!"null".equals(original[6].split(":")[1])) {
								wpb.setCommunicationPlace(original[6].split(":")[1].split(","));
							}
							if (!"null".equals(original[7].split(":")[1])) {
								wpb.setKeywords(original[7].split(":")[1].split(","));
							}
						}
						parsingMap.put(fileName, wpb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				fsdis.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsingMap;
	}

	public static Map<String, WeixinUserBean> genUserMap(FileSystem fs){
		Map<String, WeixinUserBean> weixinuserMap = new HashMap<String, WeixinUserBean>();
		// 读取数据库的微信用户信息
		Path path = new Path("/tomcat/experiment/weixincloud/tmp/weixin_info.mysql");
		try {
			FSDataInputStream fsdis = fs.open(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split("\t");
				try {
					if (array.length == 6) {
						WeixinUserBean wub = new WeixinUserBean();
						wub.setId(Integer.valueOf(array[0]));
						wub.setName(array[1]);
						wub.setAge(Integer.valueOf(array[2]));
						wub.setSex(array[3]);
						wub.setVocation(array[4]);
						wub.setFriends(array[5]);
						weixinuserMap.put(array[0], wub);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return weixinuserMap;
	}
	
	public static void generateUserFile() {
		try {
			Connection conn = BaseDao.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from WEIXIN_INFO");
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append(rs.getInt(1) + "\t");
				sb.append(rs.getString(2) + "\t");
				sb.append(rs.getInt(3) + "\t");
				if (rs.getInt(4) == 1) {
					sb.append("男" + "\t");
				} else {
					sb.append("女" + "\t");
				}
				sb.append(rs.getString(5) + "\t");
				sb.append(rs.getString(6) + "\t");
				sb.append("\n");
			}
			st.close();
			rs.close();
			conn.close();
			//读取mysql数据并保存到本地的weixin_info.mysql文件
			File temp = new File(System.getProperty("user.home") + File.separator + "temp");
			if (!temp.exists())
				temp.mkdir();
			File newFile = new File(temp+File.separator+"weixin_info.mysql");
			if (!newFile.exists())
				newFile.delete();
			OutputStream output = new FileOutputStream(newFile);
			OutputStreamWriter writer = new OutputStreamWriter(output,"UTF-8");
			writer.write(sb.toString());
			writer.close();
			//上传本地的weixin_info.mysql文件到服务器HDFS上
			InputStream in = new BufferedInputStream(new FileInputStream(newFile));
			Configuration conf = HadoopConfiguration.getConfiguration();
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path("/tomcat/experiment/weixincloud/tmp/weixin_info.mysql");
			if (hdfs.exists(path)) {
				hdfs.delete(path, true);
			}
			FSDataOutputStream out = hdfs.create(path);
			IOUtils.copyBytes(in, out, 4096, true);
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
			newFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String,Boolean> parsing(Map<String, WeixinParsingBean> parsingMap,Map<String, WeixinUserBean> weixinuserMap,String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		String[] array = value.split("\t");
		if (array.length == 5) {
			try {
				// 开始对每行数据进行逐条分析，看是不是满足用户定义的条件信息
				long beginTime = sdf.parse(array[1]).getTime();
				long endTime = sdf.parse(array[2]).getTime();
				String[] ids = array[0].substring(array[0].indexOf("{") + 1, array[0].indexOf("}")).split(",");
				if (ids.length == 2) {
					for (Map.Entry<String, WeixinParsingBean> me : parsingMap.entrySet()) {
						if (me.getValue().getTimePoint() != 0L) {
							if (beginTime < me.getValue().getTimePoint()) {
								continue;
							}
						}
						if (me.getValue().getDurationTime() != 0) {
							if (endTime - beginTime < 1000L * me.getValue().getDurationTime() * 60) {
								continue;
							}
						}
						if (me.getValue().getCommunicationPlace() != null) {
							boolean pass = false;
							for (String ele : me.getValue().getCommunicationPlace()) {
								if (array[3].trim().contains(ele.trim())) {
									pass = true;
									break;
								}
							}
							if (!pass) {
								continue;
							}
						}
						if (me.getValue().getKeywords() != null) {
							boolean nopass = false;
							for (String ele : me.getValue().getKeywords()) {
								if (!array[4].contains(ele)) {
									nopass = true;
									break;
								}
							}
							if (nopass) {
								continue;
							}
						}
						if (!(me.getValue().getGender().equals("ALL"))) {
							if (me.getValue().getGender().equals("00")) {
								if (!(weixinuserMap.get(ids[0]).getSex().equals("女"))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getSex().equals("女"))) {
									continue;
								}
							} else if (me.getValue().getGender().equals("11")) {
								if (!(weixinuserMap.get(ids[0]).getSex().equals("男"))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getSex().equals("男"))) {
									continue;
								}
							} else if (me.getValue().getGender().equals("01")) {
								if (weixinuserMap.get(ids[0]).getSex().equals(weixinuserMap.get(ids[1]).getSex())) {
									continue;
								}
							}
						}
						if (!(me.getValue().getFriend().equals("ALL"))) {
							if (me.getValue().getFriend().equals("true")) {
								if (!(weixinuserMap.get(ids[0]).getFriends().contains(ids[1]))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getFriends().contains(ids[0]))) {
									continue;
								}
							} else if (me.getValue().getFriend().equals("false")) {
								if (weixinuserMap.get(ids[0]).getFriends().contains(ids[1]) && weixinuserMap.get(ids[1]).getFriends().contains(ids[0])) {
									continue;
								}
							}
						}
						if (me.getValue().getMinAge() > 0 && me.getValue().getMaxAge() > 0) {
							if (weixinuserMap.get(ids[0]).getAge() < me.getValue().getMinAge() || weixinuserMap.get(ids[0]).getAge() > me.getValue().getMaxAge()) {
								continue;
							}
							if (weixinuserMap.get(ids[1]).getAge() < me.getValue().getMinAge() || weixinuserMap.get(ids[1]).getAge() > me.getValue().getMaxAge()) {
								continue;
							}
						}
						if (me.getValue().getVocations() != null) {
							if (!(me.getValue().getVocations().contains(weixinuserMap.get(ids[0]).getVocation()))) {
								continue;
							}
							if (!(me.getValue().getVocations().contains(weixinuserMap.get(ids[1]).getVocation()))) {
								continue;
							}
						}
						map.put(me.getKey(), true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
