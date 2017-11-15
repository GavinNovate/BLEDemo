package net.novate.cubers.view.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.novate.cubers.R;
import net.novate.cubers.databinding.ConnectedSuccessFragmentBinding;
import net.novate.cubers.view.CheckActivity;
import net.novate.cubers.view.event.ConnectedSuccessFragmentEvent;

public class ConnectedSuccessFragment extends Fragment {

    private ConnectedSuccessFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.connected_success_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        binding.setEvent(new ConnectedSuccessFragmentEvent() {
            @Override
            public void onClick() {
                startActivity(new Intent(getActivity(), CheckActivity.class));
            }
        });
    }
}
