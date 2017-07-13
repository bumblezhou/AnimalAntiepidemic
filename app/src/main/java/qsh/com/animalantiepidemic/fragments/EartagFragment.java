package qsh.com.animalantiepidemic.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.adapter.FarmerArrayAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentEartagBinding;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.persistent.FarmerDbHelper;
import com.google.zxing.Result;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class EartagFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private List<FarmerModel> farmerModels;
    private AutoCompleteTextView farmerAutoCompleteTextView;
    private EditText eartagStartEditText;
    private EditText eartagEndEditText;
    private ZXingScannerView zXingScannerView;

    public EartagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        farmerModels = FarmerDbHelper.loadAllFarmersFromDatabase(getActivity());
        if (farmerModels == null || farmerModels.size() <= 0) {
            Log.i("database", "从本地文件中获取用户数据");
            String fileContent = LocalResourceHelper.loadResourceFileContent(getActivity(), R.raw.farmers);
            Gson gson = new Gson();
            FarmerModel[] farmerArray = gson.fromJson(fileContent, FarmerModel[].class);
            farmerModels = Arrays.asList(farmerArray);
            FarmerDbHelper.syncFarmersToDatabase(getActivity(), farmerModels);
            Log.i("database", "共获取畜主数据条数:" + farmerArray.length);
        }

        farmerAutoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.farmer_selector);
        farmerAutoCompleteTextView.setFocusable(true);

        FarmerArrayAdapter farmerArrayAdapter = new FarmerArrayAdapter(getContext(), R.layout.fragment_eartag, R.id.label_householder, farmerModels);
        farmerAutoCompleteTextView.setAdapter(farmerArrayAdapter);
        farmerAutoCompleteTextView.setThreshold(1);

        eartagStartEditText = (EditText) getActivity().findViewById(R.id.txt_start_eartag);
        eartagStartEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this.onClick(getView());
            }
        });
        eartagEndEditText = (EditText) getActivity().findViewById(R.id.txt_end_eartag);
        eartagEndEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this.onClick(getView());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEartagBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_eartag, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        //binding.setUser(DataHolder.getCurrentUser());

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_eartag, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(zXingScannerView != null){
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        if(eartagStartEditText.getText().equals("")){
            eartagStartEditText.setText(result.getText());
        } else {
            eartagEndEditText.setText(result.getText());
        }
    }

    @Override
    public void onClick(View v) {
        zXingScannerView = new ZXingScannerView(getActivity());
        getActivity().setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
}
