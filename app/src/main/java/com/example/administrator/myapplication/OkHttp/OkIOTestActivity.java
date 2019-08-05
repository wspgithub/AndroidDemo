package com.example.administrator.myapplication.OkHttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

@ShowActivity
public class OkIOTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PORT = 9999;
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;
    private String sendMsg;


    private ExecutorService mExecutorClient = null;
    private EditText mSendEditText;
    private TextView mShowTextView;
    private String HOST = "192.168.23.1";
    private BufferedSink mSink;
    private BufferedSource mSource;

    private MyHandler myHandler;
    
    private TextView startServiceTv;
    private TextView connectServiceTv;
    private TextView sentMessageTv;
    private TextView disConnectServiceTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okio_test_activity);
        initUI();
        myHandler = new MyHandler();
        mExecutorClient = Executors.newCachedThreadPool();
    }
    
    private void initUI(){

        mSendEditText = findViewById(R.id.mSendEditText);
        mShowTextView = findViewById(R.id.mShowTextView);

        startServiceTv = findViewById(R.id.startService);
        startServiceTv.setOnClickListener(this);
        connectServiceTv = findViewById(R.id.connectService);
        connectServiceTv.setOnClickListener(this);
        sentMessageTv = findViewById(R.id.sentMessage);
        sentMessageTv.setOnClickListener(this);
        disConnectServiceTv = findViewById(R.id.disConnectService);
        disConnectServiceTv.setOnClickListener(this);

    }

//------------------服务端相关---------------------------------------------------------------------------------
    private void startService(){
        try {
            server = new ServerSocket(PORT);
            mExecutorService = Executors.newCachedThreadPool();
             Log.e("okio","服务器已启动...");
             Message message = Message.obtain();
             message.what = 0;
             myHandler.sendMessage(message);
            Socket client = null;
            while (true) {
                //等待一个socket访问
                client = server.accept();
                mList.add(client);
                mExecutorService.execute(new Service(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


    class Service implements Runnable {
        private Socket socket;
        private BufferedSink mSink;
        private BufferedSource mSource;

        public Service(Socket socket) {
            this.socket = socket;
            try {
                mSink = Okio.buffer(Okio.sink(socket));
                mSource = Okio.buffer(Okio.source(socket));
                Message message = Message.obtain();
                message.what = 1;
                myHandler.sendMessage(message);
                sendMsg="成功连接服务器" + "(服务器发送)";
                mSink.writeUtf8(sendMsg+"\n");
                mSink.flush();
                 Log.e("okio","成功连接服务器");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    for (String receiveMsg; (receiveMsg = mSource
                            .readUtf8Line()) != null;) {
                             Log.e("okio","receiveMsg:" + receiveMsg);
                        if (receiveMsg.equals("0")) {
                              Log.e("okio","客户端请求断开连接");
                            Message message = Message.obtain();
                            message.what = 2;
                            myHandler.sendMessage(message);
                            mSink.writeUtf8("服务端断开连接" + "（服务器发送）");
                            mSink.flush();
                            mList.remove(socket);
                            socket.close();
                            break;
                        } else {
                            sendMsg = "我已接收：" + receiveMsg + "（服务器发送）";
                            mSink.writeUtf8(sendMsg+"\n");
                            mSink.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//---------------------------------------------------------------------------------------------------

    public void connect() {
        mExecutorClient.execute(new connectService());
    }

    public void send() {
        String sendMsg = mSendEditText.getText().toString();
        mExecutorClient.execute(new sendService(sendMsg));
    }

    public void disconnect() {
        mExecutorClient.execute(new sendService("0"));
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                mSink.writeUtf8(this.msg+"\n");
                mSink.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class connectService implements Runnable {
        @Override
        public void run() {
            try {
                HOST = getHostIP();
                Log.e("okio",HOST);
                Socket socket = new Socket(HOST, PORT);
                mSink = Okio.buffer(Okio.sink(socket));
                mSource = Okio.buffer(Okio.source(socket));
                receiveMsg();
            } catch (Exception e) {
                Log.e("okio", ("okio-connectService:" + e.getMessage()));
            }
        }
    }

    private void receiveMsg() {
        try {
            while (true) {
                for (String receiveMsg; (receiveMsg = mSource.readUtf8Line()) != null; ) {
                    Log.d("okio", "receiveMsg:" + receiveMsg);
                    final String finalReceiveMsg = receiveMsg;
                    Message message = Message.obtain();
                    message.what = 3;
                    message.obj = finalReceiveMsg + "\n\n" + mShowTextView.getText();
                    myHandler.sendMessage(message);
                }
            }
        } catch (IOException e) {
            Log.e("okio", "receiveMsg: ");
            e.printStackTrace();
        }
    }


//-------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startService:

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        startService();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                break;
            case R.id.connectService:
                connect();
                break;
            case R.id.sentMessage:
                send();
                break;
            case R.id.disConnectService:
                disconnect();
                break;
            default:break;
        }
    }


    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("okio", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }


    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mShowTextView.setText("服务器已启动...");
                    break;
                case 1:
                    mShowTextView.setText("服务器已连接...");
                    break;
                case 2:
                    mShowTextView.setText("服务器已断开...");
                    break;
                case 3:
                    mShowTextView.setText((String)msg.obj);
                    break;
                default:break;
            }
        }
    }

//    private void test(){
//        try {
//            Socket socket = new Socket();
//            Okio.buffer(Okio.sink(socket));
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sink.writeUtf8(requestLine).writeUtf8("\r\n");
//    }
}
