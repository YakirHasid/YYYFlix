public class User {
    // fields
    private String username;
    private String password;
    public String name;
    private String paymentMethod;

    // public constructor
    public User(String username, String password, String name, String paymentMethod)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.paymentMethod = paymentMethod;
    }

    public String getUsername()
    {
        return this.username;
    }
    public boolean setUsername(String username) {
        // TODO: Implement
        return false;
    }

    public String getPassword() {
        return this.password;
    }
    public boolean setPassword(String password) {
        // TODO: Implement
        return false;
    }

    public String getName() {
        return this.name;
    }
    public boolean setName(String username) {
        // TODO: Implement
        return false;
    }

    public String getPaymentMethod()
    {
        return this.paymentMethod;
    }
    public boolean setPaymentMethod(String username) {
        // TODO: Implement
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
