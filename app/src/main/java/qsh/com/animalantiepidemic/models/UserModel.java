package qsh.com.animalantiepidemic.models;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class UserModel {
    private Integer id;
    private String name;
    private String address;
    private String fullName;
    private String password;

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
}
