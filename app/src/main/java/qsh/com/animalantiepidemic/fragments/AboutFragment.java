package qsh.com.animalantiepidemic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import qsh.com.animalantiepidemic.LoginActivity;
import qsh.com.animalantiepidemic.MainActivity;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button loginoutButton = (Button) getActivity().findViewById(R.id.action_logout);
        loginoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHolder.setCurrentUser(null);
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().setContentView(R.layout.activity_login);
            }
        });
    }
}
