public class User {
    // fields
    private String username;
    private String password;
    public String name;
    private String paymentMethod;

    private static final int MIN_PASSWORD_LEN = 6;

    // public constructor
    public User(String username, String password, String name, String paymentMethod)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.paymentMethod = paymentMethod;
    }

    // TODO: Add a logic function (MUST)

    /**
     * checks for valid password
      * @param password represents the password that needs to be checked
     * @return true if the given password is valid, false if it is not valid
     */
    public static boolean isPasswordValid(String password)
    {
        // at least MIN characters for the password
        return password.length()>=MIN_PASSWORD_LEN;
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
