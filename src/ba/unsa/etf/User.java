package ba.unsa.etf;

public class User extends Person {
    private String email;
    private String username;
    private String password;

    public User(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName);
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
