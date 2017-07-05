package qsh.com.animalantiepidemic.models;

import java.util.Date;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class EarNumberModel {
    private Integer id;
    private Date date_created;
    private String mini;
    private String number;
    private String type;

    public EarNumberModel(Integer id, Date date_created, String mini, String number, String type) {
        this.id = id;
        this.date_created = date_created;
        this.mini = mini;
        this.number = number;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
