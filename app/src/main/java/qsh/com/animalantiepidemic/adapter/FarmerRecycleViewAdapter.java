package qsh.com.animalantiepidemic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.databinding.ItemFarmerBinding;
import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by JackZhou on 11/07/2017.
 */

public class FarmerRecycleViewAdapter extends RecyclerView.Adapter<FarmerRecycleViewAdapter.FarmerRecycleViewHolder> {

    public interface Listener {
        void onFarmerModelClicked(FarmerModel model);
    }

    private final Listener mListener;
    private final Context mContext;
    private List<FarmerModel> mFarmerModels;

    public FarmerRecycleViewAdapter(Context context, Listener listener, List<FarmerModel> farmerModels){
        this.mListener = listener;
        this.mContext = context;
        this.mFarmerModels = farmerModels;
    }

    public void setNewDataset(List<FarmerModel> farmerModels){
        this.mFarmerModels = farmerModels;
        super.notifyDataSetChanged();
    }

    @Override
    public FarmerRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        final ItemFarmerBinding binding = ItemFarmerBinding.inflate(inflater, parent, false);
        binding.setListener(mListener);
        return new FarmerRecycleViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(FarmerRecycleViewHolder holder, final int position) {
        FarmerModel farmerModel = mFarmerModels.get(position);
        holder.performBind(farmerModel);
    }

    @Override
    public int getItemCount() {
        return mFarmerModels == null ? 0 : mFarmerModels.size();
    }

    public static class FarmerRecycleViewHolder extends RecyclerView.ViewHolder{

        private final ItemFarmerBinding mBinding;

        public FarmerRecycleViewHolder(final View itemView, ItemFarmerBinding binding) {
            super(itemView);

            this.mBinding = binding;
        }

        public void performBind(FarmerModel farmerModel){
            mBinding.setModel(farmerModel);
        }
    }
}
