package com.zhirui.lmwy.common.utils;

import java.io.*;

/**
 * 序列化工具类
 *
 */
public class SerializeUtil {

	private SerializeUtil() {
		throw new RuntimeException("en,you can't reflex create object,haha.");
	}

	/**
	 * 序列化
	 *
	 * @param obj
	 *            需要序列化的对象
	 * @return
	 * @throws Exception
	 */
	public static byte[] serialize(Object obj) throws Exception {

		if (obj == null) {
			throw new RuntimeException("serialize of obj can't be null");
		}

		ObjectOutputStream oos = null;

		ByteArrayOutputStream baos = null;

		try {

			baos = new ByteArrayOutputStream();

			oos = new ObjectOutputStream(baos);

			oos.writeObject(obj);

			return baos.toByteArray();

		} finally {

			if (baos != null) {
				baos.close();
			}

			if (oos != null) {
				oos.close();
			}
		}
	}

	/**
	 * 反序列化
	 *
	 * @param data
	 *            序列化成byte[]
	 * @param clazz
	 *            转型的class
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <E> E unserialize(byte[] data, Class<? extends Serializable> clazz) throws Exception {

		if (data == null || clazz == null) {
			throw new RuntimeException("serialize obj and class can't be null");
		}

		ObjectInputStream ois = null;

		ByteArrayInputStream bis = null;

		try {
			bis = new ByteArrayInputStream(data);
			ois = new ObjectInputStream(bis);

			return (E) ois.readObject();

		} finally {

			if (ois != null) {
				ois.close();
			}

			if (bis != null) {
				bis.close();
			}
		}
	}

}

