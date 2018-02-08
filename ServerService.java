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



import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


public class ServerService extends Thread{
    private static final String TAG = "ServerService";
    private volatile boolean serverEnable;
    ServerSocket welcomeSocket = null;
    int count = 0;
  
    public ServerService() {
        super("ServerService");
        serverEnable = true;
    }
    

    private SocketThread socketThread;

    @Override
    public void run() {		
        try {
            welcomeSocket = new ServerSocket(Wongs.WIFI_PORT);
            System.out.println("ServerService is runnning in::"+welcomeSocket.getLocalSocketAddress().toString());
            while (serverEnable) {
                Socket socket = welcomeSocket.accept();
                socketThread = new SocketThread(socket);
                socketThread.start();
                count++;
                System.out.println("client socket count:::"+count);

            }
        } catch (IOException e) {
        	System.out.println("IOException::"+e.toString());
        }
    }


    protected void onDestroy() {
        serverEnable = false;
        try {
            if (!welcomeSocket.isClosed())
                welcomeSocket.close();
            socketThread.onDestory();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * handle a client socket
     */
    private class SocketThread extends Thread {

        private Socket socket;
        private InputStream is;
        private OutputStream os;
        private PrintWriter pw;
        private volatile boolean isConnect;
        InputStreamReader isr;
        BufferedReader br;
        long receivedHeartBeat;
        public SocketThread(Socket s) {
            this.socket = s;
            isConnect = true;
        }

        @Override
        public void run() {
            //Listen for incoming connections on specified por
            // Block thread until someone connects
            try {
                is = socket.getInputStream();
                socket.setKeepAlive(true);
                os = socket.getOutputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                pw = new PrintWriter(os, true);
                String inputData = "";

                inputData = br.readLine();
                if (!inputData.equals(Wongs.WDFT_CLINET_HELLE)) {
                    throw new IOException("Invalid WDFT protocol message");
                }

                pw.println(Wongs.WDFT_SERVER_HELLE);
                pw.flush();

                inputData = br.readLine();
                if (!inputData.equals(Wongs.WDFT_CLIENT_READY)) {
                    throw new IOException("Invalid WDFT protocol message");

                }
                pw.println(Wongs.WDFT_SERVER_READY);
                pw.flush();
                System.out.println("SocketThread is isConnected");
                checkHeartBeat();
                int count = 0;
                while (isConnect) {
                    if (socket.isClosed() || !socket.isConnected()) {
                        break;
                    }        
                    String command = br.readLine();
                    System.out.println("Client command:::"+command);
                    if (Wongs.CLIENT_HEART_BEAT.equals(command)) {
                    	receivedHeartBeat = System.currentTimeMillis();
						pw.println(Wongs.SERVER_HEART_BEAT);
						
					}               
                    
                    /*if (command.equals(Wongs.COMMAND_SEND_FILE)) {
                        String fileName = br.readLine();
                        String fileLength = br.readLine();
                        long fileSize = Long.valueOf(fileLength);
                        File file = new File(Wongs.DATABASE_DIRECTORY_PATH, fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while (fileSize > 0) {
                            bytesRead = is.read(buffer, 0, buffer.length);
                            fos.write(buffer, 0, bytesRead);
                            fos.flush();
                            fileSize -= bytesRead;
                        }
                        fos.close();
                    } else if (command.equals(Wongs.COMMAND_SYNC_FILE)) {
                      
                    }*/

                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                	
                    os.close();
                    pw.close();
                    br.close();
                    isr.close();
                    is.close();
                    timer.cancel();
                    timer = null;
                    task = null;
                    socket.close();
                    System.out.println("Client socket close");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
     private Timer timer;
     private TimerTask task;
   	 long currentTime;
   	 int time = 10 * 1000;
   	 private void checkHeartBeat(){
   		 timer = new Timer();
   		receivedHeartBeat = System.currentTimeMillis();
   		 task = new TimerTask() {
   			
   			@Override
   			public void run() {
   				// TODO Auto-generated method stub
   				currentTime = System.currentTimeMillis();
   				if (currentTime - receivedHeartBeat > time) {
   					System.out.println("socket is disconnect.........");
				}
   				
   				
   			}
   		};
   		timer.schedule(task, 0, 6*1000);
   	 }

        public void sendCommand(String command) {
            if (pw != null) {
                pw.println(command);
            }
        }

        protected void onDestory(){
            isConnect = false;
            if (socket != null && !socket.isClosed()){
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
