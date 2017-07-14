package qsh.com.animalantiepidemic.fragments;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qsh.com.animalantiepidemic.MainActivity;
import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.localstate.DataHolder;

/**
 * Created by Administrator on 2017/7/14.
 */

public class ZBarScanQrcodeFragment  extends Fragment {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZBarScannerView zBarScannerView;
    LinearLayout qrCameraLayout;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        Log.d("fragment", "onCreateView 事件开发执行...");
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zbar_scanqrcode, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("fragment", "onStart 事件开发执行...,并创建zBarScannerView");
        qrCameraLayout = (LinearLayout) getView().findViewById(R.id.zbar_layout_qrcamera);
        zBarScannerView = new ZBarScannerView(getActivity());
        zBarScannerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        qrCameraLayout.addView(zBarScannerView);

        setupFormats();

        zBarScannerView.setResultHandler((MainActivity)getActivity());
        zBarScannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment", "onResume 事件开发执行...");
        if(zBarScannerView != null){
            zBarScannerView.setResultHandler((MainActivity)getActivity());
            zBarScannerView.startCamera(mCameraId);
            zBarScannerView.setFlash(mFlash);
            zBarScannerView.setAutoFocus(mAutoFocus);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("fragment", "onSaveInstanceState 事件开发执行...");
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        if(zBarScannerView != null) {
            zBarScannerView.setFormats(formats);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment", "onStop 事件开发执行...");
        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if(DataHolder.getScanedResult() != null && !DataHolder.getScanedResult().equals("")) {
                    if(zBarScannerView != null){
                        zBarScannerView.stopCamera();
                        DataHolder.IS_OPEN_SCAN_CAMERA = false;
                        ((MainActivity)getActivity()).switchToEartagFragment();
                    }
                }
            }
        });
    }
}