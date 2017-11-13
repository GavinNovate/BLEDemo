package net.novate.cubers.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

import net.novate.cubers.R;
import net.novate.cubers.base.BaseActivity;
import net.novate.cubers.ble.Bluetooth;
import net.novate.cubers.databinding.ScanActivityBinding;
import net.novate.cubers.view.event.ScanActivityEvent;
import net.novate.cubers.viewmodel.BluetoothViewModel;

public class ScanActivity extends BaseActivity {

    private static final String TAG = "ScanActivity";

    private ScanActivityBinding binding;
    private BluetoothViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.scan_activity);
        viewModel = ViewModelProviders.of(this).get(BluetoothViewModel.class);

        // 获取权限
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                init();
            }

            @Override
            public void onDenied(String permission) {
                finish();
            }
        });
    }

    private void init() {
        binding.setEvent(new ScanActivityEvent() {
            @Override
            public void onAction(int state, int scanState) {
                viewModel.action(state, scanState);
            }
        });

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    binding.setState(integer);
                    // 每次蓝牙被打开时，尝试自动扫描
                    if (integer == BluetoothAdapter.STATE_ON) {
                        viewModel.autoScan();
                    }
                }
            }
        });
        viewModel.getScanState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    binding.setScanState(integer);
                    if (integer == Bluetooth.SCAN_STATE_SUCCESS) {
                        startActivity(new Intent(ScanActivity.this, SelectActivity.class));
                    }
                }

            }
        });

        // 初始化
        viewModel.init();
        // 自动扫描
        viewModel.autoScan();
    }
}
