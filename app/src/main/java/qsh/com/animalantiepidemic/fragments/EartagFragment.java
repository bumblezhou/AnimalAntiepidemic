package qsh.com.animalantiepidemic.fragments;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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

/**
 * Created by JackZhou on 05/07/2017.
 */

public class EartagFragment extends Fragment {
    private List<FarmerModel> farmerModels;
    private AutoCompleteTextView farmerTextView;
    private ArrayAdapter<String> adapter;
    private EditText eartagStartTextView;
    private EditText eartagEndTextView;

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

//        List<String> farmerCollection = new ArrayList<>();
//        for (FarmerModel farmer : farmerModels){
//            final String farmerInfo = farmer.getMobile() + "(" + farmer.getHouseholder() + "-" + farmer.getAddress() + "-" + farmer.getBreedTypeName() + ")";
//            farmerCollection.add(farmerInfo);
//        }
//        Log.i("eartag", "获取畜主collection" + farmerCollection.size() + "条");
//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, farmerCollection);
//        farmerTextView.setAdapter(adapter);
//        farmerTextView.setThreshold(1);

        FarmerArrayAdapter farmerArrayAdapter = new FarmerArrayAdapter(getContext(), R.layout.fragment_eartag, R.id.label_householder, farmerModels);
        farmerTextView.setAdapter(farmerArrayAdapter);
        farmerTextView.setThreshold(1);
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
