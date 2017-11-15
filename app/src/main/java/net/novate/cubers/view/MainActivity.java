package net.novate.cubers.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import net.novate.cubers.R;
import net.novate.cubers.base.BaseActivity;
import net.novate.cubers.databinding.MainActivityBinding;
import net.novate.cubers.view.event.MainActivityEvent;

/**
 * 主功能页
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    boolean showi = false;

    private MainActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        binding.setEvent(new MainActivityEvent() {
            @Override
            public void onClick() {

                Log.d(TAG, "onClick: ");
                ViewGroup.LayoutParams params = binding.layout.getLayoutParams();

                if (showi) {
                    params.height = params.height / 3;
                } else {
                    params.height = params.height * 3;
                }
                binding.layout.setLayoutParams(params);
                showi = !showi;
            }
        });
    }
}
