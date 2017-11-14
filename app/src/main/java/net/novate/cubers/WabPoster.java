package net.novate.cubers;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * author: gavin
 * created on: 2017-11-14
 * description:
 */
public class WabPoster {

    BridgeWebView webView;
    FlowableProcessor<String> flowable = PublishProcessor.create();

    public WabPoster(BridgeWebView webView) {
        this.webView = webView;
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);

        webView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                // data 为 wabView 发过来的数据
                flowable.onNext(data);
                function.onCallBack("回传给webView的数据");
            }
        });

        // data 为发送给webView的数据
        webView.send("data", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                // data 为 web 回传回来的数据
            }
        });
    }

    public void send(String data) {
        webView.send(data);
    }

    public Flowable<String> receive() {
        return flowable;
    }
}
