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

    public static boolean IS_OPEN_SCAN_CAMERA = false;
    public static int TO_SCAN_CONTENT_INTO_CONTROL_INDEX = 0;
    private static String scanedResult;
    public static String getScanedResult() {return scanedResult;}
    public static void setScanedResult(String result){
        scanedResult = result;
    }
}
