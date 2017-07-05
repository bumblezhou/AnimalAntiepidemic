package qsh.com.animalantiepidemic.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.databinding.FragmentAboutBinding;
import qsh.com.animalantiepidemic.localstate.DataHolder;
import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class AboutFragment extends Fragment {
    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setUser(DataHolder.getCurrentUser());

        return view;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
