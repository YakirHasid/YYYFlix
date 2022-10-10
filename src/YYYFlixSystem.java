import java.io.*;
import java.util.*;

public class YYYFlixSystem {
    // fields
    ArrayList<User> connectedUsersList;

    // defines
    private static final String USERS_DATABASE_FILE_PATH = "usersDatabase.dat";
    private static final String USERNAMES_HASHSET_DATABASE_FILE_PATH = "usernamesHashSetDatabase.dat";

    /**
     * public constructor
     * resets fields and initializes the databases if necessary
     */
    public YYYFlixSystem() {
        connectedUsersList = new ArrayList<>();

        this.initDatabases();
    }

    /**
     * initializes the databases if necessary
     */
    public void initDatabases() {
        // init users database
        initDatabaseFromPath(USERS_DATABASE_FILE_PATH);

        // init usernames hashset database
        initDatabaseFromPath(USERNAMES_HASHSET_DATABASE_FILE_PATH);

    }

    /**
     * initializes a database, given its path
     * @param path represents the path of the database file
     */
    public void initDatabaseFromPath(String path) {
        try {
            // file stream of the database given its path
            File file = new File(path);

            // create a new database file if the file doesn't exist
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
        while(User.isPaymentMethodValid(paymentMethod) == null)
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
        
        // add username to the hashset database, in lower cases to make sure hashset contains function works well
        if(!this.addUsernameToHashset(user.getUsername()))
        {
            System.out.println("Register Failed, Please try again.");
            return null;
        }

        // return the newly created user
        return user;
    }

    /**
     * adds the given username into the hashset database
     * @param username represents the username to be added into the database
     */
    private boolean addUsernameToHashset(String username)
    {
                // read usernames hash set from database
                Set<String> set = this.readUsernamesHashSet();

                // if hash set in database is invalid or not init
                if(set == null)
                    set = new HashSet<String>();
        
                // add the username of the new user into the hashset
                if(!set.add(username.toLowerCase()))
                    return false;
        
                // write the new usernames hash set into the database
                return this.writeUsernameHashSet(set);
    }

    public boolean writeUsernameHashSet(Set<String> set)
    {
        // delete the previous hashset database
        File file = new File(USERNAMES_HASHSET_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        return insertObjectIntoDatabase(set, USERNAMES_HASHSET_DATABASE_FILE_PATH);
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
            //TODO: [BUG] Openning file stream for this file deletes the previous file, so user database only saves the last written user
            // open file stream of a database, sending path of database and true for appending to previous objects
            fos = new FileOutputStream(path, true);

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
                // close object stream
                if(oos != null)
                    oos.close();                

                // close file stream
                if(fos != null)
                    fos.close();

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
        if(this.isUsernameValid(username)){
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
            fi = new FileInputStream(USERS_DATABASE_FILE_PATH);

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
                // close object stream
                if(oi != null)
                    oi.close();

                // close file stream
                if(fi != null)
                    fi.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        return null;
    }

    public void printUsers() {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(USERS_DATABASE_FILE_PATH);

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // TODO: [BUG] two consecutive reads results in exception
            //             first one reads the first object stored as usual
            //             second one results in exception of EOF            
            // read User object from the object stream until a matching user is found
            User user = (User) oi.readObject();

            while(user!=null) {
                // print user details
                System.out.println(user);
                
                // read next user
                user = (User) oi.readObject();
            }

        // catch all the thrown exceptions, close all open streams in finally
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            return;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } finally {
            try {
                // close object stream
                if(oi != null)
                    oi.close();

                // close file stream
                if(fi != null)
                    fi.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        return;        
    }

    public void printUsernamesHashset()
    {
        System.out.println(readUsernamesHashSet());
    }
}
