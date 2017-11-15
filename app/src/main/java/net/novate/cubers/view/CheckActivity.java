package net.novate.cubers.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.novate.cubers.R;
import net.novate.cubers.databinding.CheckActivityBinding;
import net.novate.cubers.view.event.CheckActivityEvent;
import net.novate.cubers.web.WebMessenger;

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
    }

    private void init() {

        WebMessenger messenger = new WebMessenger(binding.webView);
        messenger.loadUrl("file:///android_asset/test.html");

        // 接收数据
        messenger.receive().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: Receive:" + s);
            }
        });

        // 响应数据
        messenger.respond().subscribe(new Consumer<WebMessenger.Message>() {
            @Override
            public void accept(WebMessenger.Message message) throws Exception {
                Log.d(TAG, "accept: Respond:" + message.data);
                // 回传
                message.sendBack("Send Back");
            }
        });

        binding.setEvent(new CheckActivityEvent() {
            @Override
            public void onClick() {
                // 请求数据
                messenger.request("Request Data")
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                            }
                        });
            }

            @Override
            public void onDefault() {
                // 发送数据
                messenger.send("Send Data");
            }
        });
    }
}
