package qsh.com.animalantiepidemic.models;

/**
 * Created by JackZhou on 05/07/2017.
 */

public class AnimalTypeModel {
    private Integer id;
    private String name;

    public AnimalTypeModel(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
