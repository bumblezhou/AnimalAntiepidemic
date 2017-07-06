package qsh.com.animalantiepidemic.models;

import android.database.Cursor;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class UserModel {
    private Integer id;
    private String name;
    private String address;
    private String fullName;
    private String password;

    public UserModel() {
    }

    public UserModel(Integer id, String name, String address, String fullName, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fullName = fullName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserModel parseUser(Cursor cursor){
        UserModel userModel = new UserModel();
        userModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
        userModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        userModel.setName(cursor.getString(cursor.getColumnIndex("name")));
        userModel.setFullName(cursor.getString(cursor.getColumnIndex("fullName")));
        userModel.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        return userModel;
    }

    public static String TABLE_NAME = "Users";
    public static String[] COLUMN_NAMES = new String[] {"id", "name", "fullName", "address", "password"};
    public static String DATABASE_COLUMN_NAMES = "id, name, fullName, address, password";
}
