package sadad.com.jibonomy.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigDecimal;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */

@Entity(tableName = "Wish")
public class Wish {
    public static final String WISH_ID_LABEL = "WISHID";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wishId")
    private Long wishId;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "pic")
    private String picName;
    @ColumnInfo(name = "budget")
    private BigDecimal budget;

    public Wish() {

    }

    @Ignore
    public Wish(String description, String name, String picName, BigDecimal budget) {

        this.description = description;
        this.name = name;
        this.picName = picName;
        this.budget = budget;
    }

    public Long getWishId() {
        return wishId;
    }

    public void setWishId(Long wishId) {
        this.wishId = wishId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
