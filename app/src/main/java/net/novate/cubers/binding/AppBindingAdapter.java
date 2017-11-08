package net.novate.cubers.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import net.novate.cubers.R;
import net.novate.cubers.viewmodel.ScanViewModel;

/**
 * author: gavin
 * create on: 2017/11/8.
 * description:
 */

public class AppBindingAdapter {

    @BindingAdapter(value = "noticeText")
    public static void noticeText(TextView textView, int state) {
        switch (state) {
            case ScanViewModel.STATE_UN_SUPPORT:
                textView.setText(R.string.notice_un_support);
                break;
            case ScanViewModel.STATE_UN_SUPPORT_BLE:
                textView.setText(R.string.notice_un_support_ble);
                break;
            case ScanViewModel.STATE_BLUETOOTH_OFF:
                textView.setText(R.string.notice_bluetooth_off);
                break;
            case ScanViewModel.STATE_NO_PERMISSION:
                textView.setText(R.string.notice_no_permission);
                break;
            case ScanViewModel.STATE_SCANNING:
                textView.setText(R.string.notice_scanning);
                break;
            case ScanViewModel.STATE_SCANNING_FAILED:
                textView.setText(R.string.notice_scanning_failed);
                break;
            case ScanViewModel.STATE_SCANNING_SUCCESS:
                textView.setText(R.string.notice_scanning_success);
                break;
        }
    }

    @BindingAdapter(value = "actionText")
    public static void actionText(TextView textView, int state) {
        switch (state) {
            case ScanViewModel.STATE_BLUETOOTH_OFF:
                textView.setText(R.string.action_bluetooth_off);
                break;
            case ScanViewModel.STATE_NO_PERMISSION:
                textView.setText(R.string.action_no_permission);
                break;
            case ScanViewModel.STATE_SCANNING_FAILED:
                textView.setText(R.string.action_scanning_failed);
                break;
        }
    }

    @BindingAdapter(value = "actionShow")
    public static void actionShow(TextView textView, int state) {
        switch (state) {
            case ScanViewModel.STATE_UN_SUPPORT:
            case ScanViewModel.STATE_UN_SUPPORT_BLE:
            case ScanViewModel.STATE_SCANNING:
            case ScanViewModel.STATE_SCANNING_SUCCESS:
                textView.setVisibility(View.GONE);
                break;
            case ScanViewModel.STATE_BLUETOOTH_OFF:
            case ScanViewModel.STATE_NO_PERMISSION:
            case ScanViewModel.STATE_SCANNING_FAILED:
                textView.setVisibility(View.VISIBLE);
                break;

        }
    }
}
