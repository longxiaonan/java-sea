package com.iee.geogMap;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * @ClassName: aa
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年11月16日 下午12:05:38
 * @version 1.0
 */
public class MapUtils {
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String latitude = "30.548397";
		String longitude = "104.04701";
		getAddrByLatitudeAndLongitude(latitude, longitude);
		getLatiAndLongiByAddr("中国成都人才市场");
		getBD09ByWgs();

	}

	/** 大地坐标转百度坐标
	 * @throws IOException
	 * @throws UnsupportedEncodingException */
	public static void getBD09ByWgs() throws UnsupportedEncodingException, IOException{
		URL tirc = new URL("http://api.map.baidu.com/geoconv/v1/?coords=114.391512,30.453084&from=1&to=5&ak="+MapConst.key);
		JSONObject jsonObject = sendRequest(tirc);
		System.out.println(jsonObject);
	}

	/** 地址转经纬度 */
	private static void getLatiAndLongiByAddr(String addr) {
		MapConst key = MapConst.key;
		try {
			URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + addr + "&output=json&ak=" + key);
			JSONObject jsonObj = sendRequest(tirc);
			String location = jsonObj.getJSONObject("result").getString("location");
			System.out.println(location);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 经纬度转地址 */
	private static void getAddrByLatitudeAndLongitude(String latitude, String longitude) {
		BufferedReader in = null;
		// 30.548397,104.04701
		MapConst key = MapConst.key;
		String location = latitude + "," + longitude;
		try {
			URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?ak=" + key + "&location=" + location
					+ "&output=json&pois=1");
			JSONObject jsonObject = sendRequest(tirc);
			String addr = jsonObject.getJSONObject("result").getString("formatted_address");
			System.out.println(addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static JSONObject sendRequest(URL tirc) throws UnsupportedEncodingException, IOException {
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res.trim());
		}
		String str = sb.toString();
		System.out.println(str);
		JSONObject jsonObj = JSONObject.parseObject(str);
		return jsonObj;
	}
}
