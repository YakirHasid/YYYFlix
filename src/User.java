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

    private String maskPassword()
    {
        return "*".repeat(this.password.length());
    }

    public String getUsername()
    {
        return this.username;
    }
    public boolean setUsername(String username) {
        // TODO: Implement (add validation for free username)
        return false;
    }

    public boolean setPassword(String password) {
        // TODO: Implement (add validation for valid password)
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

    /**
     * toString method of the User
     * @return a string that represents the User
     */
    @Override
    public String toString() {
        return this.name + "'s Details:\n" +
                "Username: " + this.username + "\n" +
                "Password (Masked): " + this.maskPassword() + "\n" +
                "Payment Method" + this.paymentMethod + "\n";
    }
}
