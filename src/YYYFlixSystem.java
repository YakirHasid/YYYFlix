import java.io.*;
import java.util.*;

public class YYYFlixSystem {
    ArrayList<User> connectedUsersList;

    private static final String[] VALID_PAYMENT_METHODS = {"PayPal", "VISA"};
    private static final String USERS_DATABASE_FILE_PATH = "usersDatabase.dat";
    private static final String USERNAMES_HASHSET_DATABASE_FILE_PATH = "usernamesHashSetDatabase.dat";

    public YYYFlixSystem() {
        connectedUsersList = new ArrayList<>();

        this.initDatabases();
    }

    
    public void initDatabases() {

        initDatabaseFromPath(USERS_DATABASE_FILE_PATH);
        initDatabaseFromPath(USERNAMES_HASHSET_DATABASE_FILE_PATH);

    }

    public void initDatabaseFromPath(String path) {
        try {
            File file = new File(path);

            if(!file.exists())
                file.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * register a new user, ask for details in signup, default the user to free
     * @return the newly created and registered User
     */
    public User register()
    {
        // input scanner
        Scanner scan = new Scanner(System.in);

        // get username from user
        System.out.println("Please enter your desired username: ");
        String username = scan.nextLine();
        while(!this.isUsernameValid(username))
        {
            System.out.println("[ERROR]: Username is taken.");
            System.out.println("Please enter your desired username: ");
            username = scan.nextLine();
        }

        // get password from user
        System.out.println("Please enter your desired password: ");
        String password = scan.nextLine();
        while(!User.isPasswordValid(password))
        {
            System.out.println("[ERROR]: Password is invalid (password needs to be at least 6 characters long).");
            System.out.println("Please enter your desired password: ");
            password = scan.nextLine();
        }

        // get name from user
        System.out.println("Please enter your desired name: ");
        String name = scan.nextLine();

        // get payment method from user
        System.out.println("Please enter your desired payment method [PayPal/VISA]: ");
        String paymentMethod = scan.nextLine();
        while(isPaymentMethodValid(paymentMethod) == null)
        {
            System.out.println("[ERROR]: Payment is invalid (please choose PayPal or VISA).");
            System.out.println("Please enter your desired payment method [PayPal/VISA]: ");
            paymentMethod = scan.nextLine();
        }

        // finished input from user, close input scanner
        scan.close();

        // create user object from the given parameters
        User user = new User(username, password, name, paymentMethod);

        // insert the user object into the database
        if(!this.insertObjectIntoDatabase(user, USERS_DATABASE_FILE_PATH))
        {
            System.out.println("Register Failed, Please try again.");
            return null;
        }
        
        // add username to the hashset database
        this.addUsernameToHashset(user.getName());

        // return the newly created user
        return user;
    }

    private void addUsernameToHashset(String username)
    {
                // read usernames hash set from database
                Set<String> set = this.readUsernamesHashSet();

                // if hash set in database is invalid or not init
                if(set == null)
                    set = new HashSet<String>();
        
                // add the username of the new user into the hashset
                set.add(username);
        
                // write the new usernames hash set into the database
                this.writeUsernameHashSet(set);
    }

    public boolean writeUsernameHashSet(Set<String> set)
    {
        // delete the previous hashset database
        File file = new File(USERNAMES_HASHSET_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        insertObjectIntoDatabase(set, USERNAMES_HASHSET_DATABASE_FILE_PATH);
        return true;
    }

    public HashSet<String> readUsernamesHashSet()
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(USERNAMES_HASHSET_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            return (HashSet<String>) oi.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EOFException e) {
            return null;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } finally {
            try {
                if(fi != null)
                    fi.close();

                if(oi != null)
                    oi.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        return null;
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
            // open file stream of username hashset database
            fi = new FileInputStream(new File(USERNAMES_HASHSET_DATABASE_FILE_PATH));

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            Set<String> hashSet = (HashSet<String>) oi.readObject();
            return !hashSet.contains(username);

        // catch all the thrown exceptions, close all open streams in finally
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            return true;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } finally {
            try {
                if(fi != null)
                    fi.close();

                if(oi != null)
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

    /*
    public boolean insertUsernameToHashSetDatabase(String username)
    {
        File fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(USERNAMES_HASHSET_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            Set<String> hashSet = (HashSet<String>) oi.readObject();
            return !hashSet.contains(username);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EOFException e) {
            return true;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } finally {
            try {
                if(fi != null)
                    fi.close();

                if(oi != null)
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
     * inserts an object into a database file
     * @param object represents the object that is required to be inserted into the database file
     * @param path represents the path of the database file
     * @return true if insert has been successfully completed, false otherwise
     */
    public boolean insertObjectIntoDatabase(Object object, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            // open file stream of a database
            fos = new FileOutputStream(path);

            // open object stream using the file stream
            oos = new ObjectOutputStream(fos);

            // write object to file
            oos.writeObject(object);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            return false;
        } finally {
            try {
                // close file stream
                if(fos != null)
                    fos.close();

                // close object stream
                if(oos != null)
                    oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    // login function
    public boolean login(String username, String password)
    {
        // quick check if the username is even in the database, using the username hashset
        if(!this.isUsernameValid(username)){
            System.out.println("Username is not in the database.");
            return false;
        }
            
        // a user with the matching username is inside the database
        // now we need to check for matching password

        // read the user with the matching username
        User user = readUser(username);

        if(user.isPasswordCorrect(password))
        {
            System.out.println("Login successful, welcome back " + user.getName() + "!");
            this.connectedUsersList.add(user);
            return true;
        }

        System.out.println("[ERROR]: Login Failed, password is wrong.");
        return false;
            
    }

    /**
     * read user from the database that matches the username
     * @param username the username of the searched for user in the database
     * @return if a matching user is found, returns the user,if not, returns null
     */
    public User readUser(String username)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(new File(USERS_DATABASE_FILE_PATH));

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            User user = (User) oi.readObject();
            while(user!=null) {
                if(user.getUsername().equals(username))
                    return user;

                user = (User) oi.readObject();
            }

        // catch all the thrown exceptions, close all open streams in finally
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            return null;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } finally {
            try {
                // close file stream
                if(fi != null)
                    fi.close();

                // close object stream
                if(oi != null)
                    oi.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        return null;
    }
}
