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
public class ConnectingFragment extends Fragment {


    public ConnectingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.connecting_fragment, container, false);
    }

}
