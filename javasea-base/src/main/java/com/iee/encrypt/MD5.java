package com.iee.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类，加密后转换为Base64格式
 */
public final class MD5 {

	private static MessageDigest digest;

	static {
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 Algorithm Not Supported", e);
		}

	}

//	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
//			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//
//	public static String toHexString(byte[] b) { // String to byte
//		StringBuilder sb = new StringBuilder(b.length * 2);
//		for (int i = 0; i < b.length; i++) {
//			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
//			sb.append(HEX_DIGITS[b[i] & 0x0f]);
//		}
//		return sb.toString();
//	}

	public static void main(String[] args) {
		String encryptToBase64 = encryptToBase64("123");
		System.out.println(encryptToBase64);//ICy5YqxZB1uWSwcVLSNLcA==
		System.out.println(encryptToHex("123"));
	}

	public static String encryptToBase64(String text) {
		try {
			return Base64.encodeBase64String(digest.digest(text.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("unsupported encoding : UTF-8", e);
		}
	}

	public static String encryptToHex(String text) {
		try {
			// Create MD5 Hash
//			digest.update(s.getBytes());
//			byte[] messageDigest = digest.digest();
			byte[] messageDigest = digest.digest(text.getBytes());

//			return toHexString(messageDigest);
			return Hex.encodeHexString(messageDigest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
