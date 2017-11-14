package net.novate.cubers.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.novate.cubers.R;
import net.novate.cubers.WabPoster;
import net.novate.cubers.databinding.CheckActivityBinding;
import net.novate.cubers.view.event.CheckActivityEvent;

import io.reactivex.functions.Consumer;

/**
 * 姿态校验页
 * http://blog.csdn.net/sk719887916/article/details/47189607
 */
public class CheckActivity extends AppCompatActivity {

    private static final String TAG = "CheckActivity";

    private CheckActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.check_activity);

        init();

//        binding.webView.loadUrl("file:///android_asset/test.html");
//        binding.webView.setDefaultHandler(new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
////                Log.e(TAG, "DefaultHandler接收全部来自web的数据：" + data);
//                Log.d(TAG, "handler: 接收到的数据:" + data);
//                function.onCallBack("DefaultHandler收到Web发来的数据，回传数据给你");
//            }
//        });
//
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        //第一参数是订阅的java本地函数名字 第二个参数是回调Handler , 参数返回js请求的resqustData,function.onCallBack（）回调到js，调用function(responseData)
//        binding.webView.registerHandler("submitFromWeb", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Log.e(TAG, "指定Handler接收来自web的数据：" + data);
//                function.onCallBack("指定Handler收到Web发来的数据，回传数据给你");
//            }
//        });
//
//        binding.setEvent(new CheckActivityEvent() {
//            @Override
//            public void onClick() {
////                binding.webView.callHandler("functionInJs", "发送数据给web指定接收", new CallBackFunction() {
////                    @Override
////                    public void onCallBack(String data) {
////                        Log.e(TAG, "来自web的回传数据：" + data);
////                    }
////                });
//            }
//
//            @Override
//            public void onDefault() {
////                binding.webView.send("发送数据给web默认接收 SEND", new CallBackFunction() {
////                    @Override
////                    public void onCallBack(String data) {
////                        Log.e(TAG, "来自web的回传数据：" + data);
////                    }
////                });
//                binding.webView.send("发送的数据", new CallBackFunction() {
//                    @Override
//                    public void onCallBack(String data) {
//                        // 收到的回传
//                    }
//                });
//            }
//        });
    }

    private void init() {
        WabPoster poster = new WabPoster(binding.webView);
        poster.loadUrl("file:///android_asset/test.html");

        poster.receive().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });

        binding.setEvent(new CheckActivityEvent() {
            @Override
            public void onClick() {
                poster.send("发送数据1111");
            }

            @Override
            public void onDefault() {
                poster.send("发送数据2222");
            }
        });
    }
}
