package net.novate.cubers.viewmodel;

import android.Manifest;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothAdapter;
import android.support.annotation.NonNull;

import com.anthonycr.grant.PermissionsManager;

import net.novate.cubers.base.BaseViewModel;
import net.novate.cubers.ble.Bluetooth;

/**
 * author: gavin
 * create on: 2017/11/7.
 * description:
 */

public class ScanViewModel extends BaseViewModel {

    public static final int STATE_UN_SUPPORT = 0;
    public static final int STATE_UN_SUPPORT_BLE = 1;
    public static final int STATE_BLUETOOTH_OFF = 2;
    public static final int STATE_NO_PERMISSION = 3;
    public static final int STATE_SCANNING = 4;
    public static final int STATE_SCANNING_FAILED = 5;
    public static final int STATE_SCANNING_SUCCESS = 6;


    // 状态
    private MutableLiveData<Integer> stateLiveData;

    private Bluetooth bluetooth;

    public ScanViewModel(@NonNull Application application) {
        super(application);
        stateLiveData = new MutableLiveData<>();
        bluetooth = Bluetooth.get();
    }

    public void init() {
        updateState();
    }


    // ---- 获取数据 ----

    /**
     * 获取状态
     *
     * @return 状态
     */
    public LiveData<Integer> getState() {
        return stateLiveData;
    }

    // ---- 操作指令 ----


    // ---- 私有方法 ----
    private void updateState() {
        if (bluetooth == null) {
            stateLiveData.postValue(STATE_UN_SUPPORT);
        } else if (!bluetooth.isSupportBle(getApp())) {
            stateLiveData.postValue(STATE_UN_SUPPORT_BLE);
        } else if (!(bluetooth.getState() == BluetoothAdapter.STATE_ON)) {
            stateLiveData.postValue(STATE_BLUETOOTH_OFF);
        } else if (!PermissionsManager.hasPermission(getApp(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            stateLiveData.postValue(STATE_NO_PERMISSION);
        } else {
            stateLiveData.postValue(STATE_SCANNING);
        }
    }

    @Override
    protected void onCleared() {

    }
}
