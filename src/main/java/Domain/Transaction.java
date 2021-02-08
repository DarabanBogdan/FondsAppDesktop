package Domain;

public class Transaction {
    private int id;
    private String title;
    private String details;
    private int accountId;
    private int sold;

    public Transaction(int id, String title, String details, int accountId, int sold) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.accountId = accountId;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
