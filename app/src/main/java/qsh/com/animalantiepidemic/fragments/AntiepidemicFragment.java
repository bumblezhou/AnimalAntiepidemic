package qsh.com.animalantiepidemic.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qsh.com.animalantiepidemic.R;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class AntiepidemicFragment extends Fragment {
    public AntiepidemicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_antiepidemic, container, false);
    }
}
