package com.education.experiment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.education.experiment.commons.NoteBean;

public class HtmlParseUtil {

	public static NoteBean parse(InputStream input) throws IOException{
		NoteBean bean = new NoteBean();
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.dangdang.com");
		Element titleElement = doc.select("title").get(0);
		Element contentElement = doc.select("div").get(1);
		Element dateElement = doc.select("span").get(0);
		Element userElement = doc.select("span").get(1);
		bean.setContent(contentElement.html());
		bean.setTitle(titleElement.text());
		bean.setUser(userElement.text());
		bean.setDateContent(dateElement.text());
		System.out.println(bean);
		if(input != null) input.close();
		return bean;
	}
	
	public static void main(String[] args) {
		try {
			HtmlParseUtil.parse(new FileInputStream(new File("C:\\Users\\andieguo\\Downloads\\aaaaaaaaaaaaaa.html")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
