package com.zhirui.lmwy.common.utils;

import com.google.common.base.Splitter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 各种进制转换工具类
    * ConversionHexCharByteUtil
    * @author longxiaonan
    * @email longxiaonan@163.com
    * @date 2017年8月30日 下午12:30:01
    * @version 1.0
 */
public class NumericUtils {

	private static String hexStr = "0123456789ABCDEF";
	private static String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
			"1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	/** CRC校验 */
	public static void checkValidateCode(String s, String vs) {
		String[] split = s.split(" ");
		int tmp = Integer.valueOf(split[0], 16);
		;
		for (int i = 1; i < split.length; i++) {
			tmp = tmp ^ Integer.valueOf(split[i], 16);
		}
		String binaryString = Integer.toBinaryString(tmp);
		String binaryString2 = Integer.toBinaryString(Integer.valueOf(vs, 16));
		if (binaryString.equals(binaryString2)) {
			System.out.print("对校验码的校验成功");
		} else {
			System.out.print("对校验码的校验失败");
		}
		System.out.println(",对字符串的校验为:" + binaryString + ",校验码为:" + binaryString2);

	}

	public static String hexStringToIntegerString(String s) {
		String[] split = s.split(" ");
		StringBuilder sbd = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			int tmp = Integer.valueOf(split[i], 16);
			sbd.append((tmp <= 9 ? "0" + tmp : tmp)).append(":");
		}
		sbd.deleteCharAt(sbd.length() - 1);
		return sbd.toString();
	}

	public static Integer hexStringToIntegerValue(String s) {
		Integer parseInt = null;
		try {
			String[] split = s.split(" ");
			StringBuilder sbd = new StringBuilder();
			for (int i = 0; i < split.length; i++) {
				int tmp = Integer.valueOf(split[i], 16);
				sbd.append((tmp <= 9 ? "0" + tmp : tmp)).append(":");
			}
			sbd.deleteCharAt(sbd.length() - 1);
			parseInt = Integer.parseInt(sbd.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return parseInt;
	}



	/**
	 * 不足2为在前面补充0, 如23 23 1 2, 返回成23 23 01 02
	 * @param hex
	 * @return
	 */
	public static String hexStringAddZero(String hex){
		String[] split = hex.split(" ");
		StringBuffer sb = new StringBuffer();
		Stream.of(split).peek(n -> {
			if(n.length() == 1){
				sb.append("0").append(n).append(" ");
			}else{
				sb.append(n).append(" ");
			}
		}).collect(Collectors.toList());
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static String hexStringToAsciiString(String s) {
		String[] split = s.split(" ");
		String ss = "";
		for (int i = 0; i < split.length; i++) {
			byte tmp = Byte.valueOf(split[i], 16);
			// int tmp = Integer.valueOf(split[i],16);
			ss += (char) tmp;
		}
		System.out.println("字符串按16进制转为ASCII码为:" + ss);
		return ss;
	}


	public static void lengthOfStringSplitBySpace(String s) {
		String[] split = s.split(" ");
		System.out.println("字符串按空格切分后的长度:" + split.length);
	}

	public static byte[] hexStringToByteArray(String s) {
		String[] split = s.split(" ");
		byte[] b = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			Integer valueOf = Integer.valueOf(split[i], 16);
			byte byteValue = valueOf.byteValue();
			b[i] = byteValue;
		}
		System.out.println("字符串按16进制转回的原始byte[]为:" + Arrays.toString(b));
		return b;
	}

	/**
	 * @param hexString
	 * @return 将十六进制转换为二进制字节数组 16-2
	 */
	public static byte[] hexStringToByteArray2(String hexString) {
		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位
		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}

	/**
	 *  如:2323011A  转换后:00100011001000110000000100011010
	 * @param hexString
	 * @return 将十六进制转换为二进制字符串 16-2
	 */
	public static String hexStringToBinString(String hexString) {
		return byteArrayToBinString(hexStringToByteArray(hexString));
	}

	/**
	 * 二进制字符串转换成16进制字符串
	 * @param binStr
	 *            如二进制字符串:00100011001000110000000100011010
	 * @return 转换后的16进制为:2323011A
	 */
	public static String binStringToHexString(String binStr) {
		StringBuffer sb = new StringBuffer();
		Splitter binArr = Splitter.fixedLength(4);
		List<String> splitToList = binArr.splitToList(binStr);
		splitToList.forEach(n -> {
			System.out.println(n);
			for (int i = 0; i < binaryArray.length; i++) {
				if (n.equals(binaryArray[i])) {
					sb.append(hexStr.charAt(i));
				}
			}
		});
		return sb.toString();
	}

	/**
	 * 对象转byte
	 *
	 * @param obj
	 * @return
	 */
	private byte[] ObjectToByte(Object obj) {
		byte[] bytes = null;
		try (
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream oo = new ObjectOutputStream(bo)
		) {
			oo.writeObject(obj);
			bytes = bo.toByteArray();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
		}
		return bytes;
	}

	/**
	 * byte转对象
	 *
	 * @param bytes
	 * @return
	 */
	private Object ByteToObject(byte[] bytes) {
		Object obj = null;
		try (
				// bytearray to object
				ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
				ObjectInputStream oi = new ObjectInputStream(bi)
		) {
			obj = oi.readObject();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
		}
		return obj;
	}

	/**
	 * @param bArray
	 * @return 二进制数组转换为二进制字符串 2-2
	 */
	public static String byteArrayToBinString(byte[] bArray) {
		String outStr = "";
		int pos = 0;
		for (byte b : bArray) {
			// 高四位
			pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// 低四位
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;
	}

	public static byte[] byteArrayToHexArray(byte[] raw){
		return hexStringToByteArray(byteArrayToHexString(raw));
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuilder sbd = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String hexString = String.format("%02X", b[i]);
			sbd.append(hexString).append(" ");
		}
		sbd.deleteCharAt(sbd.length() - 1);
		System.out.println("字节数组得到的16进制字符串为:" + sbd.toString());
		return sbd.toString();
	}

	/**
	 * @param bArray
	 * @return 将二进制数组转换为十六进制字符串 2-16
	 */
	public static String byteArrayToHexString2(byte[] bArray) {

		String result = "";
		String hex = "";
		for (int i = 0; i < bArray.length; i++) {
			// 字节高4位
			hex = String.valueOf(hexStr.charAt((bArray[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(hexStr.charAt(bArray[i] & 0x0F));
			result += hex; // +" "
		}
		return result;
	}

	public static String asciiStringToHexString(String s) {
		int length = s.length();
		byte[] bs = s.getBytes();
		String[] ss = new String[length];
		for (int i = 0; i < length; i++) {
			ss[i] = Integer.toHexString(bs[i]).toUpperCase();
		}
		String join = String.join(" ", ss);
		System.out.println("字符串得到的16进制字节数组:[" + join + "]");
		return join;
	}

	public static void main(String[] args) {
		String vin = "LB9KB8KG9GENJL221";
		String vinByteString = NumericUtils.hexStringToAsciiString("4C 42 39 4B 42 38 4B 47 39 47 45 4E 4A 4C 32 32 31");
		String valueOf = Integer.toBinaryString(12);

		byte[] hexStringToByteArray = hexStringToByteArray("23 23 01 1A");
		System.out.println(hexStringToByteArray);
		for (byte b : hexStringToByteArray) {
			System.out.println(b);
		}
		System.out.println(valueOf);
	}

	public static void generateAndReplaceLengthByte(byte[] b) {
		int a = b.length - 25;
		if (a > 65531) {
			throw new RuntimeException("数据单元长度有误: " + a);
		}
		byte b1 = (byte) ((a >> 8) & 0xFF);
		byte b2 = (byte) (a & 0xFF);
		b[22] = b1;
		b[23] = b2;
	}

	public static void generateAndReplaceValidateCode(byte[] b) {
		byte tmp = b[2];
		for (int i = 3; i < b.length - 1; i++) {
			tmp ^= b[i];
		}
		b[b.length - 1] = tmp;
	}

	public static String zero(int count) {
		StringBuffer sbd = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sbd.append("00 ");
		}
		sbd.deleteCharAt(sbd.length() - 1);
		String string = sbd.toString();
		System.out.println("补充的0字符串:" + string);
		return string;
	}

	public static void loggerFilter(String filePath) {// 过滤日志文件
		try {
			Files.lines(Paths.get("debug.log"), Charset.forName("gbk")).forEach(line -> {
				boolean contains = line.contains("开始【补发转发】")
						// || line.contains("南瑞")
						// || line.contains("transfer.HeartClient send")
						// || line.contains("开始第")
						// || line.contains("01 4C 42 39 4B 42 39 4B 47 39 47 45
						// 4E 4A 4C 31 31 33")
						|| line.contains("[23 23 05 FE") || line.contains("[23 23 05 01") || line.contains("开始第")
						|| line.contains("转发到南瑞平台，连接关闭");
				if (contains) {
					System.out.println(line);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
