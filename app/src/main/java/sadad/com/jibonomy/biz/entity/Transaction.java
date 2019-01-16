package sadad.com.jibonomy.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private Long id;
    private Date date;
    private String description;
    private Byte categoryType;
    private BigDecimal amount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Byte categoryType) {
        this.categoryType = categoryType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transaction(Long id, Date date, String description, Byte categoryType, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.categoryType = categoryType;
        this.amount = amount;
    }

    public Transaction() {
    }
}
