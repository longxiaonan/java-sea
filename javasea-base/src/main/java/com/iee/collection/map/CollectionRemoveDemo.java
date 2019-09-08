package com.iee.collection.map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionRemoveDemo {

    public static void main(String[] args) {
        ListRemove();
        System.out.println("-----------------------------------------------------------------------------------------------");
        SetRemove();
        System.out.println("-----------------------------------------------------------------------------------------------");
        MapRemove();
    }

    public static void ListRemove(){
        List<String> strList = new ArrayList<String>();
        strList.add("aaaa");
        strList.add("bbbb");
        strList.add("cccc");
        strList.add("cccc");
        strList.add("dddd");
        for(String str : strList){
            System.out.println(str);
        }
        System.out.println("init List size:" + strList.size());
        Iterator<String> it = strList.iterator();
        while(it.hasNext()){
            String str = it.next();
            if(str.equals("cccc")){
                it.remove();
            }
        }
        for(String str : strList){
            System.out.println(str);
        }
        System.out.println("removed List size:" + strList.size());
    }

    public static void SetRemove(){
        Set<String> strSet = new TreeSet<String>();
        strSet.add("aaaa");
        strSet.add("bbbb");
        strSet.add("cccc");
        strSet.add("cccc");//重复的数据将不会再次插入
        strSet.add("dddd");
        for(String str : strSet){
            System.out.println(str);
        }
        System.out.println("Init Set size:" + strSet.size());
        Iterator<String> it = strSet.iterator();
        while(it.hasNext()){
            String str = it.next();
            if(str.equals("cccc")){
                it.remove();
            }
        }
        for(String str : strSet){
            System.out.println(str);
        }
        System.out.println("removed Set size:" + strSet.size());
    }

    public static void MapRemove(){
        Map<String, String> strMap = new TreeMap<String, String>();
        strMap.put("a", "aaaa");
        strMap.put("b", "bbbb");
        strMap.put("c", "cccc");
        strMap.put("d", "dddd");
        for(String key : strMap.keySet()){
            System.out.println(key + " : " + strMap.get(key));
        }
        System.out.println("Init Map size:" + strMap.size());
        Iterator<Entry<String,String>> it = strMap.entrySet().iterator();
        while(it.hasNext()){
            Entry<String,String> strEntry = it.next();
            if(strEntry.getKey().equals("c")){
                it.remove();
            }
        }
        for(String key : strMap.keySet()){
            System.out.println(key + " : " + strMap.get(key));
        }
        System.out.println("removed Map size:" + strMap.size());
    }
}
