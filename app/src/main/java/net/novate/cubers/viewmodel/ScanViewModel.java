package net.novate.cubers.viewmodel;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.databinding.ObservableInt;
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


    private ObservableInt state;

    private Bluetooth bluetooth;

    public ScanViewModel(@NonNull Application application) {
        super(application);
        state = new ObservableInt();
        bluetooth = Bluetooth.get();
    }

    public void init() {
        if (bluetooth == null) {
            state.set(STATE_UN_SUPPORT);
        } else if (!bluetooth.isSupportBle(getApp())) {
            state.set(STATE_UN_SUPPORT_BLE);
        } else if (!(bluetooth.getState() == BluetoothAdapter.STATE_ON)) {
            state.set(STATE_BLUETOOTH_OFF);
        } else if (!PermissionsManager.hasPermission(getApp(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            state.set(STATE_NO_PERMISSION);
        } else {
            state.set(STATE_SCANNING);
        }
    }

    public ObservableInt getState() {
        return state;
    }
}
