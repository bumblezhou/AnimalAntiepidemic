package qsh.com.animalantiepidemic.localstate;

import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class DataHolder {
    private static UserModel currentUser;
    public static UserModel getCurrentUser() {return currentUser;}
    public static void setCurrentUser(UserModel userModel) {
        DataHolder.currentUser = userModel;
    }
}
