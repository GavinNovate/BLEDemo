package net.novate.cubers.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
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
        binding.setState(viewModel.getState());
        binding.setEvent(new ScanActivityEvent() {
            @Override
            public void onAction(ObservableInt state) {
                Log.d(TAG, "onAction() called with: state = [" + state.get() + "]");
            }
        });

        viewModel.init();
    }
}
