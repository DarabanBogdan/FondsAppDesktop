package Domain;

public class UserL {

    private String usermame;
    private String password;
    private String email;
    private int id;
    private String created_at;
    private String updated_at;


    public UserL(String username, String password, String email, int id, String created_at, String updated_at){

        this.usermame=username;
        this.password=password;
        this.email=email;
        this.id=id;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
