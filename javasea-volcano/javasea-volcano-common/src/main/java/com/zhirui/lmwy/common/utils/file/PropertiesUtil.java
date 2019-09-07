package com.zhirui.lmwy.common.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 操作属性文件的工具类
 * 指定文件目录后，读取文件的配置，文件的内容需要是key=value的格式
 */
@Slf4j
public class PropertiesUtil {

	private PropertiesUtil(){
		throw new RuntimeException("haha,you can't do this.");
	}

	/**
	 * 通过传递文件的路径，以Map形式返回解析Properties的内容，key就是一行记录"="前面的内容
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getProperties(String filePath)  {
		log.info("加载文件 {}", filePath);
		if(StringUtils.isNotBlank(filePath)) {
			log.error("传递Properties的文件路径名字不能为空");
			return Collections.emptyMap();
		}

		Properties prop = new Properties();

		Map<String,String> data = new HashMap<String, String>();

		InputStream is = null;

		try {

			//is = PropertiesUtil.class.getResourceAsStream(filePath);
			is = new FileInputStream(filePath);

			prop.load(is);
			Set<Object> keySet = prop.keySet();

			if(keySet == null || keySet.isEmpty()) {
				return Collections.emptyMap();
			}


			for(Object key : keySet) {

				String value = prop.getProperty(key.toString());

				data.put(key.toString(), value);
			}


		} catch(Exception e) {
			log.error("解析Properties出现异常，文件路径[{}]，异常信息[{}]", filePath,e);
		} finally {

			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("关闭解析Properties的输出流出现异常，文件路径[{}]，异常信息[{}]", filePath,e);
					log.error("",e);
				}
			}
		}

		return data;
	}


	/**
	 * just for test
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(getProperties("E:\\project\\vdp-cloud3\\vdp-common\\aa.init"));
	}

}
