import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Severity;

/**
 * 业务需求描述：
 * 1.vip用户，快速用户，和普通用户的产生的概率是1：3：6
 * 2.6个业务窗口。1-4为普通窗口，5号为快速窗口，6vip窗口
 * 3.客户办理业务有时间的最大值和最小值，vip和普通客户办理业务的时间在该时间之内，快速客户为最小值时间
 * 4.各类型客户在其对应窗口按顺序依次办理业务。 
 * 5.当VIP（6号）窗口和快速业务（5号）窗口没有客户等待办理业务的时候，这两个窗口可以处理普通客户的业务，
 	而一旦有对应的客户等待办理业务的时候，则优先处理对应客户的业务。
 * 6.随机生成客户时间间隔以及业务办理时间最大值和最小值自定，可以设置。	
 *
 */

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		ServerService serverService = new ServerService();
		serverService.start();
		Thread.sleep(3*1000);
		ClientService clientService = new ClientService();
		clientService.start();
		
		
	}
	
/*	
	
	public static byte[] intToBytes(int n){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(n >> 24 & 0xff);
        bytes[1] = (byte)(n >> 16 & 0xff);
        bytes[2] = (byte)(n >> 8 & 0xff);
        bytes[3] = (byte)(n  & 0xff);
        return bytes;
    }
	
	public static int bytesToInt(byte[] bytes){
	    return (bytes[0] << 24)|(bytes[1] << 16)|
	        (bytes[2] << 8)|(bytes[3]);
	    }
	
	
	private static String toHex(int n){
		StringBuffer buffer = new StringBuffer();
		while(n > 0){
			if(n%16 > 9){
				switch(n%16){
				case 10:
					buffer.append("A");
					break;
				case 11:
					buffer.append("B");
					break;
				case 12:
					buffer.append("C");
					break;
				case 13:
					buffer.append("D");
					break;
				case 14:
					buffer.append("E");
					break;
				case 15:
					buffer.append("F");
					break;
				}
			}
			else{
				buffer.append(n%16);
			}
			n = n/16;
		}
		return buffer.reverse().toString();
	}
	
	
	
	
	
	private static String toBinary(int n){
		StringBuffer buffer = new StringBuffer();
		int temp = n;
		while(n > 1){
			n = n/2;
			if(n%2 != 0){
				buffer.append("1");
			}else{
				buffer.append("0");
			}
		}
		buffer.reverse();
		if(temp%2 == 0){
			buffer.append("0");
		}else{
			buffer.append("1");
		}
		return buffer.toString();
	}
		 
	
	 * 比较字符中有无相同的字符
	 * 1.用一个数值来标记遍历过得字符，
	 * 2.比较字符是否被数组标记过
	 

		
	public static boolean compareChar(String str){
		if(str == null || str.length() > 256){
			return false;
		}
		boolean[] save = new boolean[256];
		for(int i = 0;i < str.length();i++){
			int ch = str.charAt(i);
			if(save[ch]){
				return false;
			}
			save[ch] = true;
		}
		return true;
	}
	
	private void test(){
		try{
			File a = new File("C:\\Users\\ryze.liu\\Desktop\\b.txt");
			File b = new File("C:\\Users\\ryze.liu\\Desktop\\a.txt");
			byte[] buffer = new byte[10];
		    FileInputStream fis = new FileInputStream(a);
		    FileOutputStream os = new FileOutputStream(b);
		   // long BytesToSend = fileToSend.length();
		    while(true)
		    {
			    int bytesRead = fis.read(buffer, 0, buffer.length);
			    if(bytesRead == -1)
			    {
			    	break;
			    }
			    //BytesToSend = BytesToSend - bytesRead;
			    buffer[9] = 101;
			    System.out.println("bytesRead:::"+bytesRead);
			    for(int i = 0;i < 10;i++){
			    	System.out.println("buffer:::"+buffer[i]);
			    }
			    
			    os.write(buffer,0, buffer.length);
			    os.flush();			    
		    }
		}catch (IOException e) {
			
		}
	}
	
	*//**
	 *函数反转字符
	 *//*
	public static String reveser(String str){
		if(str == null)
			throw new UnsupportedOperationException("str can not be null");
		StringBuffer buffer = new StringBuffer();
		for(int i = str.length()-1; i >= 0; i--){
			buffer.append(str.charAt(i));
		}
		return buffer.toString();
	}
	*/
	
}
