package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public  class SenderUtil {
	 // 工具类
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	public static void printGet(byte[] raw){
		String hexString = SenderUtil.byteArrayToHexString(raw);
		String vin = SenderUtil.getVINString(raw);
		SenderUtil.printAsLogger("GOT ["+ vin +"] "+ hexString);
	}
	public static void printSend(byte[] raw){
		String hexString = SenderUtil.byteArrayToHexString(raw);
		String vin = SenderUtil.getVINString(raw);
		SenderUtil.printAsLogger("SEND ["+ vin +"] "+ hexString);
	}
	public static void printSend(String line){
		SenderUtil.printAsLogger("SEND "+ line);
	}
	
	public static byte[] byteArrayToHexArray(byte[] raw){
		return hexStringToByteArray(byteArrayToHexString(raw));
	}
	
	public static String byteArrayToHexString(byte[] raw) {
		StringBuilder sbd = new StringBuilder();
		for (int i = 0; i < raw.length; i++) {
			String format = String.format("%02X", raw[i]);
			sbd.append(format).append(" ");
		}
		sbd.deleteCharAt(sbd.length()-1);
		return sbd.toString();
	}
	public static void printAsLogger(String line){
		String name = Thread.currentThread().getName();
		String format = sdf.format(new Date());
		System.out.printf("%s [%s] %s\n", format, name, line);
	}
	public static byte[] hexStringToByteArray(String line) { // 如23 23 01 FE
		String[] split = line.split(" ");
		int length = split.length;
		byte[] bs = new byte[length];
		for (int i = 0; i < length; i++) {
			Integer valueOf = Integer.valueOf(split[i], 16);
			byte byteValue = valueOf.byteValue();
			bs[i] = byteValue;
		}
		return bs;
	}
	
	/**
	 * 不足2为在前面补充0
	 * @param hex
	 * @return
	 */
	public static String HexStringAddZero(String hex){
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
	public static void main(String[] args) {
		String s1 = "23 23 2 FE 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 31 1 1 ED 11 0A 18 0A 1E 12 1 1 2 1 0 0 0 2 DB EA 17 2C 27 1B 44 1 1 9D 3F 28 0 2 1 1 1 63 4E 36 50 82 62 17 AC 26 F2 5 0 7 34 14 53 1 E8 75 10 6 1 1 0C E7 1 3 0C CF 1 2 40 3 9 3C 7 0 0 0 3 0 0 0 0 0 8 1 1 17 2C 27 1B 0 B4 0 1 B4 0C E1 0C E7 0C DC 0C E1 0C E4 0C E1 0C DE 0C E1 0C E0 0C DF 0C DF 0C E1 0C DF 0C DE 0C DF 0C E2 0C E0 0C DF 0C DF 0C DE 0C DF 0C DE 0C DF 0C DB 0C DE 0C E1 0C DB 0C E0 0C DB 0C DE 0C E1 0C E1 0C DF 0C DE 0C E1 0C DE 0C CF 0C DF 0C E4 0C DF 0C DB 0C DE 0C DC 0C E4 0C E1 0C E1 0C DA 0C DF 0C DB 0C E0 0C DF 0C DB 0C E0 0C DE 0C DE 0C DF 0C E1 0C E1 0C DE 0C DF 0C E1 0C E2 0C DF 0C DF 0C E0 0C DF 0C E1 0C E0 0C E1 0C DB 0C E1 0C E0 0C DE 0C E0 0C E0 0C D9 0C DF 0C E0 0C D9 0C DF 0C E1 0C DE 0C DE 0C E1 0C E1 0C E4 0C DF 0C E2 0C DE 0C E1 0C DB 0C E1 0C DF 0C DF 0C DE 0C DF 0C DF 0C DF 0C DF 0C DF 0C DC 0C DF 0C E0 0C E1 0C E1 0C DF 0C DF 0C DF 0C DF 0C E0 0C E1 0C E1 0C DE 0C E1 0C E1 0C E2 0C E0 0C E0 0C DB 0C E1 0C E0 0C E0 0C E1 0C E0 0C E1 0C DF 0C E1 0C DE 0C DF 0C E2 0C E2 0C E2 0C E2 0C E1 0C DF 0C E2 0C E1 0C E2 0C E3 0C DE 0C E2 0C E2 0C E4 0C E1 0C E1 0C E1 0C E2 0C E2 0C E1 0C E1 0C E4 0C E1 0C E2 0C DF 0C E1 0C E4 0C E2 0C E1 0C E2 0C E2 0C E0 0C E1 0C DC 0C DC 0C E0 0C DF 0C DF 0C E0 0C E0 0C DF 0C DF 0C DE 0C DF 0C DF 0C E1 0C E1 0C E1 0C E1 0C E1 0C E0 9 1 1 0 28 40 40 40 40 3E 3E 3E 3E 3C 3C 3C 3D 3E 3C 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3C 3E 3E 3E 3C 3C 3E 3E 3E 3E 40 40 40 40 DB";
		String s2 = "23 23 2 FE 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 31 1 1 ED 11 0B 18 0A 1E 12 1 1 2 1 0 0 0 2 DB EA 17 2C 27 1B 44 1 1 9D 3F 28 0 2 1 1 1 63 4E 36 50 82 62 17 AC 26 F2 5 0 7 35 3a 84 1 E8 62 e1 6 1 1 0C E7 1 3 0C CF 1 2 40 3 9 3C 7 0 0 0 3 0 0 0 0 0 8 1 1 17 2C 27 1B 0 B4 0 1 B4 0C E1 0C E7 0C DC 0C E1 0C E4 0C E1 0C DE 0C E1 0C E0 0C DF 0C DF 0C E1 0C DF 0C DE 0C DF 0C E2 0C E0 0C DF 0C DF 0C DE 0C DF 0C DE 0C DF 0C DB 0C DE 0C E1 0C DB 0C E0 0C DB 0C DE 0C E1 0C E1 0C DF 0C DE 0C E1 0C DE 0C CF 0C DF 0C E4 0C DF 0C DB 0C DE 0C DC 0C E4 0C E1 0C E1 0C DA 0C DF 0C DB 0C E0 0C DF 0C DB 0C E0 0C DE 0C DE 0C DF 0C E1 0C E1 0C DE 0C DF 0C E1 0C E2 0C DF 0C DF 0C E0 0C DF 0C E1 0C E0 0C E1 0C DB 0C E1 0C E0 0C DE 0C E0 0C E0 0C D9 0C DF 0C E0 0C D9 0C DF 0C E1 0C DE 0C DE 0C E1 0C E1 0C E4 0C DF 0C E2 0C DE 0C E1 0C DB 0C E1 0C DF 0C DF 0C DE 0C DF 0C DF 0C DF 0C DF 0C DF 0C DC 0C DF 0C E0 0C E1 0C E1 0C DF 0C DF 0C DF 0C DF 0C E0 0C E1 0C E1 0C DE 0C E1 0C E1 0C E2 0C E0 0C E0 0C DB 0C E1 0C E0 0C E0 0C E1 0C E0 0C E1 0C DF 0C E1 0C DE 0C DF 0C E2 0C E2 0C E2 0C E2 0C E1 0C DF 0C E2 0C E1 0C E2 0C E3 0C DE 0C E2 0C E2 0C E4 0C E1 0C E1 0C E1 0C E2 0C E2 0C E1 0C E1 0C E4 0C E1 0C E2 0C DF 0C E1 0C E4 0C E2 0C E1 0C E2 0C E2 0C E0 0C E1 0C DC 0C DC 0C E0 0C DF 0C DF 0C E0 0C E0 0C DF 0C DF 0C DE 0C DF 0C DF 0C E1 0C E1 0C E1 0C E1 0C E1 0C E0 9 1 1 0 28 40 40 40 40 3E 3E 3E 3E 3C 3C 3C 3D 3E 3C 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3E 3C 3E 3E 3E 3C 3C 3E 3E 3E 3E 40 40 40 40 DB";
		System.out.println(HexStringAddZero(s1));
		System.out.println(HexStringAddZero(s2));
	}
	public static String getVINString(String raw){ // 从原始数据中获取车机号
		byte[] vinBytes = SenderUtil.hexStringToByteArray(raw);
		return getVINString(vinBytes);
	}
	
	public static String getVINString(byte[] raw){ // 从原始数据中获取车机号
		byte[] vin = new byte[17];
		System.arraycopy(raw, 4, vin, 0, 17);
		String vinString = new String(vin);
		return vinString;
	}
	public static void printException(Exception e){
		System.err.println(Thread.currentThread().getName()+" "+e.getMessage());
	}
	public static String generateDevcode(int a){ // 依据给到的序号生成17位的字符串车机号
		String string = Integer.toString(a);
		String leftPad = StringUtils.leftPad(string, 17, '0');
		return leftPad;
	}
	public static byte[] replaceDevcodeAndValidate(String rawString, String newDevcode){ // 替换原始数据中的车机号为有序车机号及校验码,并返回字节数组
		byte[] bs = SenderUtil.hexStringToByteArray(rawString);
		byte[] devcodeBs = newDevcode.getBytes();
		if ( devcodeBs.length != 17 ) {
			throw new RuntimeException("车机号长度要求是17位:"+devcodeBs.length);
		}
		for (int i = 0; i < devcodeBs.length; i++) {
			bs[i+4] = devcodeBs[i];
		}
		SenderUtil.generateAndReplaceValidateByte(bs);
		return bs;
	}
	public static void generateAndReplaceValidateByte(byte[] bs) { // 生成并替换校验字节
		byte tmp = bs[2];
		for (int i = 3; i < bs.length -1; i++) {
			tmp ^= bs[i];
		}
		bs[bs.length-1] = tmp;
	}
	public static boolean checkLogginSucceed(byte[] tmp) { // 检查是否为登入成功应答包
		boolean b1 = tmp[2] == 0x01;
		boolean b2 = tmp[3] == 0x01;
		if( b1 && b2 ){
			return true;
		}else{
			return false;
		}
	}

}