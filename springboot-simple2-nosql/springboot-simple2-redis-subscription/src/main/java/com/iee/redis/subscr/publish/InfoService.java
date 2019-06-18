package com.iee.redis.subscr.publish;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoService implements ICacheUpdate {
	private static Logger logger = LoggerFactory.getLogger(InfoService.class);
	@Autowired
	private RedisCache redisCache;
	/**
	 * 按信息类型分类查询信息
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> selectAllInfo(){
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		List<String> infoTypeList = Lists.newArrayList();
		infoTypeList.add("AA");
		infoTypeList.add("BB");
		infoTypeList.add("CC");
		logger.info("-------按信息类型查找公共信息开始----"+infoTypeList);
		if(infoTypeList!=null && infoTypeList.size()>0) {
			for (String infoType : infoTypeList) {
				List<Map<String, Object>> result = this.selectByInfoType(infoType);
				resultMap.put(infoType, result);
			}
		}
		return resultMap;
	}

	private List<Map<String, Object>> selectByInfoType(String infoType) {
		List resultList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		resultList.add(map);
		switch (infoType){
			case "AA":
				map.put("AA", "A11111");
				break;
			case "BB":
				map.put("BB", "B11111");
				break;
			case "CC":
				map.put("CC", "C11111");
				break;
		}
		return resultList;
	}

	@Override
	public void update() {
		//缓存首页信息
		logger.info("InfoService selectAllInfo 刷新缓存");
		Map<String, List<Map<String, Object>>> resultMap = this.selectAllInfo();
		Set<String> keySet = resultMap.keySet();
		for(String key:keySet){
			List<Map<String, Object>> value = resultMap.get(key);
			redisCache.put("PUBLIC_INFO_ALL"+key, value);
		}
	}
}
