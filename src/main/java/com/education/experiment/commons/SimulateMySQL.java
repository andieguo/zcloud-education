package com.education.experiment.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SimulateMySQL {
	private static SimulateMySQL instance;
	private Map<String, UserBean> users = new HashMap<String, UserBean>();
	private final File file = new File(SimulateMySQL.class.getResource("/").getPath() + "mysql.db");

	private SimulateMySQL() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				if (split.length == 6) {
					UserBean ub = new UserBean();
					ub.setUserId(split[0]);
					ub.setUserPassWd(split[1]);
					ub.setUserName(split[2]);
					ub.setCloudSize(Integer.parseInt(split[3]));
					ub.setPhoneNumber(split[4]);
					ub.setRemark(split[5]);
					users.put(ub.getUserId(), ub);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized static SimulateMySQL getInstance() {
		System.out.println(Thread.currentThread().getName());
		if (instance == null) {
			instance = new SimulateMySQL();
		}
		return instance;
	}

	public synchronized void writeIntoMySQL(UserBean ub) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			String content = "\n" + ub.getUserId() + "|";
			content += ub.getUserPassWd() + "|";
			content += ub.getUserName() + "|";
			content += ub.getCloudSize() + "|";
			content += ub.getPhoneNumber() + "|";
			content += ub.getRemark();
			bw.write(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Map<String, UserBean> getUsers() {
		return this.users;
	}

	public static void main(String[] args) {
		SimulateMySQL sm = SimulateMySQL.getInstance();
		System.out.println(System.getProperty("file.encoding"));
		System.out.println(sm.getUsers().size());
		for (Map.Entry<String, UserBean> me : sm.getUsers().entrySet()) {
			System.out.println(me.getKey() + "\t" + me.getValue());
		}
	}
}
