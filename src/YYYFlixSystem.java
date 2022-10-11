import java.io.*;
import java.util.*;

public class YYYFlixSystem {
    // fields
    ArrayList<User> connectedUsersList;

    // defines
    private static final String USERS_DATABASE_FILE_PATH = "UsersDatabase";
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
        initDatabaseFromPath(USERS_DATABASE_FILE_PATH, false);

        // init usernames hashset database
        initDatabaseFromPath(USERNAMES_HASHSET_DATABASE_FILE_PATH, true);

    }

    /**
     * initializes a database, given its path
     * @param isFile represents if the path is a regular file or not
     * @param path represents the path of the database file
     */
    public void initDatabaseFromPath(String path, boolean isFile) {
        try {

            // file stream of the database given its path
            File file = new File(path);

            // if exists than no need for change
            if(!file.exists())
            {
                // checks if the given path if a directory (user database) or file (username hashset database)
                if(isFile) {
                    // create a new database file if the file doesn't exist
                    file.createNewFile();
                }
                else {
                    // creates the directory in the given path
                    file.mkdirs();
                }
            }
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
            // check if the given object is a user
            if (object instanceof User) {
                // type-cast the object to user, inorder to get information of username for additional pathing
                User user = (User) object;

                // open file stream of user's database, sending path of database + additional file pathing
                // and true for appending to previous objects
                fos = new FileOutputStream(path + "/" + user.getUsername() + ".dat" , true);
            }
            // the given object is not a user
            else {
                // open file stream of a database, sending path of database and true for appending to previous objects
                fos = new FileOutputStream(path, true);
            }

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
            fi = new FileInputStream(USERS_DATABASE_FILE_PATH + "/" + username + ".dat") ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            User user = (User) oi.readObject();
            return user;

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
            File folder = new File(USERS_DATABASE_FILE_PATH);

            for (final File fileEntry : folder.listFiles()) {
                // open file stream of users database
                fi = new FileInputStream(fileEntry.getPath());

                // open object stream using the file stream
                oi = new ObjectInputStream(fi);                

                // read User object from the object stream
                User user = (User) oi.readObject();

                if(user != null)
                    System.out.println(user);
                else
                    System.out.println("[ERROR] Invalid user file found!");
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

    // updates the database file of the given user with the updated instance of the user
    public boolean updateUser(User user)
    {
        // delete previous database file
        if(!this.deleteUser(user.getUsername()))
            return false;
            
        // write new database file (no need for hashset update, username never changes)
        insertObjectIntoDatabase(user, USERS_DATABASE_FILE_PATH)

    }

    /**
     * deletes the database file of the given user
     * @param user represents the user we want to delete, username is the identifier
     * @return true only if the user's database file exists and has been deleted successfully, otherwise false
     */
    public boolean deleteUser(String username)
    {
        // file stream of given user's database file
        File file = new File(userPath(username));

        // return the result of deleting the user's database file
        return file.delete();
    }
/**
 * creates the relative path to the user's database file
 * @param username represents the username of the user's
 * @return the relative path of the user's database file
 */
    public String userPath(String username)
    {
        return USERS_DATABASE_FILE_PATH + "/" + username + ".dat";
    }
}
