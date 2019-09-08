package com.iee.geogMap;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 获取经纬度
 *
 * @author jueyue 返回格式：Map<String,Object> map map.put("status",
 *         reader.nextString());//状态 map.put("result", list);//查询结果 list<map
 *         <String,String>> 密钥:f247cdb592eb43ebac6ccd27f796e2d2
 */
public class TestMapBaidu {
	public static void main(String[] args) throws Exception {
		// getAddrByLatiAndLongi();
		getLatiAndLongiByAddr();
	}
	/**
	 * 地址转经纬度
	 * @throws Exception
	 */
	private static void getLatiAndLongiByAddr() throws Exception {
		String key = "03d371c5c9927a9522fc046a5ac08ea9";
		URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address=中国成都人才市场&output=json&ak="+key);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			System.out.println(str);
			JSONObject jsonObj = JSONObject.parseObject(str);
			String addr = jsonObj.getJSONObject("result").getString("location");
			System.out.println(addr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 经纬度转地址
	 * @throws MalformedURLException
	 */
	private static void getAddrByLatiAndLongi() throws MalformedURLException {
		BufferedReader in = null;
		// 30.548397,104.04701
		String latitude = "30.548397";
		String longitude = "104.04701";
		String key = "03d371c5c9927a9522fc046a5ac08ea9";
		URL tirc = new URL(
				"http://api.map.baidu.com/geocoder/v2/?ak=" + key + "&location=30.548397,104.04701&output=json&pois=1");
		try {
			in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			System.out.println(str);
			JSONObject jsonObj = JSONObject.parseObject(str);
			String addr = jsonObj.getJSONObject("result").getString("formatted_address");
			System.out.println(addr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
