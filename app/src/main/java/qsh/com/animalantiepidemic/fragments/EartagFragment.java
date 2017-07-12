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

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.adapter.FarmerArrayAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentEartagBinding;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.persistent.FarmerDbHelper;

import static android.Manifest.permission_group.CAMERA;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class EartagFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CAMERA = 1;

    private List<FarmerModel> farmerModels;
    private AutoCompleteTextView farmerTextView;
    private EditText eartagStartEditText;
    private EditText eartagEndEditText;

    public EartagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        farmerModels = FarmerDbHelper.loadAllFarmersFromDatabase(getActivity());
        if(farmerModels == null || farmerModels.size() <= 0){
            Log.i("database", "从本地文件中获取用户数据");
            String fileContent = LocalResourceHelper.loadResourceFileContent(getActivity(), R.raw.farmers);
            Gson gson = new Gson();
            FarmerModel[] farmerArray = gson.fromJson(fileContent, FarmerModel[].class);
            farmerModels = Arrays.asList(farmerArray);
            FarmerDbHelper.syncFarmersToDatabase(getActivity(), farmerModels);
            Log.i("database", "共获取畜主数据条数:" + farmerArray.length);
        }

        farmerTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.farmer_selector);
        farmerTextView.setFocusable(true);

        FarmerArrayAdapter farmerArrayAdapter = new FarmerArrayAdapter(getContext(), R.layout.fragment_eartag, R.id.label_householder, farmerModels);
        farmerTextView.setAdapter(farmerArrayAdapter);
        farmerTextView.setThreshold(1);

        eartagStartEditText = (EditText) getActivity().findViewById(R.id.txt_start_eartag);
        eartagEndEditText = (EditText) getActivity().findViewById(R.id.txt_end_eartag);

        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
        scanIntegrator.initiateScan();
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
    public void onClick(View v) {
        if(v.getId()==R.id.eartag_btn_scan_start){
            //scan
        }

        if(v.getId()==R.id.eartag_btn_scan_start){
            //scan
        }
    }
}
