package sadad.com.jibonomy.biz.dto;

public class ChartDataDto {

    String lable;
    long totalAmount;

    public ChartDataDto(){};

    public ChartDataDto(String lable, long percent) {
        this.lable = lable;
        this.totalAmount = percent;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
