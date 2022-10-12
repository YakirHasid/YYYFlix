import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    // fields
    private final String username;
    private String password;
    public String name;
    private String paymentMethod;

    private static final int MIN_PASSWORD_LEN = 6;
    private static final String[] VALID_PAYMENT_METHODS = {"PayPal", "VISA"};

    // public constructor
    public User(String username, String password, String name, String paymentMethod)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.paymentMethod = paymentMethod;
    }

    /**
     * override equals for HashSet usage
     * @param o compared to object
     * @return true if the objects are equals, false otherwise (checked on all fields)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(paymentMethod, user.paymentMethod);
    }

    /**
     * override hashCode for HashSet usage
     * @return hash of the current object
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, paymentMethod);
    }

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

    /**
     * checks if the given payment method is a valid form of payment from the VALID_PAYMENT_METHODS array
     * @param payment represents the given payment method
     * @return a valid matching payment method (case corrected if necessary), null if given payment is not valid
     */
    public static String isPaymentMethodValid(String payment)
    {
        for (String paymentMethod :
                VALID_PAYMENT_METHODS) {
            if(payment.equalsIgnoreCase(paymentMethod))
                return paymentMethod;
        }
        return null;
    }

    /**
     * masks the current user's password with '*'
     * @return a masked password, so that each character is replaced by '*'
     *         password of "123456" => "******"
     *         password of "abc" => "***"
     */
    private String maskPassword()
    {
        return "*".repeat(this.password.length());
    }

    // username getter
    public String getUsername()
    {
        return this.username;
    }

    /**
     * password setter with validation
     * (external database update is assumed to be handled by the caller)
     * @param password represents the updated password
     * @return true if the given password is valid and local password update has been successfully completed
     *         false if the given password is not valid
     */
    public boolean setPassword(String password) {
        // check if password is valid
        if(!isPasswordValid(password))
            return false;

        // password is valid, update user password and return true
        // (external database update is assumed to be handled by the caller)
        this.password = password;
        return true;
    }

    // name getter
    public String getName() {
        return this.name;
    }

    // name setter (external database update is assumed to be handled by the caller)
    public boolean setName(String name) {
        this.name = name;
        return false;
    }

    public String getPaymentMethod()
    {
        return this.paymentMethod;
    }

    /**
     * payment method setter with validation
     * (external database update is assumed to be handled by the caller)
     * @param paymentMethod represents the updated payment method
     * @return true if the given password is valid and local password update has been successfully completed
     *         false if the given password is not valid
     */
    public boolean setPaymentMethod(String paymentMethod) {
        // check for valid payment method checker
        String payment = isPaymentMethodValid(paymentMethod);

        // if invalid return false
        if(payment == null)
            return false;

        // payment method is valid, update payment method and return true
        // (external database update is assumed to be handled by the caller)
        this.paymentMethod = payment;
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
                "Payment Method: " + this.paymentMethod + "\n";
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }
}
