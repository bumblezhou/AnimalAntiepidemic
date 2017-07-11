package qsh.com.animalantiepidemic.adapter.viewholder;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import qsh.com.animalantiepidemic.adapter.FarmerAdapter;
import qsh.com.animalantiepidemic.databinding.ItemFarmerBinding;
import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by JackZhou on 11/07/2017.
 */

public class FarmerViewHolder extends SortedListAdapter.ViewHolder<FarmerModel> {

    private final ItemFarmerBinding mBinding;

    public FarmerViewHolder(final ItemFarmerBinding binding, FarmerAdapter.Listener listener) {
        super(binding.getRoot());

        //binding.setListener(listener);

        mBinding = binding;
    }

    @Override
    protected void performBind(@NonNull FarmerModel item) {

        mBinding.setModel(item);
    }
}