package net.novate.cubers.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import net.novate.cubers.R;
import net.novate.cubers.base.BaseActivity;
import net.novate.cubers.databinding.ScanActivityBinding;
import net.novate.cubers.view.event.ScanActivityEvent;
import net.novate.cubers.viewmodel.ScanViewModel;

/**
 * 扫描页 包含设备不支持提示，蓝牙未打开提示，扫描中，扫描结果
 * <p>
 * 状态：不支持 蓝牙未打开 无扫描权限 扫描中 - 扫描成功（设备选择页） 扫描失败（无匹配）
 */
public class ScanActivity extends BaseActivity {

    private static final String TAG = "ScanActivity";

    private ScanActivityBinding binding;
    private ScanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.scan_activity);
        viewModel = ViewModelProviders.of(this).get(ScanViewModel.class);

        init();
    }

    private void init() {
        binding.setEvent(new ScanActivityEvent() {
            @Override
            public void onAction(int state) {
                Log.d(TAG, "onAction() called with: state = [" + state + "]");
            }
        });

        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    binding.setState(integer);
                }
            }
        });
        viewModel.init();
    }
}
