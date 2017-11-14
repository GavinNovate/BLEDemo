package net.novate.cubers;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * author: gavin
 * created on: 2017-11-14
 * description:网页消息收发器
 */
@SuppressWarnings("WeakerAccess")
public class WebService {

    private BridgeWebView bridgeWebView;

    public WebService(BridgeWebView bridgeWebView) {
        this.bridgeWebView = bridgeWebView;
    }

    /**
     * 加载网页地址
     *
     * @param url 网页地址
     */
    public void loadUrl(String url) {
        bridgeWebView.loadUrl(url);
        bridgeWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

            }
        });
    }

    public Observable<String> send(String data) {
        Subject<String> responseSubject = PublishSubject.create();
        bridgeWebView.send(data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                responseSubject.onNext(data);
                responseSubject.onComplete();
            }
        });
        return responseSubject;
    }
}
