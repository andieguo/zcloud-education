package com.education.experiment.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.education.experiment.commons.Constants;

public class WeatherDataGenUtil {

	// 2012-01-01 Temp(max:8.16℃/min:-5.34℃);Humidity(0.68%);WSP(22.61m/s)
	// 1、为每一年生成一个文件
	// 2、每个文件中的一条记录是一天
	// 3、温度随机值范围：Temp（max:-10.0-37.0）(min:-10.0-37.0) max > min 保留3位小数
	// 4、湿度随机值范围：Humidity(0.00%-100.00%) 保留3位小数
	// 5、风速随机值范围：WSP(0.00m/s-50.00m/s) 保留3为小数
	public static void genWeatherDate(int startyear,int endyear) {
		try {
			for(int year=startyear;year<=endyear;year++){
				//生成文件夹
				File weather = new File(Constants.PROJECTPATH + File.separator + "weather");
				if (!weather.exists()) weather.mkdir();
				//默认为UTF-8格式
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(weather + File.separator + "weatherdata-" + year+".txt"), false));//覆盖
				//生成日期
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				calendar.set(year, 0, 0);
				//生成随机数
				Random random = new Random();
				for (int i = 0; i < 365; i++) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					double humidity = random.nextDouble()*100;//[0-100]
					double wsp = random.nextDouble()*50;//[0-50]
					double tempMax = random.nextDouble()*47-10;//[-10,37]
					double tempMin = random.nextDouble()*47-10;//[-10,37]
					if(tempMax < tempMin){//交换
						double temperature = tempMin;
						tempMax = temperature;
						tempMin = tempMax;
					}
					String data = MessageFormat.format("{0} Temp(max:{1}℃/min:{2}℃);Humidity({3}%);WSP({4}m/s)", 
							simpleDateFormat.format(calendar.getTime()),tempMax,tempMin,humidity,wsp);
					writer.write(data);
					writer.newLine();
				}
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		genWeatherDate(2009,2015);
	}
}
