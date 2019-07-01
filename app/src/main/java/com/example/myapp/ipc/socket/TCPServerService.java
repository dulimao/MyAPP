package com.example.myapp.ipc.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Sokcet实现进程间通信
 */
public class TCPServerService extends Service {
    private static final String TAG = "TCPServerService";

    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessage = new String[]{
            "你好，哈哈", "请问你叫什么名字啊", "今天的天气不错啊"
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");
        new Thread(new TcpServer()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
                Log.d(TAG,"ServerSocket start");
                while (!mIsServiceDestoryed) {
                    Log.d(TAG, "wait client accept!");
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "client accept!");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG,"服务器启动失败： " + e.getMessage());
            }
        }
    }

    private void responseClient(Socket socket) throws IOException {
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())));

        out.println("欢迎来到聊天室!");
        out.flush();
        while (!mIsServiceDestoryed) {
            Log.d(TAG,"等待客户端发送消息");
            String msg = in.readLine();
            Log.d(TAG,"msg from client : " + msg);
            if (msg == null) {//客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessage.length);
            String replyMsg = mDefinedMessage[i];
            out.println(replyMsg);
            out.flush();
            Log.d(TAG,"send to client: " + replyMsg);
        }

        out.close();
        in.close();
        socket.close();

    }


}
