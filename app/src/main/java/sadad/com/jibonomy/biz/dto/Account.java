package sadad.com.jibonomy.biz.dto;

public class Account {

    private String accountNumber;
    private String accountType;
    private boolean hasBlockedAmount;
    private boolean hasSpecialRule;
    private long availableBalance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isHasBlockedAmount() {
        return hasBlockedAmount;
    }

    public void setHasBlockedAmount(boolean hasBlockedAmount) {
        this.hasBlockedAmount = hasBlockedAmount;
    }

    public boolean isHasSpecialRule() {
        return hasSpecialRule;
    }

    public void setHasSpecialRule(boolean hasSpecialRule) {
        this.hasSpecialRule = hasSpecialRule;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }
}
