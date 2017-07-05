package qsh.com.animalantiepidemic.models;

/**
 * Created by JackZhou on 04/07/2017.
 */

public class FarmerModel {
    private Integer id;
    private String householder;
    private String mobile;
    private String address;
    private Integer breed_type;


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
}
