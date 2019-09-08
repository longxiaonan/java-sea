package com.iee.encrypt;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * ShiroCryptographyTest
 * @date 2016年12月17日 上午9:43:51
 * @version 1.0
 */
public class ShiroCryptographyTest {

	/** base64 */
	@Test
	public void base64(){
		/** 加密 */
		String base64Encoded = Base64.encodeToString("123456".getBytes());
		System.out.println("加密: " + base64Encoded);

		/** 解密 */
		System.out.println("解密: " + Base64.decodeToString(base64Encoded));
	}

	/** hex */
	@Test
	public void hex(){
		/** 加密 */
		String hexEncoded = Hex.encodeToString("123456".getBytes());
		System.out.println("加密: " + hexEncoded);
		/** 解密 */
		System.out.println("解密: " + new String(Hex.decode(hexEncoded)));
	}

	/** AES */
	@Test
	public void aes(){
		/**  创建AES加密服务类 */
		AesCipherService as = new AesCipherService();
		/** 定义key: 密钥 (16位长度的字节数组) */
		byte[] key = "1111111111111111".getBytes();

		/** 加密 (字节源对象)*/
		ByteSource  byteSource = as.encrypt("123456".getBytes(), key);
		System.out.println("加密: " + byteSource.toHex());

		/** 解密 */
		ByteSource bs = as.decrypt(byteSource.getBytes(), key);
		System.out.println("解密: " + new String(Hex.decode(bs.toHex())));

	}



	/** md5 */
	@Test
	public void md5(){
		// 不安全 32位
		System.out.println(new Md5Hash("123").toHex());//202cb962ac59075b964b07152d234b70
		// 普通MD5默认toBase64, 这里改成toBase64后和普通的MD5相等
//		System.out.println(new Md5Hash("123").toBase64());/ICy5YqxZB1uWSwcVLSNLcA==
//		System.out.println(MD5.getMD5("123"));/202cb962ac59075b964b07152d234b70
		System.out.println(MD5.encryptToBase64("123"));//ICy5YqxZB1uWSwcVLSNLcA==

		/** SecureRandomNumberGenerator安全随机数生成器(32位) */
		SecureRandomNumberGenerator srg = new SecureRandomNumberGenerator();
		System.out.println("安全随机数：" + srg.nextBytes().toHex());
		// 加盐和迭代次数, 盐写死了...
		System.out.println(new Md5Hash("123456", "67608bfedd52ad00d6ef728c89855e2c", 5));
		// 加盐和迭代次数, 盐是通过安全随机数生成的
		System.out.println(new Md5Hash("123456", srg.nextBytes().toHex(), 5));
	}

	/** SHA-1 */
	@Test
	public void sha1Hash(){
		// 不安全 40位
		System.out.println(new Sha1Hash("123456").toHex());
		/** SecureRandomNumberGenerator安全随机数生成器(32位) */
		SecureRandomNumberGenerator srg = new SecureRandomNumberGenerator();
		System.out.println("安全随机数：" + srg.nextBytes().toHex());
		// 加盐
		System.out.println(new Sha1Hash("123456", srg.nextBytes().toHex(), 5));
		// 加密迭代次数
		System.out.println(new Sha1Hash("123456", srg.nextBytes().toHex(), 5));
	}

	/** SHA-256 */
	@Test
	public void sha256Hash(){
		// 不安全 64位
		System.out.println(new Sha256Hash("123456").toHex());
		/** SecureRandomNumberGenerator安全随机数生成器(32位) */
		SecureRandomNumberGenerator srg = new SecureRandomNumberGenerator();
		System.out.println("安全随机数：" + srg.nextBytes().toHex());
		// 加盐
		System.out.println(new Sha256Hash("123456", srg.nextBytes().toHex(), 5));
		// 加密迭代次数
		System.out.println(new Sha256Hash("123456", srg.nextBytes().toHex(), 5));
	}

	/** SHA-384 */
	@Test
	public void sha384Hash(){
		// 不安全 96位
		System.out.println(new Sha384Hash("123456").toHex().length());
		/** SecureRandomNumberGenerator安全随机数生成器(32位) */
		SecureRandomNumberGenerator srg = new SecureRandomNumberGenerator();
		System.out.println("安全随机数：" + srg.nextBytes().toHex());
		// 加盐
		System.out.println(new Sha384Hash("123456", srg.nextBytes().toHex(), 5));
		// 加密迭代次数
		System.out.println(new Sha384Hash("123456", srg.nextBytes().toHex(), 5));
	}

	/** SHA-512 */
	@Test
	public void sha512Hash(){
		// 不安全 128位
		System.out.println(new Sha512Hash("123456").toHex().length());
		/** SecureRandomNumberGenerator安全随机数生成器(32位) */
		SecureRandomNumberGenerator srg = new SecureRandomNumberGenerator();
		System.out.println("安全随机数：" + srg.nextBytes().toHex());
		// 加盐
		System.out.println(new Sha512Hash("123456", srg.nextBytes().toHex(), 5));
		// 加密迭代次数
		System.out.println(new Sha512Hash("123456", srg.nextBytes().toHex(), 5));
	}

	public static void main(String[] args) {
		System.out.println(new Md5Hash("123456", "888888", 5));
	}


}
