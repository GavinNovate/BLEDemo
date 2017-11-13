package net.novate.cubers.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.novate.cubers.R;
import net.novate.cubers.databinding.SupportActivityBinding;

@SuppressWarnings("FieldCanBeLocal")
public class SupportActivity extends AppCompatActivity {

    public static final String CODE = "code";

    public static final int UN_SUPPORT = 0;
    public static final int UN_SUPPORT_BLE = 1;

    private SupportActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.support_activity);
        binding.setNotice(getIntent().getIntExtra(CODE, UN_SUPPORT) == UN_SUPPORT_BLE ? getString(R.string.notice_un_support_ble) : getString(R.string.notice_un_support));
    }
}
