package com.education.experiment.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
	
	public static String doRest(String type, String surl, String data) throws Exception {
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		URL url = new URL(surl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(5000);
		connection.setRequestMethod(type);
		connection.setRequestProperty("ContentType", "text;charset=utf-8");
		/*if (xkey != null) {
			connection.setRequestProperty("X-ApiKey", xkey);
		}*/
		if (data != null) {
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes("utf-8"));// 写入data信息
		}
		connection.setDoInput(true);
		in = new InputStreamReader(connection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(in);
		StringBuffer strBuffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			strBuffer.append(line);
		}
		connection.disconnect();
		return strBuffer.toString();
	}
	
}
