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
 * ҵ������������
 * 1.vip�û��������û�������ͨ�û��Ĳ����ĸ�����1��3��6
 * 2.6��ҵ�񴰿ڡ�1-4Ϊ��ͨ���ڣ�5��Ϊ���ٴ��ڣ�6vip����
 * 3.�ͻ�����ҵ����ʱ������ֵ����Сֵ��vip����ͨ�ͻ�����ҵ���ʱ���ڸ�ʱ��֮�ڣ����ٿͻ�Ϊ��Сֵʱ��
 * 4.�����Ϳͻ������Ӧ���ڰ�˳�����ΰ���ҵ�� 
 * 5.��VIP��6�ţ����ںͿ���ҵ��5�ţ�����û�пͻ��ȴ�����ҵ���ʱ�����������ڿ��Դ�����ͨ�ͻ���ҵ��
 	��һ���ж�Ӧ�Ŀͻ��ȴ�����ҵ���ʱ�������ȴ����Ӧ�ͻ���ҵ��
 * 6.������ɿͻ�ʱ�����Լ�ҵ�����ʱ�����ֵ����Сֵ�Զ����������á�	
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
		 
	
	 * �Ƚ��ַ���������ͬ���ַ�
	 * 1.��һ����ֵ����Ǳ��������ַ���
	 * 2.�Ƚ��ַ��Ƿ������ǹ�
	 

		
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
	 *������ת�ַ�
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
