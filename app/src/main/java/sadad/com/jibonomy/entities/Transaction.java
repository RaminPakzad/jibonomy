package sadad.com.jibonomy.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "Transactions")
public class Transaction {
    public static final Byte EXPENSE = (byte) 1;
    public static final Byte INCOME = (byte) 2;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    private Long transactionId;
    @ColumnInfo(name = "transactionDate")
    private String transactionDate;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "categoryType")
    private Byte categoryType;
    @ColumnInfo(name = "transactionType")
    private Byte transactionType;
    @ColumnInfo(name = "amount")
    private BigDecimal amount;


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
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

    public Byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Byte transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}