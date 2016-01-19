package com.education.experiment.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.cloudexpress.GPRSBean;
import com.education.experiment.commons.HadoopConfiguration;

public class ExpressMapReduceTool {

	public static void updateGPS(String userid,String longitude,String latitude){
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

	public static Map<String, GPRSBean> mapInit(){
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
}
