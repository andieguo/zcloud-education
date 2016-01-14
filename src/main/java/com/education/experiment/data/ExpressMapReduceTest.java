package com.education.experiment.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.cloudexpress.GPRSBean;
import com.education.experiment.commons.HadoopConfiguration;

import junit.framework.TestCase;

public class ExpressMapReduceTest extends TestCase{
	
	public void updateGPS(String userid,String longitude,String latitude){
		try {
			FileSystem hdfs = FileSystem.get(HadoopConfiguration.getConfigurationJava());
			Path path = new Path("/tomcat/experiment/expresscloud/courier/courier.txt");
			if (hdfs.exists(path)) {
				FSDataInputStream fsdis = hdfs.open(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
				String line = null;
				StringBuffer sb = new StringBuffer();
				boolean sign = false;
				while ((line = br.readLine()) != null) {
					String[] array = line.split("\t");
					if (!sign && array[0].equals(userid)) {
						sb.append(array[0] + "\t" + longitude + "," + latitude + "\n");
						sign = true;
					} else {
						sb.append(line + "\n");
					}
				}
				if (!sign) {
					sb.append(userid + "\t" + longitude + "," + latitude + "\n");
				}
				fsdis.close();
				hdfs.delete(path, true);
				FSDataOutputStream hdfsOut = hdfs.create(path);
				hdfsOut.write(sb.toString().getBytes());
				hdfsOut.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, GPRSBean> mapInit(){
		Map<String, GPRSBean> map = new HashMap<String, GPRSBean>();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(HadoopConfiguration.getConfigurationJava());
			if (map.size() == 0) {
				Path path = new Path("/tomcat/experiment/expresscloud/courier/courier.txt");
				FSDataInputStream fsdis = fs.open(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] array = line.split("\t");
					try {
						if (array.length == 2) {
							GPRSBean gprsb = new GPRSBean();
							gprsb.setLongitude(Float.parseFloat((array[1].split(",")[0])));
							gprsb.setLatitude(Float.parseFloat((array[1].split(",")[1])));
							map.put(array[0], gprsb);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			for(String key : map.keySet()){
				System.out.println(key);
				System.out.println(map.get(key));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return map;
	}
	
	public void testMapInit(){
		mapInit();
	}
	
	public void testUpdateGPS(){
		updateGPS("andy", "115.293", "23.222");
	}
	
	public void testMap(){
	    Map<String, ArrayList<String>> collect = new HashMap<String, ArrayList<String>>();
		Map<String, GPRSBean> map = mapInit();
		BufferedReader reader = null;
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("expressdata.txt");
		try {
			//使用本地环境中的默认字符集，例如在中文环境中将使用 GBK编码
			reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			String line = null;
			// 一次读一行，读入null时文件结束
			while ((line = reader.readLine()) != null) {
				// 把当前行号显示出来
				String[] array = line.toString().split("\t");
				//2013-02-09 17:56:57	北京市东郊火车站	经纬度:25.98128,57.90178	备注:　　如果是周六日，请送到保安处，在三楼305。
				if (array.length == 4) {
					String[] ll = array[2].substring(array[2].indexOf("经纬度:") + "经纬度:".length()).split(",");
					float longitude = Float.parseFloat(ll[0]);
					float latitude = Float.parseFloat(ll[1]);
					String result = null;
					double distance = -1;
					for (Map.Entry<String, GPRSBean> me : map.entrySet()) {
						if (distance == -1) {
							distance = Math.sqrt(Math.pow((longitude - me.getValue().getLongitude()), 2) + Math.pow(latitude - me.getValue().getLatitude(), 2));
							result = me.getKey();
						} else if (Math.sqrt(Math.pow((longitude - me.getValue().getLongitude()), 2) + Math.pow(latitude - me.getValue().getLatitude(), 2)) < distance) {
							result = me.getKey();
						}
					}
					if (collect.containsKey(result)) {
						collect.get(result).add(array[1] + "\t" + array[3]);
					} else {
						collect.put(result, new ArrayList<String>());
						collect.get(result).add(array[1] + "\t" + array[3]);
					}
				}
			}
			for(String key : collect.keySet()){
				List<String> v = collect.get(key);
				System.out.println(v.size());
				for(String k : v){
					System.out.println(k);
				}
				System.out.println("=========================================================");
			}		
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) input.close();
				if (reader != null) reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
