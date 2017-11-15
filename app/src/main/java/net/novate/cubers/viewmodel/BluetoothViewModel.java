package net.novate.cubers.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

import net.novate.cubers.base.BaseViewModel;
import net.novate.cubers.ble.Bluetooth;
import net.novate.cubers.ble.BluetoothConnector;
import net.novate.cubers.model.Device;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author: gavin
 * create on: 2017/11/3.
 * description:
 */

public class BluetoothViewModel extends BaseViewModel {

    private static final String TAG = "BluetoothViewModel";

    private Bluetooth bluetooth;
    private BluetoothConnector connector;

    // 蓝牙状态
    private MutableLiveData<Integer> stateLiveData;
    // 扫描状态
    private MutableLiveData<Integer> scanStateLiveData;
    // 连接状态
    private MutableLiveData<Integer> connectStateLiveData;
    // 扫描到的设备数据
    private MutableLiveData<List<Device>> scannedDevicesLiveData;

    // 绑定句柄
    private List<Disposable> disposableList = new ArrayList<>();

    public BluetoothViewModel(@NonNull Application application) {
        super(application);

        bluetooth = Bluetooth.get();
        stateLiveData = new MutableLiveData<>();
        scanStateLiveData = new MutableLiveData<>();
        connectStateLiveData = new MutableLiveData<>();
        scannedDevicesLiveData = new MutableLiveData<>();
    }

    public void init() {

        // 查询蓝牙状态
        stateLiveData.postValue(bluetooth.getState());
        // 查询蓝牙扫描状态
        scanStateLiveData.postValue(bluetooth.getScanState());
        // 查询已扫描到的设备
        scannedDevicesLiveData.postValue(bluetoothDevicesToDevices(bluetooth.getScannedBluetoothDevices()));
        // TODO: 17-11-3 连接状态

        // 监听蓝牙状态
        bluetooth.observeState().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableList.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                stateLiveData.postValue(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 监听蓝牙扫描状态
        bluetooth.observeScanState().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableList.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                scanStateLiveData.postValue(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 监听已扫描到的设备
        bluetooth.observeScanedBluetoothDevices().subscribe(new Observer<List<BluetoothDevice>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableList.add(d);
            }

            @Override
            public void onNext(List<BluetoothDevice> bluetoothDevices) {
                scannedDevicesLiveData.postValue(bluetoothDevicesToDevices(bluetoothDevices));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void autoScan() {
        // 蓝牙为打开状态，且未扫描过执行自动扫描操作
        if (bluetooth.getState() == BluetoothAdapter.STATE_ON && bluetooth.getScanState() == Bluetooth.SCAN_STATE_NONE) {
            bluetooth.scan();
        }
    }

    /**
     * 获取蓝牙状态
     *
     * @return 蓝牙状态
     */
    public LiveData<Integer> getState() {
        return stateLiveData;
    }

    /**
     * 获取扫描状态
     *
     * @return 扫描状态
     */
    public LiveData<Integer> getScanState() {
        return scanStateLiveData;
    }

    /**
     * 获取连接状态
     *
     * @return 连接状态
     */
    public LiveData<Integer> getConnectState() {
        return connectStateLiveData;
    }

    public LiveData<List<Device>> getScannedDevices() {
        return scannedDevicesLiveData;
    }


    /**
     * 操作指令
     *
     * @param state     蓝牙状态
     * @param scanState 蓝牙扫描状态
     */
    public void action(int state, int scanState) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                bluetooth.turnOn();
                break;
            case BluetoothAdapter.STATE_ON:
                switch (scanState) {
                    case Bluetooth.SCAN_STATE_FAILED:
                    case Bluetooth.SCAN_STATE_NONE:
                        bluetooth.scan();
                        break;
                }
                break;
        }
    }

    /**
     * 连接
     *
     * @param address 地址
     */
    public void connect(String address) {
        connector = bluetooth.connect(getApp(), false, address);
        // 观察连接状态
        connector.observeConnectState()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        connectStateLiveData.postValue(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 将 BluetoothDevices 转化为 Devices
     *
     * @param bluetoothDevices BluetoothDevices
     * @return Devices
     */
    private List<Device> bluetoothDevicesToDevices(List<BluetoothDevice> bluetoothDevices) {
        List<Device> devices = new ArrayList<>();
        for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
            Device device = new Device();
            device.setName(bluetoothDevice.getName());
            device.setAddress(bluetoothDevice.getAddress());
            devices.add(device);
        }
        return devices;
    }


    @Override
    protected void onCleared() {
        Iterator<Disposable> iterator = disposableList.iterator();
        while (iterator.hasNext()) {
            Disposable disposable = iterator.next();
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
            iterator.remove();
        }
    }
}
