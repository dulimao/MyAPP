package com.example.myapp.ipc.socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "TCPClientActivity";

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button mSendButton;
    private Button mStartServiceButton;
    private Button mLinkServerButton;
    private TextView mMessageTextView;
    private EditText mMessgeEditText;


    private PrintWriter mPrintWriter;
    private Socket mClientSocket;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    mMessageTextView.setText(mMessgeEditText.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendButton.setEnabled(true);
                    mMessageTextView.setText("服务器连接成功");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = findViewById(R.id.tv_message);
        mMessgeEditText = findViewById(R.id.edit_message);
        mSendButton = findViewById(R.id.btn_send);
        mSendButton.setEnabled(false);
        mSendButton.setOnClickListener(this);
        mStartServiceButton = findViewById(R.id.btn_start_service);
        mLinkServerButton = findViewById(R.id.btn_link_server);
        mStartServiceButton.setOnClickListener(this);
        mLinkServerButton.setOnClickListener(this);

    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                Log.d(TAG, "开始连接服务器");
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())));
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d("TCPClientActivity", "connect server success: ");
            } catch (IOException e) {
                //如果连接失败，睡眠1秒后重新连接
                SystemClock.sleep(1000);
                Log.d("TCPClientActivity", "connect server fail 尝试重新连接: " + e.getMessage());
            }

        }


        //接收服务端的消息
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()) {
                Log.d(TAG, "等待服务端消息。。。");
                String msg = br.readLine();
                Log.d(TAG, "receive msg: " + msg);
                if (msg != null) {
                    String time = formatDataTime(System.currentTimeMillis());
                    final String showedMsg = "server " + time + " : " + msg + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                }
            }

            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String formatDataTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("(HH:mm:ss)");
        return format.format(new Date(time));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == mSendButton) {
            final String msg = mMessgeEditText.getText().toString();
            if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPrintWriter.println(msg);
                        mPrintWriter.flush();
                    }
                }).start();

                mMessgeEditText.setText("");
                String time = formatDataTime(System.currentTimeMillis());
                final String showedMsg = "self " + time + ":" + msg + "\n";
                mMessageTextView.setText(mMessageTextView.getText() + showedMsg);
            }
        } else if (v == mStartServiceButton) {
            Intent intent = new Intent(this, TCPServerService.class);
            startService(intent);
            Log.d(TAG, "开启服务成功");
        } else if (v == mLinkServerButton) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    connectTCPServer();
                }
            }).start();
        }
    }
}
