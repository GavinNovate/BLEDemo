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
public class WebMessenger {

    private BridgeWebView bridgeWebView;

    public WebMessenger(BridgeWebView bridgeWebView) {
        this.bridgeWebView = bridgeWebView;
    }

    /**
     * 加载网页地址
     *
     * @param url 网页地址
     */
    public void loadUrl(String url, WebService service) {
        bridgeWebView.loadUrl(url);
        bridgeWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(service.onReceive(url, data));
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
