package com.education.experiment.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.apache.hadoop.fs.FileSystem;

import com.education.experiment.cloudwechat.WeixinParsingBean;
import com.education.experiment.cloudwechat.WeixinUserBean;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.WeiXinParsingUtil;

public class WeiXinParsingTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			FileSystem fs = FileSystem.get(HadoopConfiguration.getConfiguration());
			Map<String, WeixinParsingBean> parsingMap = WeiXinParsingUtil.genParsingMap(fs);
			Map<String, WeixinUserBean> weixinuserMap = WeiXinParsingUtil.genUserMap(fs);
			BufferedReader reader = null;
			File file = new File("C:\\Users\\andieguo\\temp\\weixindata.txt");
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			// 一次读一行，读入null时文件结束
			while ((line = reader.readLine()) != null) {
				// 把当前行号显示出来
				Map<String,Boolean> maps = WeiXinParsingUtil.parsing(parsingMap, weixinuserMap, line);
				for(String key : maps.keySet()){
					if(maps.get(key)){
						System.out.println(line);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
