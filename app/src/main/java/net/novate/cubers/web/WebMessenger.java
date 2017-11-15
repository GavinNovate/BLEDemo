package net.novate.cubers.web;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * author: gavin
 * created on: 2017-11-14
 * description:网页消息收发器
 */
public class WebMessenger {

    private BridgeWebView bridgeWebView;

    private Subject<Message> responseSubject;

    public WebMessenger(BridgeWebView bridgeWebView) {
        this.bridgeWebView = bridgeWebView;
        this.responseSubject = PublishSubject.create();
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
                responseSubject.onNext(new Message(data, function));
            }
        });
    }

    /**
     * 发送数据
     *
     * @param data 发送
     */
    public void send(String data) {
        bridgeWebView.send(data);
    }

    /**
     * 接收数据
     *
     * @return 数据观察对象
     */
    public Observable<String> receive() {
        return responseSubject
                .map(new Function<Message, String>() {
                    @Override
                    public String apply(Message message) throws Exception {
                        return message.data;
                    }
                });
    }

    /**
     * 请求数据
     *
     * @param data 请求数据
     * @return 响应数据
     */
    public Observable<String> request(String data) {
        Subject<String> requestSubject = PublishSubject.create();
        bridgeWebView.send(data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                requestSubject.onNext(data);
                requestSubject.onComplete();
            }
        });
        return requestSubject;
    }

    /**
     * 响应数据
     *
     * @return 响应数据观察对象
     */
    public Observable<Message> respond() {
        return responseSubject;
    }


    public static class Message {
        public String data;
        private CallBackFunction function;

        Message(String data, CallBackFunction function) {
            this.data = data;
            this.function = function;
        }

        public void sendBack(String data) {
            function.onCallBack(data);
        }
    }
}
