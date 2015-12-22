package com.education.experiment.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class WinxinUserImportUtil {

	public static void main(String[] args) {
		readFileByLines("src/weixindata.txt");
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		ListMultimap<Integer,Integer> myMultimap = ArrayListMultimap.create();  
		BaseDao baseDao = new BaseDao();
		System.out.println("connection:" + BaseDao.getConnection());
		try {
			System.out.println("以行为单位读取文件内容，一次读一行");
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			// 一次读一行，读入null时文件结束
			while ((line = reader.readLine()) != null) {
				// 把当前行号显示出来
				String[] ids = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");
				myMultimap.put(Integer.valueOf(ids[0]), Integer.valueOf(ids[1]));
			}
			Random random = new Random();
			int[] age = new int[] { 20, 21, 22, 23, 19, 18, 17, 16, 25, 24 };// 0-9
			int[] sex = new int[] { 0, 1 };// 0-1
			String[] vocations = new String[] { "餐饮", "教育", "金融", "律师", "娱乐", "军人", "体育", "建筑", "无业" };// 0-8
			for(int i=10001;i<=10100;i++){
				baseDao.executeNonQuery("insert into WEIXIN_INFO(ID,NAME,AGE,SEX,VOCATION,RELATION) values (?,?,?,?,?,?)", new Object[]{i,"andy"+i,age[random.nextInt(10)],sex[random.nextInt(2)],vocations[random.nextInt(9)],myMultimap.get(i).toString()});
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
