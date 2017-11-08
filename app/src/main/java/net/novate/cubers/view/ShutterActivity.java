package net.novate.cubers.view;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anthonycr.grant.PermissionsManager;

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
 * <p>
 * 1.不支持BLE蓝牙 -> 扫描页 - 不支持
 * 3.蓝牙未打开 -> 扫描页 -> 未打开
 * 4.无扫描权限 -> 扫描页 - 无权限
 * 4.未连接过 -> 扫描页
 * 5.连接过 ->  连接页 - 尝试连接上一次的地址
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
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(Long aLong) {
        Bluetooth bluetooth = Bluetooth.get();
        if (bluetooth == null) {
            // TODO: 2017/11/1  设备不支持蓝牙
            Log.d(TAG, "onNext: 设备不支持蓝牙");
        } else if (!bluetooth.isSupportBle(this)) {
            // TODO: 2017/11/1 设备不支持BLE蓝牙
            Log.d(TAG, "onNext: 设备不支持BLE蓝牙");
        } else if (!(bluetooth.getState() == BluetoothAdapter.STATE_ON)) {
            // TODO: 2017/11/1 蓝牙未打开
            Log.d(TAG, "onNext: 蓝牙未打开");

        } else if (!PermissionsManager.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // TODO: 2017/11/1 无扫描权限
            Log.d(TAG, "onNext: 无扫描权限");

//        }else if (){
            // TODO: 2017/11/1 未连接过
//        }else if (){
            // TODO: 2017/11/1 连接过，直接连接
        }
        startActivity(new Intent(this, ScanActivity.class));
        finish();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
