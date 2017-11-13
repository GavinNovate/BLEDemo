package net.novate.cubers.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.novate.cubers.R;
import net.novate.cubers.databinding.SelectActivityBinding;
import net.novate.cubers.viewmodel.BluetoothViewModel;

/**
 * 设备选择页
 */
public class SelectActivity extends AppCompatActivity {

    private SelectActivityBinding binding;
    private BluetoothViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.select_activity);
        viewModel = ViewModelProviders.of(this).get(BluetoothViewModel.class);

        init();
    }

    private void init() {

    }
}
