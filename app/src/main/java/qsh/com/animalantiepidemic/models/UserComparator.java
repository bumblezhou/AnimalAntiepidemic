package qsh.com.animalantiepidemic.models;

import java.util.Comparator;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class UserComparator implements Comparator<UserModel> {
    @Override
    public int compare(UserModel first, UserModel next) {
        return first.getId().compareTo(next.getId());
    }
}
