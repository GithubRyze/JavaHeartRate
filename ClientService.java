/*
 WiFi Direct File Transfer is an open source application that will enable sharing 
 of data between Android devices running Android 4.0 or higher using a WiFi direct
 connection without the use of a separate WiFi access point.This will enable data 
 transfer between devices without relying on any existing network infrastructure. 
 This application is intended to provide a much higher speed alternative to Bluetooth
 file transfers. 

 Copyright (C) 2012  Teja R. Pitla
 Contact: teja.pitla@gmail.com

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.VoiceStatus;



public class ClientService extends Thread {

	private static final String TAG = "ClientService";
	OutputStream os = null;
	InputStream is = null ;
	Socket clientSocket = null;
	private boolean isConnected = false;
	InputStreamReader isr;
	BufferedReader br;
	PrintWriter pw;
	private Timer timer;
	private TimerTask task;
	
	public  ClientService() {
		super("ClientService");
		
	}
	
	private void reconnectServer(){
		System.out.println("reconnectServer to  server");
		ClientService service = new ClientService();
		service.start();
	}
	
	public void sendCommand(String command) {
        if (pw != null) {
            pw.println(command);
            pw.flush();
        }
    }
	int count = 0;
	@Override
	public void run() {
			try{
				clientSocket = new Socket("0.0.0.0", Wongs.WIFI_PORT);
				clientSocket.setKeepAlive(true);
				System.out.println("clientSocket is running");
				os = clientSocket.getOutputStream();
				is = clientSocket.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);

				pw = new PrintWriter(os);
				String inputData = "";
				pw.println(Wongs.WDFT_CLINET_HELLE);
				pw.flush();
				inputData = br.readLine();
				if(!inputData.equals(Wongs.WDFT_SERVER_HELLE))
				{
					throw new IOException("Invalid WDFT protocol message");

				}
				pw.println(Wongs.WDFT_CLIENT_READY);
				pw.flush();

				inputData = br.readLine();
				if(!inputData.equals(Wongs.WDFT_SERVER_READY))
				{
					throw new IOException("Invalid WDFT protocol message");
				}
				isConnected = true;
				System.out.println("ClientService is isConnected");
				startSendHeartBeat();
				while (true && clientSocket.isConnected()){
					//todo
					String command = br.readLine();
					System.out.println("Server command:::"+command);
					if (command.equals(Wongs.SERVER_HEART_BEAT)) {
						receiverHeartTime = System.currentTimeMillis();
					}
				}
				br.close();
				isr.close();
				is.close();
				pw.close();
				os.close();
				clientSocket.close();
			} catch (IOException e) {
				
			}
			catch(Exception e)
			{
				

			}
			

		}
	 long receiverHeartTime;
	 long sendHeartTime;
	 int time = 10 * 1000;
	 private void startSendHeartBeat(){
		 timer = new Timer();
		 task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if ((sendHeartTime -receiverHeartTime) > time) {
					System.out.println("socket is disconnect.........");
					timer.cancel();
					timer = null;
					task = null;
					try {
						clientSocket.close();
						reconnectServer();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				pw.println(Wongs.CLIENT_HEART_BEAT);
				pw.flush();
				sendHeartTime = System.currentTimeMillis();
				
			}
		};
		timer.schedule(task, 0, 6*1000);
	 }
	public void syncFile(){
		if (os == null){
			return;
		}
		File file = new File(Wongs.DATABASE_DIRECTORY_PATH);
		FileInputStream fis = null;
		BufferedInputStream bis;
		PrintWriter pw = new PrintWriter(os,true);
		try {
			for(File databaseFile : file.listFiles()) {

				pw.println(Wongs.COMMAND_SEND_FILE);
				pw.println(Wongs.COMMAND_FILE_NAME);
				pw.println(databaseFile.getName());
				pw.println(Wongs.COMMAND_FILE_LENGTH);
				pw.println(databaseFile.length()+"");

				byte[] buffer = new byte[4096];
				fis = new FileInputStream(databaseFile);
				while (true) {
					int bytesRead = fis.read(buffer, 0, buffer.length);
					if (bytesRead == -1) {
						break;
					}
					os.write(buffer, 0, bytesRead);
					os.flush();
				}
			}
		}catch (IOException e){
			
		}finally {
			try {
				fis.close();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}

	public void onDestroy()
	{
		isConnected = false;

	}

}