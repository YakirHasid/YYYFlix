import java.io.*;
import java.util.*;

public class YYYFlixSystem {
    ArrayList<User> connectedUsersList;

    private static final String[] VALID_PAYMENT_METHODS = {"PayPal", "VISA"};
    private static final String USERS_DATABASE_FILE_PATH = "usersDatabase.dat";
    private static final String USERNAMES_HASHSET_DATABASE_FILE_PATH = "usernamesHashSetDatabase.dat";

    // TODO: Add constructor that on initialization creates all the needed files (empty)
    /**
     * register a new user, ask for details in signup, default the user to free
     */
    public User register()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your desired username: ");

        // get username from user
        String username = scan.nextLine();
        while(!this.isUsernameValid(username))
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

        User user = new User(username, password, name, paymentMethod);
        if(!this.insertUserIntoDatabase(user))
        {
            System.out.println("Register Failed, Please try again.");
            return null;
        }

        System.out.println("Register Successfully completed.");
        return user;
    }

    /**
     * checks if the given username is not used, implemented with checking for key in hash set
      * @param username the given username to check
     * @return true if the username is not in the hash set, therefore no user is registered with that username in the database
     *         false if the username is in the hash set, therefore a user is already registered with that username in the database
     */
    public boolean isUsernameValid(String username) {

        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(USERNAMES_HASHSET_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);

            // the set that contains all the usernames inside the database
            Set<String> hashSet = (HashSet<String>) oi.readObject();
            return !hashSet.contains(username);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    // needs to be deleted
    /*
    public boolean isUsernameValid(String username) {
        // TODO: Implement username check method from files
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(USERS_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);

            User user = (User) oi.readObject();
            while (user != null) {
                if (user.getUsername().equalsIgnoreCase(username))
                    return false;

                user = (User) oi.readObject();
            }

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }
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
     *
     * @return true
     */
    public boolean insertUserIntoDatabase(User user) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(USERS_DATABASE_FILE_PATH);
            oos = new ObjectOutputStream(fos);

            // write object to file
            oos.writeObject(user);

            // TODO: add the username into the hash map database

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            return false;
        } finally {
            try {
                fos.close();
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
