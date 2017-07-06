package qsh.com.animalantiepidemic.models;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * Created by JackZhou on 04/07/2017.
 */

public class FarmerModel  implements SortedListAdapter.ViewModel {
    private Integer id;
    private String householder;
    private String mobile;
    private String address;
    private Integer breed_type;

    public FarmerModel() {
    }

    public FarmerModel(Integer id, String householder, String mobile, String address, Integer breed_type) {
        this.id = id;
        this.householder = householder;
        this.mobile = mobile;
        this.address = address;
        this.breed_type = breed_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBreed_type() {
        return breed_type;
    }

    public void setBreed_type(Integer breed_type) {
        this.breed_type = breed_type;
    }

    public String getBreedTypeName(){
        return breed_type == 0 ? "猪" : breed_type == 1 ? "牛羊" : "混合";
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof FarmerModel) {
            final FarmerModel farmerModel = (FarmerModel) item;
            return farmerModel.getId() == getId();
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof FarmerModel) {
            final FarmerModel farmerModel = (FarmerModel) item;
            return farmerModel.getAddress().equals(getAddress())
                    && farmerModel.getHouseholder().equals(getHouseholder())
                    && farmerModel.getMobile().equals(getMobile());
        }
        return false;
    }
}
