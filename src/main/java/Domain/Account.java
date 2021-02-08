package Domain;

public class Account {


    private int id;
    private String name;
    private int userId;
    private int sold;
    private boolean debt;

    public Account(int id, String name, int userId, int sold, boolean debt) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.sold = sold;
        this.debt = debt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public boolean isDebt() {
        return debt;
    }

    public void setDebt(boolean debt) {
        this.debt = debt;
    }
}
