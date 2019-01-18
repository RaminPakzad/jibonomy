package sadad.com.jibonomy.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "Transactions")
public class Transaction {
    public static final String TRANSACTION_ID_LABEL = "TRANSACTION_ID";
    public static final Byte EXPENSE = (byte) 1;
    public static final Byte INCOME = (byte) 2;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    private Long transactionId;
    @ColumnInfo(name = "transactionDate")
    private String transactionDate;
    @ColumnInfo(name = "transactionTime")
    private String transactionTime;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "subCategoryType")
    private Long subCategoryType;
    @ColumnInfo(name = "transactionType")
    private Byte transactionType;
    @ColumnInfo(name = "amount")
    private BigDecimal amount;
    @ColumnInfo(name = "tag")
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSubCategoryType() {
        return subCategoryType;
    }

    public void setSubCategoryType(Long subCategoryType) {
        this.subCategoryType = subCategoryType;
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
