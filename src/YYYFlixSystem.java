import java.io.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import java.awt.event.*;

import javax.swing.JFrame;

public class YYYFlixSystem {
    // fields
    ArrayList<User> connectedUsersList;

    // defines
    private static final String USERS_DATABASE_FILE_PATH = "UsersDatabase";
    private static final String CONTENTS_DATABASE_FILE_PATH = "ContentDatabase";
    private static final String USERNAMES_HASHSET_DATABASE_FILE_PATH = "usernamesHashSetDatabase.dat";    

    /**
     * public constructor
     * resets fields and initializes the databases if necessary
     */
    public YYYFlixSystem() {        
        connectedUsersList = new ArrayList<>();

        this.initDatabases();        

        ModelLogin m = new ModelLogin("", "");
        ViewLogin v = new ViewLogin("YYYFlix");
        ControllerLogin c = new ControllerLogin(m, v);

        // action for pressing login
        v.getLogin().addActionListener(e -> login(m.getUsername(), m.getPassword()));
        c.initController();        
    }

    /**
     * initializes the databases if necessary
     */
    public void initDatabases() {
        // init users database
        initDatabaseFromPath(USERS_DATABASE_FILE_PATH, false);

        // init contents database
        initDatabaseFromPath(CONTENTS_DATABASE_FILE_PATH, false);

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

        // inserts the user into the user database and inserts the username into the username hashset
        if(!insertAndAddUser(user))
            return null;

        // return the newly created user
        return user;
    }

    public Content createContent() {
        // input scanner
        Scanner scan = new Scanner(System.in);

        // get content type from user
        System.out.println("Please enter your desired content [Commercial / Movie / TVShow]: ");
        String contentStr = scan.nextLine();   
        
        // use enum to grab the content type
        Content.VALID_CONTENT_TYPES contentType = Content.isContentValid(contentStr);   
        while(contentType == null)
        {
            System.out.println("[ERROR]: Given content is not valid.");
            System.out.println("Please enter your desired content [Commercial / Movie / TVShow]: ");
            contentStr = scan.nextLine();   
            contentType = Content.isContentValid(contentStr);  
        }

        //#region gets content fields from user
       
        // get format from user
        System.out.println("Please enter your desired format: ");
        String format = scan.nextLine();

        // get subtitlesFileName from user
        System.out.println("Please enter your desired subtitles file name: ");
        String subtitlesFileName = scan.nextLine();

        // get name from user
        System.out.println("Please enter your desired name: ");
        String name = scan.nextLine();

        // get length from user
        System.out.println("Please enter your desired length: ");
        float length = Float.parseFloat(scan.nextLine());

         //#endregion                     
        
        //#region create the derived content object
        Content content = null;

        switch(contentType)
        {
            // Commercial
            case Commercial:
                // get publisher from user
                System.out.println("Please enter your desired publisher: ");
                String publisher = scan.nextLine(); 
                
                // create Commercial object from the given parameters
                content = new Commercial(format, subtitlesFileName, name, length, publisher);
                break;

            // Movie
            case Movie:
                // get director from user
                System.out.println("Please enter your desired director: ");
                String director = scan.nextLine(); 
                
                // create Movie object from the given parameters     
                content =  new Movie(format, subtitlesFileName, name, length, director);       
                break;

            // TVShow
            case TVShow:
                // get season from user
                System.out.println("Please enter your desired season: ");
                int season = Integer.parseInt(scan.nextLine()); 

                // get episode from user
                System.out.println("Please enter your desired episode: ");
                int episode = Integer.parseInt(scan.nextLine());                 
                
                // create TVShow object from the given parameters     
                content =  new TVShow(format, subtitlesFileName, name, length, season, episode);       
                break;

            default:
                // TODO: maybe throws exception because for the given content there is no getting details from user implementation        
                content =  null;        
                break;                          
        }
        //#endregion

        // finished input from user, close input scanner
        scan.close();

        // inserts the user into the user database and inserts the username into the username hashset
        if(!insertObjectIntoDatabase(content, YYYFlixSystem.CONTENTS_DATABASE_FILE_PATH))
            return null;

        // return the newly created user
        return content;

    }
    // TODO: Different name?
    /**
     * inserts the user into the user database and inserts the username into the username hashset
     * @param user represents the given user to be inserted into both databases
     * @return true if all of the insertions have been successfully completed, false otherwise
     */
    public boolean insertAndAddUser(User user) {

        // insert the user object into the database
        if(!this.insertObjectIntoDatabase(user, USERS_DATABASE_FILE_PATH))
        {
            System.out.println("Register Failed, Please try again.");
            return false;
        }
        
        // add username to the hashset database, in lower cases to make sure hashset contains function works well
        if(!this.addUsernameToHashset(user.getUsername()))
        {
            System.out.println("Register Failed, Please try again.");
            return false;
        }        

        return true;
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
                if(oi != null)
                    oi.close();

                if(fi != null)
                    fi.close();

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
            return !hashSet.contains(username.toLowerCase());

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
                if(oi != null)
                    oi.close();

                if(fi != null)
                    fi.close();

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
                User obj = (User) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.USERS_DATABASE_FILE_PATH, obj.getUsername()
                                                      )
                                          );
            }
            // check if the given object is a content
            else if (object instanceof Content) {
                // type-cast the object to user, inorder to get information of username for additional pathing
                Content obj = (Content) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.CONTENTS_DATABASE_FILE_PATH, String.valueOf(obj.getID())
                                                      )
                                          );
            }            
            // the given object is not a user
            else {
                // open file stream of a database, sending path of database
                fos = new FileOutputStream(path);
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

    public boolean logout(User user){

        // upon successful remove from the connected list, it means the user was connected, else, they were not.
        if(this.connectedUsersList.remove(user)) {
            System.out.println("Logout successful, hope to see you soon " + user.getName() + "!");
            return true;    
        }
        
        System.out.println("Failed to logout, user " + user.getUsername() + " is not logged in.");
        return false;
    }


    // TODO: make readObject and check for instance of
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
            fi = new FileInputStream(objectPath(USERS_DATABASE_FILE_PATH, username)) ;

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

    /**
     * read content from the database that matches the content id
     * @param contentID the content id of the searched for content in the database
     * @return if a matching content is found, returns the content,if not, returns null
     */
    public Content readContent(int contentID)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(objectPath(CONTENTS_DATABASE_FILE_PATH, String.valueOf(contentID))) ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            Content content = (Content) oi.readObject();
            return content;

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

    /**
     * prints all the users in the database
     */
    public void printObjects(String path) {

        File folder = new File(path);

        ExecutorService executor = Executors.newCachedThreadPool();

        for (final File fileEntry : folder.listFiles()) {
            

            executor.execute(fileRunnable(fileEntry));                
        }
        
        executor.shutdown();

        try {
            while(!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        return;        
    }    

    /**
     * prints all the users in the database
     */
    public void printUsers() {
        System.out.println("==============================================");
        System.out.println("Users Database:");
        printObjects(USERS_DATABASE_FILE_PATH); 
        System.out.println("==============================================");             
    }

    /**
     * prints all the users in the database
     */
    public void printContents() {
        System.out.println("==============================================");
        System.out.println("Content Database:");
        printObjects(CONTENTS_DATABASE_FILE_PATH);  
        System.out.println("==============================================");          
    }
    
    /**
     * prints the usernames hashset
     */
    public void printUsernamesHashset() {        
        System.out.println("==============================================");
        System.out.println("Usernames HashSet Database:");
        System.out.println(readUsernamesHashSet());
        System.out.println("==============================================");
    }    

    /**
     * the task of openning a single file
     * @param fileEntry represents the file to be opened
     * @return a runnable task of openning a file
     */
    private Runnable fileRunnable(File fileEntry) {
        return () -> {

            FileInputStream fi = null;
            ObjectInputStream oi = null;

            try {
                // open file stream of users database
                fi = new FileInputStream(fileEntry.getPath());
    
                // open object stream using the file stream
                oi = new ObjectInputStream(fi);                

                Object obj = oi.readObject();

                // type-cast the object to 
                if (obj instanceof User) {
                    
                    User user = (User) obj;

                    if(user != null)
                        System.out.println(user);                 
                }
                else if (obj instanceof Commercial) {

                    Commercial commercial = (Commercial) obj;

                    if(commercial != null)
                        System.out.println(commercial);      
                }

                else if (obj instanceof Movie) {

                    Movie movie = (Movie) obj;

                    if(movie != null)
                        System.out.println(movie);      
                }

                else if (obj instanceof TVShow) {

                    TVShow tvShow = (TVShow) obj;

                    if(tvShow != null)
                        System.out.println(tvShow);      
                }
                else {
                    System.out.println("[ERROR] Invalid object file found!");
                }
    
                if(oi != null)
                    oi.close();
    
                if(fi != null)
                    fi.close();

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

        };
    }

    // updates the database file of the given user with the updated instance of the user
    public boolean updateUser(User user)
    {
        // delete previous database file
        if(!this.deleteUser(user.getUsername()))
            return false;
            
        // write new database file (no need for hashset update, username never changes)
        return true;

    }

    /**
     * deletes the database file of the given user
     * @param username represents the user we want to delete, username is the identifier
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
    public String objectPath(String path, String ending)
    {
        return path + "/" + ending + ".dat";
    }

    /**
     * creates the relative path to the user's database file
     * @param username represents the username of the user's
     * @return the relative path of the user's database file
     */
    public String userPath(String username)
    {
        return objectPath(USERS_DATABASE_FILE_PATH, username);
        //return USERS_DATABASE_FILE_PATH + "/" + username + ".dat";
    }

    // TODO: Maybe no need for password security check?
    //       OR add security check for each function change of details (payment method, name, password)
    /**
     * changes the password of the given user both locally and in the database
     * @param user represents the the given user to be updated
     * @param oldPassword represents the old password for security check
     * @param newPassword represents the new password to be updated
     * @return true if the update has been sucessful, false otherwise
     */    
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        // check if the old password is currently the user's password
        if(!user.isPasswordCorrect(oldPassword)) {
            System.out.println("The given 'old' password does not match the current user's password.");
            return false;
        }

        // check if the new password is valid
        if(!User.isPasswordValid(newPassword))
        {
            System.out.println("The given 'new' password is not valid, please enter a valid password (at least 6 characters).");
            return false;
        }
        
        // update locally the user's password
        if(!user.setPassword(newPassword))
        {
            System.out.println("Failed to update the user's password due to an internal error, please try again.");
            return false;
        }
        
        // updates the object of user in the database to be the given object
        return updateUserInDatabase(user);
    }

    /**
     * changes the payment method of the given user both locally and in the database
     * @param user represents the the given user to be updated
     * @param newName represents the new name to be updated
     * @return true if the update has been sucessful, false otherwise
     */    
    public boolean changeName(User user, String newName) {
        // update locally the user's name
        if(!user.setName(newName))
        {
            System.out.println("Failed to update the user's name due to an internal error, please try again.");
            return false;
        }

        // updates the object of user in the database to be the given object
        return updateUserInDatabase(user);
    }

    /**
     * changes the payment method of the given user both locally and in the database
     * @param user represents the the given user to be updated
     * @param newPaymentMethod represents the new payment method to be updated
     * @return true if the update has been sucessful, false otherwise
     */
    public boolean changePaymentMethod(User user, String newPaymentMethod) {
        // update locally the user's payment method
        if(!user.setPaymentMethod(newPaymentMethod))
        {
            System.out.println("Failed to update the user's payment method due to an internal error, please try again.");
            return false;
        }        

        // updates the object of user in the database to be the given object
        return updateUserInDatabase(user);
    }

    /**
     * updates an old copy of user in the database to be the new given copy
     * @param user the given user instance to be updated in the database
     * @return true if the update has been sucessful (the given user has been found in the database and has been removed), false otherwise
     */
    public boolean updateUserInDatabase(User user) {

        // deletes the database of the current user, prepares for new database file
        if(!deleteUser(user.getUsername()))
        {
            System.out.println("User " + user.getUsername() + " has not been found inside the database, can't update the instance.");
            return false;
        }

        // inserts the newly updated user into the database
        if(!this.insertObjectIntoDatabase(user, USERS_DATABASE_FILE_PATH))
        {
            System.out.println("Failed to insert the updated user into database.");
            // TODO: Probably exception throw because user is now not in the database
            return false;
        }

        // user's details has been successfully updated in the database
        System.out.println("Details have been updated successfully.");
        return true;
    }
}
