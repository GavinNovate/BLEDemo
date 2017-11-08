package net.novate.cubers.viewmodel;

import android.app.Application;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import net.novate.cubers.base.BaseViewModel;

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
    public static final int STATE_SCANNING_FAILED = 6;
    public static final int STATE_SCANNING_SUCCESS = 5;


    private ObservableInt state;

    public ScanViewModel(@NonNull Application application) {
        super(application);
        state = new ObservableInt();
    }

    public ObservableInt getState() {
        return state;
    }
}
