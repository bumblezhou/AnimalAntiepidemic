package qsh.com.animalantiepidemic.fragments;

import android.animation.Animator;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.adapter.FarmerRecycleViewAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentHomeBinding;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.helper.PinYinUtil;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.persistent.FarmerDbHelper;

import static qsh.com.animalantiepidemic.R.color.colorPrimaryDark;

/**
 * Created by JackZhou on 05/07/2017.
 */

//public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, SortedListAdapter.Callback {
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private FarmerRecycleViewAdapter farmerRecycleViewAdapter;
    private List<FarmerModel> farmerModels;
    private FarmerModel selectedFarmerModel;

    private FragmentHomeBinding fragmentHomeBinding;
    private Animator mAnimator;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        //binding.setUser(DataHolder.getCurrentUser());

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {

        farmerModels = new ArrayList<>();
        //本地文件存储转入本地数据库存储中
        Log.i("database", "从本地文件中获取畜主数据");
        String fileContent = LocalResourceHelper.loadResourceFileContent(getActivity(), R.raw.farmers);
        Gson gson = new Gson();
        FarmerModel[] farmerArray = gson.fromJson(fileContent, FarmerModel[].class);
        FarmerDbHelper.syncFarmersToDatabase(getActivity(), Arrays.asList(farmerArray));
        Log.i("database", "共获取畜主数据条数:" + farmerArray.length);

        farmerModels = FarmerDbHelper.loadAllFarmersFromDatabase(getActivity());
        farmerRecycleViewAdapter = new FarmerRecycleViewAdapter(getActivity(), farmerModels);

        fragmentHomeBinding.farmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentHomeBinding.farmerRecyclerView.setAdapter(farmerRecycleViewAdapter);

        super.onStart();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("search", "search key:" + query);
        final List<FarmerModel> filteredModelList = filter(farmerModels, query);
        farmerRecycleViewAdapter.setNewDataset(filteredModelList);

        return true;
    }

    private static List<FarmerModel> filter(List<FarmerModel> models, String query) {
        final String lowerCaseQuery = query.trim().toLowerCase();

        Log.d("search", "total search items:" + models.size());
        final List<FarmerModel> filteredModelList = new ArrayList<>();
        for (FarmerModel model : models) {
            if (model.getAddress().contains(lowerCaseQuery)
                    || model.getMobile().contains(lowerCaseQuery)
                    || model.getHouseholder().contains(lowerCaseQuery)
                    || PinYinUtil.getFirstSpell(model.getAddress()).trim().toLowerCase().contains(lowerCaseQuery)
                    || PinYinUtil.getFirstSpell(model.getHouseholder()).trim().toLowerCase().contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        Log.d("search", "filted search items:" + filteredModelList.size());
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("search", "search key:" + newText);
        final List<FarmerModel> filteredModelList = filter(farmerModels, newText);
        farmerRecycleViewAdapter.setNewDataset(filteredModelList);
        return true;
    }

//    @Override
//    public void onEditStarted() {
//        if (fragmentHomeBinding.toolbarEditProgressBar.getVisibility() != View.VISIBLE) {
//            fragmentHomeBinding.toolbarEditProgressBar.setVisibility(View.VISIBLE);
//            fragmentHomeBinding.toolbarEditProgressBar.setAlpha(0.0f);
//        }
//
//        if (mAnimator != null) {
//            mAnimator.cancel();
//        }
//
//        mAnimator = ObjectAnimator.ofFloat(fragmentHomeBinding.toolbarEditProgressBar, View.ALPHA, 1.0f);
//        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        mAnimator.start();
//
//        fragmentHomeBinding.farmerRecyclerView.animate().alpha(0.5f);
//    }
//
//    @Override
//    public void onEditFinished() {
//        fragmentHomeBinding.farmerRecyclerView.scrollToPosition(0);
//        fragmentHomeBinding.farmerRecyclerView.animate().alpha(1.0f);
//
//        if (mAnimator != null) {
//            mAnimator.cancel();
//        }
//
//        mAnimator = ObjectAnimator.ofFloat(fragmentHomeBinding.toolbarEditProgressBar, View.ALPHA, 0.0f);
//        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        mAnimator.addListener(new AnimatorListenerAdapter() {
//
//            private boolean mCanceled = false;
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//                mCanceled = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                if (!mCanceled) {
//                    fragmentHomeBinding.toolbarEditProgressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//        mAnimator.start();
//    }

    public void openAddFarmerDialog(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.create_farmer_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("提交",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                Spinner spinner = (Spinner) promptsView.findViewById(R.id.txt_farmer_breed_type);
                                String breed_type = spinner.getSelectedItem().toString();

                                TextView txtViewHouseholder = (TextView) promptsView.findViewById(R.id.txt_farmer_householder);
                                String householder = txtViewHouseholder.getText().toString();

                                TextView txtViewAddress = (TextView) promptsView.findViewById(R.id.txt_farmer_address);
                                String address = txtViewAddress.getText().toString();

                                TextView txtViewMobile = (TextView) promptsView.findViewById(R.id.txt_farmer_mobile);
                                String mobile = txtViewMobile.getText().toString();

                                //final String message = "准备提交畜主信息(" + householder + "-" + address + "-" + mobile + "-" + breed_type + ")!";
                                //Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();

                                if(householder.equals("") || address.equals("") || mobile.equals("")){
                                    final String message = "必填项不能为空，请确认!";
                                    Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                    return;
                                }

                                SQLiteDatabase db = new FarmerDbHelper(getActivity()).getWritableDatabase();
                                //找一下库中有没有JSON文件中最大ID的记录
                                Cursor queryCursor = db.query(FarmerModel.TABLE_NAME, FarmerModel.COLUMN_NAMES,
                                        "householder = ? AND address = ? AND mobile = ?",
                                        new String[] { householder, address, mobile },
                                        null, null, null);
                                Integer recordCount = queryCursor.getCount();
                                db.close();

                                //如果有，则别同步了
                                if (recordCount > 0) {
                                    final String message = "库中已存在畜主(" + householder + "-" + address + "-" + mobile + "-" + breed_type + "),请修改畜主名称、地址、电话中的任意一项重新提交!";
                                    Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Log.i("database", "如果没有JSON文件中最大ID的记录");
                                    Log.i("database", "开始同步用户数据到数据库中");
                                    db = new FarmerDbHelper(getActivity()).getWritableDatabase();
                                    try {
                                        ContentValues contentValues = new ContentValues();
                                        //contentValues.put("id", user.getId());
                                        contentValues.put("householder", householder);
                                        contentValues.put("mobile", mobile);
                                        contentValues.put("address", address);
                                        contentValues.put("breed_type", breed_type == "猪" ? 0 : breed_type == "牛羊" ? 1 : 2);
                                        db.insert(FarmerModel.TABLE_NAME, null, contentValues);

                                        final String message = "添加畜主(" + householder + "-" + address + "-" + mobile + "-" + breed_type + ")成功!";
                                        Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                        db.close();

                                        //提交成攻，刷新界面上的数据
                                        farmerModels = FarmerDbHelper.loadAllFarmersFromDatabase(getActivity());
                                        farmerRecycleViewAdapter.setNewDataset(farmerModels);

                                    } catch (Exception e) {
                                        Log.d("debug", "同步数据库失败，错误信息:" + e.getMessage());
                                        db.close();
                                    }
                                }
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("添加畜主");
        alertDialog.setIcon(R.mipmap.ic_launcher_round);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(colorPrimaryDark));
            }
        });

        Spinner spinner = (Spinner) promptsView.findViewById(R.id.txt_farmer_breed_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.breed_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner == null){
            Log.d("system", "未能找到对话框中的spinner widget.");
        }
        if(adapter == null){
            Log.d("system", "未能成功创建spinner适配器.");
        }
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // show it
        alertDialog.show();
    }

    public void openEidtFarmerDialog(){

        if(farmerRecycleViewAdapter.selectedPosition < 0){
            final String message = "请先选择畜主后，再进行编辑操作!";
            Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            return;
        }

        selectedFarmerModel = farmerRecycleViewAdapter.mFarmerModels.get(farmerRecycleViewAdapter.selectedPosition);

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.create_farmer_dialog, null);

        // 绑定控件值
        Spinner spinner = (Spinner) promptsView.findViewById(R.id.txt_farmer_breed_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.breed_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner == null){
            Log.d("system", "未能找到对话框中的spinner widget.");
        }
        if(adapter == null){
            Log.d("system", "未能成功创建spinner适配器.");
        }
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedFarmerModel.getBreed_type());

        TextView txtViewHouseholder = (TextView) promptsView.findViewById(R.id.txt_farmer_householder);
        txtViewHouseholder.setText(selectedFarmerModel.getHouseholder());

        TextView txtViewAddress = (TextView) promptsView.findViewById(R.id.txt_farmer_address);
        txtViewAddress.setText(selectedFarmerModel.getAddress());

        TextView txtViewMobile = (TextView) promptsView.findViewById(R.id.txt_farmer_mobile);
        txtViewMobile.setText(selectedFarmerModel.getMobile());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("提交",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                Spinner spinner = (Spinner) promptsView.findViewById(R.id.txt_farmer_breed_type);
                                String breed_type = spinner.getSelectedItem().toString();

                                TextView txtViewHouseholder = (TextView) promptsView.findViewById(R.id.txt_farmer_householder);
                                String householder = txtViewHouseholder.getText().toString();

                                TextView txtViewAddress = (TextView) promptsView.findViewById(R.id.txt_farmer_address);
                                String address = txtViewAddress.getText().toString();

                                TextView txtViewMobile = (TextView) promptsView.findViewById(R.id.txt_farmer_mobile);
                                String mobile = txtViewMobile.getText().toString();

                                //final String message = "准备提交畜主信息(" + householder + "-" + address + "-" + mobile + "-" + breed_type + ")!";
                                //Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();

                                if(householder.equals("") || address.equals("") || mobile.equals("")){
                                    final String message = "必填项不能为空，请确认!";
                                    Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                    return;
                                }

                                SQLiteDatabase db = new FarmerDbHelper(getActivity()).getWritableDatabase();
                                //找一下库中有没有JSON文件中最大ID的记录
                                Cursor queryCursor = db.query(FarmerModel.TABLE_NAME, FarmerModel.COLUMN_NAMES,
                                        "id = ?",
                                        new String[] { selectedFarmerModel.getId().toString() },
                                        null, null, null);
                                Integer recordCount = queryCursor.getCount();
                                db.close();

                                //如果有，则别同步了
                                if (recordCount > 0) {
                                    Log.i("database", "如果存在ID=" + selectedFarmerModel.getId().toString() + "的记录");
                                    Log.i("database", "则开始更新用户数据到数据库中");
                                    db = new FarmerDbHelper(getActivity()).getWritableDatabase();
                                    try {
                                        ContentValues contentValues = new ContentValues();
                                        //contentValues.put("id", user.getId());
                                        contentValues.put("householder", householder);
                                        contentValues.put("mobile", mobile);
                                        contentValues.put("address", address);
                                        contentValues.put("breed_type", breed_type == "猪" ? 0 : breed_type == "牛羊" ? 1 : 2);
                                        db.update(FarmerModel.TABLE_NAME, contentValues, "id = ?", new String[]{ selectedFarmerModel.getId().toString() });

                                        final String message = "更新畜主(" + householder + "-" + address + "-" + mobile + "-" + breed_type + ")信息成功!";
                                        Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                        db.close();

                                        //提交成攻，刷新界面上的数据
                                        farmerModels = FarmerDbHelper.loadAllFarmersFromDatabase(getActivity());
                                        farmerRecycleViewAdapter.setNewDataset(farmerModels);

                                    } catch (Exception e) {
                                        Log.d("debug", "更新畜主失败，错误信息:" + e.getMessage());
                                        db.close();
                                    }
                                } else {
                                    final String message = "库中不存在(id=" + selectedFarmerModel.getId() + ")的畜主,请勿非法操作!";
                                    Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("修改畜主");
        alertDialog.setIcon(R.mipmap.ic_launcher_round);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(colorPrimaryDark));
            }
        });

        // show it
        alertDialog.show();
    }
}
