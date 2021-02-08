package Domain;

public class User {

    private String usermame;
    private String password;
    private String email;
    private int id;

    public User(String username,String password,String email,int id){

        this.usermame=username;
        this.password=password;
        this.email=email;
        this.id=id;
    }


    public String getUsermame() {
        return usermame;
    }

    public void setUsermame(String usermame) {
        this.usermame = usermame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
