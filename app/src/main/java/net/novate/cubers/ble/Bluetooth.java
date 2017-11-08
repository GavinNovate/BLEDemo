package net.novate.cubers.ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * author: gavin
 * create on: 2017/10/30.
 * description:
 * <p>
 * 蓝牙
 * <p>
 * 1.获取蓝牙，不支持返回NUll #
 * 2.获取蓝牙状态 观察蓝牙状态 打开蓝牙 关闭蓝牙 #
 * 3.
 * 4.
 */

public class Bluetooth {

    private static final String TAG = "Bluetooth";

    private BluetoothAdapter bluetoothAdapter;

    // 蓝牙状态监听器
    private Subject<Integer> blueStateSubject;

    private Bluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        blueStateSubject = PublishSubject.create();
    }

    /**
     * 获取蓝牙实例
     *
     * @return 蓝牙实例，为空表示设备不支持蓝牙
     */
    @Nullable
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static Bluetooth get() {
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            return null;
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 设备是否支持BLE
     *
     * @param context context
     * @return 是否支持
     */
    public boolean isSupportBle(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * 获取蓝牙状态
     *
     * @return 蓝牙状态
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public int getState() {
        return bluetoothAdapter.getState();
    }

    /**
     * 观察蓝牙状态
     *
     * @return 蓝牙状态观察对象
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public Observable<Integer> observeState() {
        return blueStateSubject;
    }

    /**
     * 打开蓝牙
     *
     * @return 是否操作成功（操作成功不代表蓝牙打开成功，蓝牙状态需要另外监听）
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public boolean turnOn() {
        return bluetoothAdapter.enable();
    }

    /**
     * 关闭蓝牙
     *
     * @return 是否操作成功（操作成功不代表蓝牙关闭成功，蓝牙状态需要另外监听）
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public boolean turnOff() {
        return bluetoothAdapter.disable();
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    /**
     * 开启扫描
     */
    public void startScan() {
        bluetoothAdapter.getBluetoothLeScanner().startScan(scanCallback);
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
    }

    /**
     * 连接远程设备
     *
     * @param context     context
     * @param autoConnect autoConnect
     * @param device      device
     */
    public void connect(Context context, boolean autoConnect, BluetoothDevice device) {
        device.connectGatt(context, autoConnect, new BluetoothGattCallback() {
        });
    }

    /**
     * 蓝牙状态改变
     *
     * @param state 状态
     */
    void onStateChanged(int state) {
        if (blueStateSubject.hasObservers()) {
            blueStateSubject.onNext(state);
        }
    }

    /**
     * 单例持有类
     */
    private static class SingletonHolder {
        private static final Bluetooth INSTANCE = new Bluetooth();
    }
}
