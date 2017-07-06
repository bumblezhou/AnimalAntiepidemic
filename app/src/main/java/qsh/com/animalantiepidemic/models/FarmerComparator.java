package qsh.com.animalantiepidemic.models;

import java.util.Comparator;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class FarmerComparator implements Comparator<FarmerModel> {
    @Override
    public int compare(FarmerModel first, FarmerModel next) {
        return first.getId().compareTo(next.getId());
    }
}
