package qsh.com.animalantiepidemic.models;

import java.util.Date;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class AnimalAntiepidemicModel {
    /*
    记录Id
     */
    private Integer id;
    /*
    动物类型Id
     */
    private Integer animal_type_id;
    /*
    动物数量，animal_amount = ought_amount + in_cote_amount - out_cote_amount
     */
    private Integer animal_amount;
    /*
    应防疫动物数量
     */
    private Integer ought_amount;
    /*
    实防疫动物数量
     */
    private Integer actual_amount;
    /*
    补免动物数量
     */
    private Integer missing_amount;
    /*
    补免原因
     */
    private String missing_reason;
    /*
    补免日期
     */
    private Date missing_animal_antiepidemic_date;
    /*
    出栏数量
     */
    private Integer out_cote_amount;
    /*
    补栏数量
     */
    private Integer in_cote_amount;
    /*
    防疫日期
     */
    private Date antiepidemic_date;
    /*
    畜主id
     */
    private Integer farmer_id;
    /*
    操作用户(防疫员)id
     */
    private Integer user_id;
    /*
    疫苗
     */
    private String vaccine;
    /*
    疫苗厂家
     */
    private String vaccine_manufacture;
    /*
    疫苗批号
     */
    private String vaccine_patch_number;

    public AnimalAntiepidemicModel() {
    }

    public AnimalAntiepidemicModel(Integer animal_type_id, Integer ought_amount, Integer actual_amount, Integer missing_amount, String missing_reason, Date missing_animal_antiepidemic_date, Integer out_cote_amount, Integer in_cote_amount, Date antiepidemic_date, Integer farmer_id, Integer user_id, String vaccine, String vaccine_manufacture, String vaccine_patch_number) {
        this.animal_type_id = animal_type_id;
        this.ought_amount = ought_amount;
        this.actual_amount = actual_amount;
        this.missing_amount = missing_amount;
        this.missing_reason = missing_reason;
        this.missing_animal_antiepidemic_date = missing_animal_antiepidemic_date;
        this.out_cote_amount = out_cote_amount;
        this.in_cote_amount = in_cote_amount;
        this.antiepidemic_date = antiepidemic_date;
        this.farmer_id = farmer_id;
        this.user_id = user_id;
        this.vaccine = vaccine;
        this.vaccine_manufacture = vaccine_manufacture;
        this.vaccine_patch_number = vaccine_patch_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnimal_type_id() {
        return animal_type_id;
    }

    public void setAnimal_type_id(Integer animal_type_id) {
        this.animal_type_id = animal_type_id;
    }

    public Integer getAnimal_amount() {
        return ought_amount + in_cote_amount - out_cote_amount;
    }

    public Integer getOught_amount() {
        return ought_amount;
    }

    public void setOught_amount(Integer ought_amount) {
        this.ought_amount = ought_amount;
    }

    public Integer getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(Integer actual_amount) {
        this.actual_amount = actual_amount;
    }

    public Integer getMissing_amount() {
        return missing_amount;
    }

    public void setMissing_amount(Integer missing_amount) {
        this.missing_amount = missing_amount;
    }

    public String getMissing_reason() {
        return missing_reason;
    }

    public void setMissing_reason(String missing_reason) {
        this.missing_reason = missing_reason;
    }

    public Date getMissing_animal_antiepidemic_date() {
        return missing_animal_antiepidemic_date;
    }

    public void setMissing_animal_antiepidemic_date(Date missing_animal_antiepidemic_date) {
        this.missing_animal_antiepidemic_date = missing_animal_antiepidemic_date;
    }

    public Integer getOut_cote_amount() {
        return out_cote_amount;
    }

    public void setOut_cote_amount(Integer out_cote_amount) {
        this.out_cote_amount = out_cote_amount;
    }

    public Integer getIn_cote_amount() {
        return in_cote_amount;
    }

    public void setIn_cote_amount(Integer in_cote_amount) {
        this.in_cote_amount = in_cote_amount;
    }

    public Date getAntiepidemic_date() {
        return antiepidemic_date;
    }

    public void setAntiepidemic_date(Date antiepidemic_date) {
        this.antiepidemic_date = antiepidemic_date;
    }

    public Integer getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(Integer farmer_id) {
        this.farmer_id = farmer_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getVaccine_manufacture() {
        return vaccine_manufacture;
    }

    public void setVaccine_manufacture(String vaccine_manufacture) {
        this.vaccine_manufacture = vaccine_manufacture;
    }

    public String getVaccine_patch_number() {
        return vaccine_patch_number;
    }

    public void setVaccine_patch_number(String vaccine_patch_number) {
        this.vaccine_patch_number = vaccine_patch_number;
    }
}
