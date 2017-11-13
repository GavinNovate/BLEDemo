package net.novate.cubers.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import net.novate.cubers.R;
import net.novate.cubers.databinding.SelectActivityBinding;
import net.novate.cubers.model.Device;
import net.novate.cubers.view.adapter.DeviceListAdapter;
import net.novate.cubers.view.event.DeviceItemEvent;
import net.novate.cubers.viewmodel.BluetoothViewModel;

/**
 * 设备选择页
 */
public class SelectActivity extends AppCompatActivity {

    private SelectActivityBinding binding;
    private BluetoothViewModel viewModel;

    private DeviceListAdapter deviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.select_activity);
        viewModel = ViewModelProviders.of(this).get(BluetoothViewModel.class);

        init();
    }

    private void init() {
        deviceListAdapter = new DeviceListAdapter(new DeviceItemEvent() {
            @Override
            public void onSelected(Device device) {
                // TODO: 2017/11/13 进入连接界面
                startActivity(new Intent(SelectActivity.this, ConnectActivity.class));
            }
        });

        binding.deviceListView.setLayoutManager(new LinearLayoutManager(this));
        binding.deviceListView.setAdapter(deviceListAdapter);
        deviceListAdapter.setDevices(viewModel.getScannedDeviceList());
    }
}
