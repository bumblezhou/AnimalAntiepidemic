package qsh.com.animalantiepidemic.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.adapter.FarmerAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentHomeBinding;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.helper.PinYinUtil;
import qsh.com.animalantiepidemic.localstate.DataHolder;
import qsh.com.animalantiepidemic.models.FarmerComparator;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, SortedListAdapter.Callback {

    private FarmerAdapter farmerAdapter;
    private List<FarmerModel> farmerModels;

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

        farmerAdapter = new FarmerAdapter(getActivity(), new FarmerComparator(), new FarmerAdapter.Listener() {
            @Override
            public void onFarmerModelClicked(FarmerModel model) {
                final String message = "点击了畜主(" + model.getHouseholder() + "-" + model.getMobile() + "-" + model.getAddress() +")!";
                Snackbar.make(fragmentHomeBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
        farmerAdapter.addCallback(this);
        fragmentHomeBinding.farmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentHomeBinding.farmerRecyclerView.setAdapter(farmerAdapter);

        farmerModels = new ArrayList<>();
        //本地文件存储转入本地数据库存储中
        Log.i("database", "从本地文件中获取用户数据");
        String fileContent = LocalResourceHelper.loadResourceFileContent(getActivity(), R.raw.farmers);
        Gson gson = new Gson();
        FarmerModel[] farmerArray = gson.fromJson(fileContent, FarmerModel[].class);
        farmerModels = Arrays.asList(farmerArray);
        Log.i("database", "共获取畜主数据条数:" + farmerArray.length);
        farmerAdapter.edit()
                .replaceAll(farmerModels)
                .commit();

        super.onStart();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("search", "search key:" + query);
        final List<FarmerModel> filteredModelList = filter(farmerModels, query);
        farmerAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
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
                    || PinYinUtil.getFirstSpell(model.getAddress()).trim().toLowerCase().contains(query)
                    || PinYinUtil.getFirstSpell(model.getHouseholder()).trim().toLowerCase().contains(query)) {
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
        farmerAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        return true;
    }

    @Override
    public void onEditStarted() {
        if (fragmentHomeBinding.toolbarEditProgressBar.getVisibility() != View.VISIBLE) {
            fragmentHomeBinding.toolbarEditProgressBar.setVisibility(View.VISIBLE);
            fragmentHomeBinding.toolbarEditProgressBar.setAlpha(0.0f);
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(fragmentHomeBinding.toolbarEditProgressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        fragmentHomeBinding.farmerRecyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        fragmentHomeBinding.farmerRecyclerView.scrollToPosition(0);
        fragmentHomeBinding.farmerRecyclerView.animate().alpha(1.0f);

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(fragmentHomeBinding.toolbarEditProgressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    fragmentHomeBinding.toolbarEditProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();
    }
}
