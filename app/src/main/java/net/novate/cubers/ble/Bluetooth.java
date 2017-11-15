package net.novate.cubers.ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
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

@SuppressWarnings("UnusedReturnValue")
public class Bluetooth {

    private static final String TAG = "Bluetooth";

    // 扫描失败
    public static final int SCAN_STATE_FAILED = -1;
    // 未扫描过
    public static final int SCAN_STATE_NONE = 0;
    // 扫描成功(扫描到合适设备才算成功)
    public static final int SCAN_STATE_SUCCESS = 1;
    // 正在扫描
    public static final int SCAN_STATE_SCANNING = 2;
    // 已扫描到一个设备
    public static final int SCAN_STATE_FOUND = 3;


    private BluetoothAdapter bluetoothAdapter;

    // 蓝牙状态监听器
    private Subject<Integer> blueStateSubject;

    // 扫描状态
    private int scanState = SCAN_STATE_NONE;
    // 扫描状态监听器
    private Subject<Integer> scanStateSubject;
    // 扫描程序定时器
    private Observable<Long> scannerTimer;
    // 已扫描到的设备
    private List<BluetoothDevice> scannedDevices = new ArrayList<>();
    // 扫描设备监听器
    private Subject<List<BluetoothDevice>> scannedDevicesSubject;

    private Bluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        blueStateSubject = PublishSubject.create();
        scanStateSubject = PublishSubject.create();
        scannedDevicesSubject = PublishSubject.create();
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

    /**
     * 获取扫描状态
     *
     * @return 扫描状态
     */
    public int getScanState() {
        return scanState;
    }

    /**
     * 观察扫描状态
     *
     * @return 扫描状态监听器
     */
    public Observable<Integer> observeScanState() {
        return scanStateSubject;
    }

    /**
     * 开启扫描
     */
    public Observable<List<BluetoothDevice>> scan() {
        if (scannerTimer == null) {
            // 开始扫描
            scanState = SCAN_STATE_SCANNING;
            scanStateSubject.onNext(scanState);
            IScanCallback iScanCallback = new IScanCallback();
            bluetoothAdapter.getBluetoothLeScanner().startScan(iScanCallback);

            // 定时关闭扫描
            scannerTimer = Observable.timer(15, TimeUnit.SECONDS);
            scannerTimer.subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    bluetoothAdapter.getBluetoothLeScanner().stopScan(iScanCallback);
                    if (scannedDevices.size() > 0) {
                        scanState = SCAN_STATE_SUCCESS;
                    } else {
                        scanState = SCAN_STATE_FAILED;
                    }
                    scanStateSubject.onNext(scanState);
                    scannerTimer = null;
                }
            });
        }
        return scannedDevicesSubject;
    }

    /**
     * 获取已扫描到的蓝牙设备
     *
     * @return 蓝牙设备
     */
    public List<BluetoothDevice> getScannedBluetoothDevices() {
        return scannedDevices;
    }

    public Observable<List<BluetoothDevice>> observeScanedBluetoothDevices() {
        return scannedDevicesSubject;
    }

    /**
     * 连接远程设备
     *
     * @param context     context
     * @param autoConnect autoConnect
     * @param address     address
     */
    public BluetoothConnector connect(Context context, boolean autoConnect, String address) {
        BluetoothConnector connector = new BluetoothConnector();
        bluetoothAdapter.getRemoteDevice(address).connectGatt(context, autoConnect, connector);
        return connector;
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
     * 扫描回调接口
     */
    class IScanCallback extends ScanCallback {

        private Map<String, BluetoothDevice> deviceMap = new HashMap<>();

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if (result.getDevice().getName() != null) {
                int oldSize = deviceMap.size();
                deviceMap.put(result.getDevice().getAddress(), result.getDevice());
                int newSize = deviceMap.size();
                if (newSize > oldSize) {
                    scannedDevices.clear();
                    scannedDevices.addAll(getDevices());
                    scannedDevicesSubject.onNext(scannedDevices);
                    if (newSize == 1) {
                        scanStateSubject.onNext(SCAN_STATE_FOUND);
                    }
                    Log.d(TAG, "onScanResult: " + scannedDevices.get(0).getName());
                }
            }
        }

        List<BluetoothDevice> getDevices() {
            return new ArrayList<>(deviceMap.values());
        }
    }

    /**
     * 单例持有类
     */
    private static class SingletonHolder {
        private static final Bluetooth INSTANCE = new Bluetooth();
    }
}
