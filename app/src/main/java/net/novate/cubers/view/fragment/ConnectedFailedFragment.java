package net.novate.cubers.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.novate.cubers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectedFailedFragment extends Fragment {


    public ConnectedFailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.connected_failed_fragment, container, false);
    }

}
