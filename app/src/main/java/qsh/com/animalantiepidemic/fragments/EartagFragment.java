package qsh.com.animalantiepidemic.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import qsh.com.animalantiepidemic.MainActivity;
import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.adapter.FarmerArrayAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentEartagBinding;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.localstate.DataHolder;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.persistent.FarmerDbHelper;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class EartagFragment extends Fragment {

    private List<FarmerModel> farmerModels;
    private AutoCompleteTextView farmerAutoCompleteTextView;
    private EditText eartagStartEditText;
    private ImageButton btnScanStart;
    private EditText eartagEndEditText;
    private ImageButton btnScanEnd;
    private Button eartagSubmitButton;

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
        if(farmerAutoCompleteTextView != null){
            farmerAutoCompleteTextView.setFocusable(true);

            FarmerArrayAdapter farmerArrayAdapter = new FarmerArrayAdapter(getContext(), R.layout.fragment_eartag, R.id.label_householder, farmerModels);
            farmerAutoCompleteTextView.setAdapter(farmerArrayAdapter);
            farmerAutoCompleteTextView.setThreshold(1);
        }

        eartagStartEditText = (EditText) getActivity().findViewById(R.id.txt_start_eartag);
        btnScanStart = (ImageButton) getActivity().findViewById(R.id.eartag_btn_scan_start);
        if(btnScanStart != null){
            btnScanStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eartagStartEditText.setText("");
                    DataHolder.setScanedResult("");
                    DataHolder.TO_SCAN_CONTENT_INTO_CONTROL_INDEX = 0;
                    DataHolder.IS_OPEN_SCAN_CAMERA = true;
                    ((MainActivity)getActivity()).switchToScanQrcodeFragment();
                }
            });
        }

        eartagEndEditText = (EditText) getActivity().findViewById(R.id.txt_end_eartag);
        btnScanEnd = (ImageButton) getActivity().findViewById(R.id.eartag_btn_scan_end);
        if(btnScanEnd != null){
            btnScanEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eartagEndEditText.setText("");
                    DataHolder.setScanedResult("");
                    DataHolder.TO_SCAN_CONTENT_INTO_CONTROL_INDEX = 1;
                    DataHolder.IS_OPEN_SCAN_CAMERA = true;
                    ((MainActivity)getActivity()).switchToScanQrcodeFragment();
                }
            });
        }

        eartagSubmitButton = (Button) getActivity().findViewById(R.id.eartag_btn_submit);
        eartagSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //启动EartagFragment时，填入扫描结果值
        if(DataHolder.getScanedResult() != null && !DataHolder.getScanedResult().equals("")){
            setScanedResultText(DataHolder.getScanedResult());
        }
    }

    private void setScanedResultText(String scanedResultText){
        if(eartagStartEditText == null){
            eartagStartEditText = (EditText) getActivity().findViewById(R.id.txt_start_eartag);
        }

        if(eartagEndEditText == null){
            eartagEndEditText = (EditText) getActivity().findViewById(R.id.txt_end_eartag);
        }

        String processedResult = scanedResultText;

        if(DataHolder.TO_SCAN_CONTENT_INTO_CONTROL_INDEX == 0){
            eartagStartEditText.setText(processedResult);
        } else {
            eartagEndEditText.setText(processedResult);
        }
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
}
