package net.novate.cubers.binding;

import android.bluetooth.BluetoothAdapter;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import net.novate.cubers.ble.Bluetooth;

/**
 * author: gavin
 * create on: 2017/11/1.
 * description:
 */

public class AppBindingAdapter {

    private static final String TAG = "AppBindingAdapter";

    @BindingAdapter(value = {"noticeTextState", "noticeTextScanState"})
    public static void noticeText(TextView textView, int state, int scanState) {
        // 蓝牙状态
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                textView.setText("蓝牙未打开");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                textView.setText("蓝牙打开中");
                break;
            case BluetoothAdapter.STATE_ON:
                switch (scanState) {
                    case Bluetooth.SCAN_STATE_FAILED:
                        textView.setText("未扫描到设备");
                        break;
                    case Bluetooth.SCAN_STATE_NONE:
                        // 理论上无此提示
                        textView.setText("未扫描过");
                        break;
                    case Bluetooth.SCAN_STATE_SUCCESS:
                        // 理论上无此提示，将跳转到选择界面
                        textView.setText("已扫描到设备");
                        break;
                    case Bluetooth.SCAN_STATE_SCANNING:
                        textView.setText("扫描中");
                        break;
                }
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                textView.setText("蓝牙关闭中");
                break;
        }
    }

    @BindingAdapter(value = {"actionTextState", "actionTextScanState"})
    public static void actionText(TextView textView, int state, int scanState) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                textView.setText("打开蓝牙");
                break;
            case BluetoothAdapter.STATE_ON:
                switch (scanState) {
                    case Bluetooth.SCAN_STATE_FAILED:
                        textView.setText("重新扫描");
                        break;
                    case Bluetooth.SCAN_STATE_NONE:
                        textView.setText("开始扫描");
                        break;
                }
                break;
        }
    }

    @BindingAdapter(value = {"actionShowState", "actionShowScanState"})
    public static void actionShow(TextView textView, int state, int scanState) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                textView.setVisibility(View.VISIBLE);
                break;
            case BluetoothAdapter.STATE_ON:
                switch (scanState) {
                    case Bluetooth.SCAN_STATE_FAILED:
                    case Bluetooth.SCAN_STATE_NONE:
                        textView.setVisibility(View.VISIBLE);
                        break;
                    case Bluetooth.SCAN_STATE_SUCCESS:
                    case Bluetooth.SCAN_STATE_SCANNING:
                        textView.setVisibility(View.GONE);
                        break;
                }
                break;
            default:
                textView.setVisibility(View.GONE);
                break;
        }
    }
}
