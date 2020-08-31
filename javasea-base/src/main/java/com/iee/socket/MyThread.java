package com.iee.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyThread extends Thread{

	private InputStream inputStream;
	private Socket mySocket = null;
	private OutputStream outputStream;


	public MyThread(InputStream inputStream, Socket socket) {
		this.inputStream = inputStream;
		this.mySocket = socket;
	}

	@Override
	public void run(){//透传终端数据
		try {
			while(true){
//				System.out.println("开始了");
				byte[] b = new byte[1024];
				//一直读通道里的内容，收到指令就继续执行
				int read = inputStream.read(b);
				if(read==-1){
					continue;
				}
				byte[] realRead = new byte[read];
				System.arraycopy(b, 0, realRead, 0, read);

				System.out.println("读取到服务端回包:["+bytesToHexString(realRead)+"]");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 字节数组转成16进制表示格式的字符串,中间以空格隔开
	 *
	 * @param byteArray
	 *            需要转换的字节数组
	 * @return 16进制表示格式的字符串
	 **/
	public static  String bytesToHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1)
			throw new IllegalArgumentException("this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i])).append(" ");
		}
		return hexString.toString().toUpperCase();
	}

}
