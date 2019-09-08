package com.iee.mongodb.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

public class DataObj {
	@Id
	private String id;

	protected JSONObject data;

	public JSONObject getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public static DataObj formJSONString(String jsonStr) {
		JSONObject obj = JSONObject.parseObject(jsonStr);
		DataObj data = new DataObj(obj);
		return data;
	}

	public DataObj offer(String key, Object obj) {
		this.data.put(key, obj);
		return this;
	}

	public void put(String key, Object obj) {
		this.data.put(key, obj);
	}

	public void setObjType(int type) {
		data.put("OBJTYPE", type);
	}

	public int getObjtype() {
		return data.getIntValue("OBJTYPE");
	}

	public JSONArray getJSONArray(String key){
		return data.getJSONArray(key);
	}
	public JSONObject getJSONObj(String key){
		return data.getJSONObject(key);
	}

	public String getDataTag() {
		return data.getString("DATATAG");
	}

	public void setDataTag(String tag) {
		data.put("DATATAG", tag);
	}

	public String getDevid() {
		return data.getString("DEVCODE");
	}

	public long getConnectSN() {
		return data.getLongValue("CONNECT_SN");
	}

	public void setConnectSN(long sn) {
		data.put("CONNECT_SN", sn);
	}

	public void setDevid(String devcode) {
		data.put("DEVCODE", devcode);
	}

	public void setConnectSN(int sn) {
		data.put("CONNECT_SN", sn);
	}

	public Date getRecvTime() {
		return data.getDate("RECVTIME");
	}
	public long getReportTime() {
		return data.getDate("TIME").getTime();
	}
	public void setRecvTime(Date date) {
		this.data.put("RECVTIME", date);
	}

	public Date getDate(String dateStr) {
		return this.data.getDate(dateStr);
	}

	public DBObject toDBObject() {
		DBObject obj = (DBObject) JSON.parse(data.toJSONString());
		obj.put("RECVTIME", data.getDate("RECVTIME"));
		obj.put("TIME", data.getDate("TIME"));
		return obj;
	}

	@SuppressWarnings("unchecked")
	public void formDBObject(DBObject obj) {
		data.putAll(obj.toMap());
	}

	public void putObj(String key, Object obj) {
		this.putObj(key, obj, data);
	}

	public void putObj(String path, Object obj, Map<String, Object> map) {
		String[] keys = path.split("\\.");

		if (keys.length == 1) {
			data.put(path, obj);
		} else if (keys.length == 2) {
			JSONObject submap;
			if (!data.containsKey(keys[0])) {
				submap = new JSONObject();
				data.put(keys[0], submap);
			} else {
				submap = data.getJSONObject(keys[0]);
			}
			submap.put(keys[1], obj);
		} else {
			throw new RuntimeException("NO SUPPORT SO MANY LAYER " + path);
		}

	}

	public String getString(String key) {
		return data.getString(key);
	}

	public Object get(String key) {
		return data.get(key);
	}

	public String toJSONString() {
		return this.data.toJSONString();
	}

	public DataObj() {
		this.data = new JSONObject();
	}

	public DataObj(DBObject obj) {
		this.data = new JSONObject();
		this.formDBObject(obj);
	}

	public DataObj(JSONObject obj) {
		this.data = obj;
	}

	public String toString() {
		return this.data.toString();
	}

	public long getLong(String key){
		return this.data.getLongValue(key);
	}

	public int getInt(String key){
		return this.data.getIntValue(key);
	}

	public Object getValue(String path) {
		String[] names = path.split("\\.");
		if (names.length > 3) {
			throw new RuntimeException("Not Support :" + path);
		}

		Object valueInStr = null;
		try{
		switch (names.length) {
			case 1:
				valueInStr = this.get(names[0]);
				break;
			case 2:
				valueInStr = data.getJSONObject(names[0]).get(names[1]);
				break;
			case 3:
				valueInStr = data.getJSONObject(names[0]).getJSONObject(names[1]).get(names[2]);
				break;
			}
		}catch(Exception e){
		}
		return valueInStr;
	}

	public String getStrValue(String path) {
		String[] names = path.split("\\.");
		if (names.length > 3) {
			throw new RuntimeException("Not Support :" + path);
		}
		String valueInStr = null;
		try {
			switch (names.length) {
			case 1:
				valueInStr = this.getString(names[0]);
				break;
			case 2:
				valueInStr = data.getJSONObject(names[0]).getString(names[1]);
				break;
			case 3:
				valueInStr = data.getJSONObject(names[0]).getJSONObject(names[1]).getString(names[2]);
				break;
			}
		} catch (Exception e) {
		}
		return valueInStr;
	}

	public static void  main(String[] args){
		DataObj obj = new DataObj();
		obj.putObj("A.B", "C");
		System.out.println(obj);
	}



}
