package qsh.com.animalantiepidemic.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qsh.com.animalantiepidemic.MainActivity;
import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.databinding.FragmentChipsetBinding;
import qsh.com.animalantiepidemic.localstate.DataHolder;

/**
 * Created by JackZhou on 13/07/2017.
 */

public class ScanQrcodeFragment  extends Fragment {
    private ZXingScannerView zXingScannerView;
    LinearLayout qrCameraLayout;

    public ScanQrcodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment", "onCreateView 事件开发执行...");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanqrcode, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("fragment", "onStart 事件开发执行...,并创建zXingScannerView");
        qrCameraLayout = (LinearLayout) getView().findViewById(R.id.ll_qrcamera);
        zXingScannerView = new ZXingScannerView(getActivity().getApplicationContext());
        zXingScannerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        qrCameraLayout.addView(zXingScannerView);

        zXingScannerView.setResultHandler(((MainActivity)getActivity()));
        zXingScannerView.startCamera();
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        Log.d("fragment", "onPause 事件开发执行...");
//        if(zXingScannerView != null){
//            zXingScannerView.stopCamera();
//            DataHolder.IS_OPEN_SCAN_CAMERA = false;
//        }
//        Handler uiHandler = new Handler();
//        uiHandler.post(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                ((MainActivity)getActivity()).switchToEartagFragment();
//            }
//        });
//    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("fragment", "onStop 事件开发执行...");
        if(zXingScannerView != null){
            zXingScannerView.stopCamera();
            DataHolder.IS_OPEN_SCAN_CAMERA = false;
        }
        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                ((MainActivity)getActivity()).switchToEartagFragment();
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("fragment", "onResume 事件开发执行...");
//        qrCameraLayout = (LinearLayout) getView().findViewById(R.id.ll_qrcamera);
//        zXingScannerView = new ZXingScannerView(getActivity().getApplicationContext());
//        zXingScannerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        qrCameraLayout.addView(zXingScannerView);
//
//        zXingScannerView.setResultHandler(((MainActivity)getActivity()));
//        zXingScannerView.startCamera();
//    }
}
