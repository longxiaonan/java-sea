package com.iee.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//客户端
public class ClientDemo {

	public static void main(String[] args) throws IOException {
		//将内容写到通道内的流中，再由服务器读取并打印在窗口上
//		24 10 64 FF 00 11 01 00 01 00 00 00 00 80 F2 24
		//连接服务端
//		Socket socket=new Socket("14.29.230.175",80);
		Socket socket=new Socket("14.23.108.204",5500);
//		Socket socket=new Socket("127.0.0.1",5501);
//		Socket socket=new Socket("188.88.3.15",5500);
//		Socket socket=new Socket("103.46.128.41",36348);
//		Socket socket=new Socket("192.168.83.1",8702);
//		Socket socket=new Socket("183.63.187.148",8702);//sc123400000000000
//		Socket socket=new Socket("120.78.221.1",8702);
//		Socket socket=new Socket("192.168.56.1",8702);
//		Socket socket=new Socket("192.168.189.1",8702);
//		Socket socket=new Socket("192.168.83.1",8702);
		InputStream inputStream = socket.getInputStream();
		MyThread myThread = new MyThread(inputStream,socket);
		myThread.start();

		//通道内的流
		OutputStream ops = socket.getOutputStream();
		String sss = "24 70 64 01 80 17 0F 14 0A 7D 19 9F DC C9 6B 5D 40 BD 97 E8 19 E8 83 38 40 00 00 00 00 00 00 3B 40 14 08 06 11 1F 1B 6D 04 3F 00 00 00 11 01 AA 36 C9 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 0C 00 6D 04 3F 00 00 00 0B 01 00 01 01 15 00 00 00 00 00 00 00 00 E6 AA 23";

		String[] split = sss.split(" ");
		byte[] bys=new byte[split.length];
		for (int i=0;i<split.length;i++) {
			short tmp = Short.valueOf(split[i], 16);
			bys[i]=(byte)tmp;
		}

		/*String s2 = "24 10 64 00 00 00 00 0D 01 00 00 00 00 64 1C 24";
		String[] split2 = s2.split(" ");
		byte[] bys2=new byte[split2.length];
		for (int i=0;i<split2.length;i++) {
			short tmp = Short.valueOf(split2[i], 16);
			bys2[i]=(byte)tmp;
		}*/

//		ops.write(bys);
//		ops.close();
		try {
			for(;;){
				ops.write(bys);
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}





	}


}
