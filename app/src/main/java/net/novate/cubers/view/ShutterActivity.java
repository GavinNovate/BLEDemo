package net.novate.cubers.view;

import android.content.Intent;
import android.os.Bundle;

import net.novate.cubers.R;
import net.novate.cubers.base.BaseActivity;
import net.novate.cubers.ble.Bluetooth;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * author: gavin
 * create on: 2017/10/28.
 * description:闪屏页
 */

public class ShutterActivity extends BaseActivity implements Observer<Long> {

    private static final String TAG = "ShutterActivity";

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shutter_activity);

        Observable
                .timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(Long aLong) {
        Bluetooth bluetooth = Bluetooth.get();

        if (bluetooth == null) {
            // 设备不支持蓝牙 - 转到 SupportActivity

            Intent intent = new Intent(this, SupportActivity.class);
            intent.putExtra(SupportActivity.CODE, SupportActivity.UN_SUPPORT);
            startActivity(intent);
        } else if (!bluetooth.isSupportBle(this)) {
            // 设备不支持BLE蓝牙 - 转到 SupportActivity

            Intent intent = new Intent(this, SupportActivity.class);
            intent.putExtra(SupportActivity.CODE, SupportActivity.UN_SUPPORT_BLE);
            startActivity(intent);
        } else if (false) {
            // 已连接过 - 转到 ConnectActivity
            // TODO: 17-11-3

        } else {
            // 未连接过 - 转到 ScanActivity

            startActivity(new Intent(this, ScanActivity.class));
        }
        finish();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
