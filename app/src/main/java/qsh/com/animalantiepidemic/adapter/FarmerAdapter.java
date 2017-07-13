package qsh.com.animalantiepidemic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import qsh.com.animalantiepidemic.databinding.ItemFarmerBinding;
import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class FarmerAdapter extends SortedListAdapter<FarmerModel> {

    public interface Listener {
        void onFarmerModelClicked(FarmerModel model);
    }

    private final Listener mListener;

    public FarmerAdapter(Context context, Comparator<FarmerModel> comparator, Listener listener) {
        super(context, FarmerModel.class, comparator);
        mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends FarmerModel> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ItemFarmerBinding binding = ItemFarmerBinding.inflate(inflater, parent, false);
        return new FarmerViewHolder(binding, mListener);
    }
}
