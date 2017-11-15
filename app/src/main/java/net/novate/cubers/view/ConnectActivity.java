package net.novate.cubers.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothGatt;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.novate.cubers.R;
import net.novate.cubers.databinding.ConnectActivityBinding;
import net.novate.cubers.view.fragment.ConnectedFailedFragment;
import net.novate.cubers.view.fragment.ConnectedSuccessFragment;
import net.novate.cubers.view.fragment.ConnectingFragment;
import net.novate.cubers.viewmodel.BluetoothViewModel;

/**
 * 设备连接页
 */
public class ConnectActivity extends AppCompatActivity {

    public static final String ADDRESS = "address";

    private ConnectActivityBinding binding;
    private BluetoothViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.connect_activity);

        viewModel = ViewModelProviders.of(this).get(BluetoothViewModel.class);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, new ConnectingFragment())
                .commit();

        init();
    }

    private void init() {
        viewModel.getConnectState()
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        if (integer != null)
                            switch (integer) {
                                case BluetoothGatt.STATE_DISCONNECTED:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragmentLayout, new ConnectedFailedFragment())
                                            .commit();
                                    break;
                                case BluetoothGatt.STATE_CONNECTING:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragmentLayout, new ConnectingFragment())
                                            .commit();
                                    break;
                                case BluetoothGatt.STATE_CONNECTED:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragmentLayout, new ConnectedSuccessFragment())
                                            .commit();
                                    break;
                                case BluetoothGatt.STATE_DISCONNECTING:
                                    break;
                            }
                    }
                });
        viewModel.connect(getIntent().getStringExtra(ADDRESS));
    }
}
