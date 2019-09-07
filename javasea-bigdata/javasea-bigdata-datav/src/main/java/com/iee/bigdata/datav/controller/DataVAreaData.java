package com.iee.bigdata.datav.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iee.bigdata.datav.common.HttpClientUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName DataVAreaData
 * @Description DataV的区域数据加载类, 用于大屏监控中进行查询的地理位置条件
 * @Author longxiaonan@163.com
 * @Date 2018/9/21 0021 16:58
 */
public class DataVAreaData {

    //最外层的key用于索引, 可能是中国的区域id 100000, 或者是省份的区域id, 还可能是市区的区域id
    //里面的map是针对于第一个key的下级行政单位的区域id和行政单位名称.
    //如: 最外层的key为100000, 那么下级的行政单位级别是省, 那么里层的map就是省份的区域id和对应的省名称, 如{"440000":"广东省", "450000":"广西省",...}.
    //如: 最外层的key为440000, 那么下级的行政单位级别是市, 那么里层的map就是市的区域id和对应的市名称. 如{"440100":"广州市", "440600":"佛山市",...}.
    private static Map<String, Map<String, String>> areaMap = new HashMap<>();

    private DataVAreaData() {
    }

    private static class SingletonFactory {
        private static DataVAreaData instance = new DataVAreaData();
    }

    public static DataVAreaData getInstance() {
        //加载中国省份的区域code和省份名称.
        String result = HttpClientUtils.sendHttpGet("https://geo.datav.aliyun.com/areas/bound/100000_full.json");
        areaMap.put("100000", parseResult(result));
        return SingletonFactory.instance;
    }

    private static Map<String, String> parseResult(String result) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray features = jsonObject.getJSONArray("features");
        features.forEach((a) -> {
            JSONObject bb = (JSONObject) a;
            JSONObject properties = bb.getJSONObject("properties");
            String adcode = properties.getString("adcode");//省份code
            String name = properties.getString("name");//省份名称
            map.put(adcode, name);
        });
        return map;
    }

    public Map<String, String> getAreaData(){
        return getAreaData(null);
    }

    /**
     * 获取区域数据. 如果areaId为""或者null, 返回中国省份的数据,格式为{"440000":"广东省", "450000":"广西省", ...}
     */
    public Map<String, String> getAreaData(String areaId) {
        Map<String, String> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(areaId)) {
            return areaMap.get("100000");
        }
        Set<String> keys = areaMap.keySet();
        if (keys.contains(areaId)) {
            return areaMap.get(areaId);
        }
        String result = HttpClientUtils.sendHttpGet("https://geo.datav.aliyun.com/areas/bound/" + areaId + "_full.json");
        areaMap.put(areaId, parseResult(result));
        return areaMap.get(areaId);
    }

    public static void main(String[] args) {
        System.out.println(DataVAreaData.getInstance().getAreaData());//默认是获取全国省(包括直辖市)的区域id和行政名
        System.out.println(DataVAreaData.getInstance().getAreaData("110000"));//110000为北京市的区域id
        System.out.println(DataVAreaData.getInstance().getAreaData("440000"));//440000为广东省的区域id
        System.out.println(DataVAreaData.getInstance().getAreaData("441900"));//441900为东莞的区域id
    }
}
