import java.util.ArrayList;
import java.util.Scanner;

public class YYYFlixSystem {
    ArrayList<User> connectedUsersList;

    private static final String[] VALID_PAYMENT_METHODS = {"PayPal", "VISA"};

    /**
     * register a new user, ask for details in signup, default the user to free
     */
    public User register()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your desired username: ");

        // get username from user
        String username = scan.nextLine();
        while(!this.isUsernameValid())
        {
            System.out.println("[ERROR]: Username is taken.");
            System.out.println("Please enter your desired username: ");
            username = scan.nextLine();
        }

        System.out.println("Please enter your desired password: ");

        // get password from user
        String password = scan.nextLine();
        while(!User.isPasswordValid(password))
        {
            System.out.println("[ERROR]: Password is invalid (password needs to be at least 6 characters long).");
            System.out.println("Please enter your desired password: ");
            password = scan.nextLine();
        }

        System.out.println("Please enter your desired name: ");

        // get name from user
        String name = scan.nextLine();

        System.out.println("Please enter your desired payment method [PayPal/VISA]: ");

        String paymentMethod = scan.nextLine();

        while(isPaymentMethodValid(paymentMethod) == null)
        {
            System.out.println("[ERROR]: Payment is invalid (please choose PayPal or VISA).");
            System.out.println("Please enter your desired payment method [PayPal/VISA]: ");
            paymentMethod = scan.nextLine();
        }

        if(!this.insertUserIntoDatabase())
        {
            return null;
        }

        User user = new User(username, password, name, paymentMethod);
        return user;
    }


    public boolean isUsernameValid()
    {
        // TODO: Implement username check method from files
        return true;
    }

    public String isPaymentMethodValid(String payment)
    {
        for (String paymentMethod :
                this.VALID_PAYMENT_METHODS) {
            if(payment.equalsIgnoreCase(paymentMethod))
                return paymentMethod;
        }
        return null;
    }

    /**
     *
     * @return true
     */
    public boolean insertUserIntoDatabase()
    {
        return false;
    }
}
