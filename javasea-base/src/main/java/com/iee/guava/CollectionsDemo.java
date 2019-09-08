package com.iee.guava;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionsDemo {
   public static void main(String[] args) {
      // create map
	   LinkedHashMap<String,String> map = new LinkedHashMap<String, String> ();

      // populate the map
      map.put("2","IS");
      map.put("1","TP");
      map.put("3","BEST");

      // create a sorted map
      Map sortedmap = (Map)Collections.synchronizedMap(new LinkedHashMap<>());

      sortedmap.put("aa", 11);
      sortedmap.put("bb", 22);
      System.out.println("Synchronized sorted map is :"+sortedmap);
   }
}
