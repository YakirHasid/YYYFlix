import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class YYYFlixSystem {
    // fields

    User connectedUser;
    Library userLibrary;
    UserSubscriptionDetails userSubDetails;

    ModelMenu m;
    ViewMenu v;
    ControllerMenu c;

    // a single scanner to be used globaly by the program
    private static Scanner scan = new Scanner(System.in);

    private static ArrayList<Content> contentInLibraryHolder;

    // defines
    private static final String USERS_DATABASE_FILE_PATH = "UsersDatabase";
    private static final String LIBRARIES_DATABASE_FILE_PATH = "LibrariesDatabase";
    private static final String USERS_SUBS_DETAILS_DATABASE_FILE_PATH = "UsersSubsDetailsDatabase";
    private static final String NOTIFY_USER_DATABASE_FILE_PATH = "NotifyUserDatabase";
    private static final String SUBS_DATABASE_FILE_PATH = "SubsDatabase";
    private static final String CONTENTS_DATABASE_FILE_PATH = "ContentDatabase";
    private static final String USERNAMES_HASHSET_DATABASE_FILE_PATH = "usernamesHashSetDatabase.dat";
    private static final String LAST_CONTENT_ID_DATABASE_FILE_PATH = "contentID.dat";
    private static final String LAST_TRANSACTION_ID_DATABASE_FILE_PATH = "transactionID.dat";
    private static final String LAST_SUBSCRIPTION_ID_DATABASE_FILE_PATH = "subscriptionID.dat";

    /**
     * public constructor
     * resets fields and initializes the databases if necessary
     */
    public YYYFlixSystem() {        

        connectedUser = null;

        this.initDatabases();

        Integer counter;

        counter  = readContentCounter();
        if(counter==null)
            Content.COUNTER = 0;
        else
            Content.COUNTER=counter;


        counter = readTransactionCounter();
        if(counter==null)
            UserSubscriptionDetails.COUNTER = 0;
        else
            UserSubscriptionDetails.COUNTER=counter;            

        this.m = new ModelMenu("", "", "");
        this.v = new ViewMenu("YYYFlix");
        this.c = new ControllerMenu(this.m, this.v);

        // action for pressing login
        v.getLogin().addActionListener(e -> login(m.getUsername(), m.getPassword()));

        // action for pressing logout
        v.getLogout().addActionListener(e -> logout());

        // menu buttons
        v.getM_Menu_Register().addActionListener(e -> register());
        v.getM_Menu_CreateContent().addActionListener(e -> createContent());
        v.getM_Menu_AddContentToYourLibrary().addActionListener(e -> addContentToLibrary());        
        v.getM_Menu_Subscribe().addActionListener(e -> subscribe());
        v.getM_Menu_SendNotificationToAUser().addActionListener(e -> notifyUser());
        v.getM_Menu_Print_MyUserDetails().addActionListener(e -> printConnectedUser());
        v.getM_Menu_Print_MyLibrary().addActionListener(e -> printLibrary());
        v.getM_Menu_Print_SubscriptionDetails().addActionListener(e -> printUserSubDetails());
        v.getM_Menu_Print_Notifications().addActionListener(e -> printNotifyUser());
        v.getM_Menu_Change_Name().addActionListener(e -> changeName());
        v.getM_Menu_Change_Password().addActionListener(e -> changePassword());
        v.getM_Menu_Change_PaymentMethod().addActionListener(e -> changePaymentMethod());        

        c.initController();   
    }

    /**
     * helper funcition that checks if there is a connected user, notifies the gui accordingly
     * @return true if there is a user connected, false otherwise
     */     
    private boolean isLoggedIn() {
        if(this.connectedUser == null) {
            this.c.sayNotLoggedIn();
            return false;
        }

        return true;
    }

    /**
     * inities the change payment method procedure, allows the user to update their payment method in the database
     * @return true if the change has been sucessfull, false otherwise
     */     
    private boolean changePaymentMethod() {
        if(!isLoggedIn()) {
            return false;
        }

        System.out.println("Please enter your desired payment method: ");
        String paymentMethod = scan.nextLine();

        returnToGUIMessage();
        return changePaymentMethod(connectedUser, paymentMethod);
    }

    /**
     * inities the change password procedure with security check, allows the user to update their password in the database
     * @return true if the change has been sucessfull, false otherwise
     */    
    private boolean changePassword() {
        if(!isLoggedIn()) {
            return false;
        }

        System.out.println("Please enter your old password: ");
        String oldPassword = scan.nextLine();

        System.out.println("Please enter the new password you desire: ");
        String newPassword = scan.nextLine();

        returnToGUIMessage();
        return changePassword(this.connectedUser, oldPassword, newPassword);
    }

    /**
     * inities the change name procedure, allows the user to update their name in the database
     * @return true if the change has been sucessfull, false otherwise
     */
    private boolean changeName() {
        if(!isLoggedIn()) {
            return false;
        }

        System.out.println("Please enter the new name you desire: ");
        String newName = scan.nextLine();

        returnToGUIMessage();
        return changeName(this.connectedUser, newName);
    }

    /**
     * start method that welcomes the user to the program
     */
    public void start() {
        String message = "Welcome to YYYFlix inc.";
        System.out.println(message);
        this.c.returnToGUIMessage(message);
    }

    /**
     * inities the notify user procedure, sending a notification to a user
     */
    private void notifyUser() {
        if(!isLoggedIn()) {
            return;
        }
                        
        System.out.println("Please enter the username of the user you want to send a message to: ");
        String username = scan.nextLine();
        while(this.isUsernameValid(username))
        {
            System.out.println("[ERROR]: No user exists with that username.");
            System.out.println("Please enter the username of the user you want to send a message to: ");
            username = scan.nextLine();
        }

        System.out.println("Please enter the message title you want to send to the user: ");
        String messageTitle = scan.nextLine();
        System.out.println("Please enter the message text you want to send to the user: ");
        String messageText = scan.nextLine();

        Notification notification = new Notification(0, this.connectedUser.getUsername() , messageTitle, messageText);
        NotifyUser notifyUser = readNotifyUser(username);
        notifyUser.addNotification(notification);
        updateNotifyUserInDatabase(notifyUser);   
        
        String message = "Successfully send " + username + " a notification.";

        // alert the user
        returnToGUIMessage(message);

    }

    /**
     * prints all the details of the connected user
     */
    private void printConnectedUser() {
        if(this.connectedUser == null) {
            this.c.sayNotLoggedIn();
            return;
        }

        this.c.returnToGUIMessage(this.connectedUser.toString());
        return;
    }    

    /**
     * prints the library of the connected user, using Threads as shown in the flow diagram
     */
    private void printLibrary() {
        if(!isLoggedIn()) {
            return;
        }

        ArrayList<Integer> contentIDList = this.userLibrary.getContentIDList();
        String message = this.userLibrary.libraryHeader(this.connectedUser.getName());        

        ExecutorService executor = Executors.newCachedThreadPool();

        contentInLibraryHolder = new ArrayList<>();
                        
        for (Integer contentID : contentIDList) {            
            executor.execute(readContentTask(contentID));
        }

        executor.shutdown();

        try {
            while(!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {            
            e.printStackTrace();
        }        
                
        for(Content content : YYYFlixSystem.contentInLibraryHolder) {
            if(content != null)
            message += content  + "\n";
        else
            message += "[this content has been deleted]" + "\n";            
        }

        message += "Total Library Size = " + contentIDList.size() + "\n";

        System.out.println(message);
        this.c.returnToGUIMessage(message);                
    }

    /**
     * prints the user subscription details of the connected user
     */
    private void printUserSubDetails() {
        if(!isLoggedIn()) {
            return;
        }

        this.c.returnToGUIMessage(this.userSubDetails.printReciept(this.connectedUser.getName()));
    }    

    /**
     * prints the notify user of the connected user
     */
    private void printNotifyUser() {
        if(!isLoggedIn()) {
            return;
        }
        
        // read notify user object
        NotifyUser notifyUser = readNotifyUser(this.connectedUser.getUsername());

        // create header for the alert
        String header = "Notifications Left To Read: " + notifyUser.getNotificationList().size() + "\n";

        // get the latest notification
        Notification notification = notifyUser.getLatestNotification();
        String message = "";

        // check if there is a notification, if there is then create a message
        if(notification!=null)
            message = notification.createMessage();        

        // append the message to the header, store in message
        message = header + message;

        // update the notify user database to not include the recently viewed notification
        updateNotifyUserInDatabase(notifyUser);
        
        // alert the user
        this.c.returnToGUIMessage(message);
        return;
    }        

    /**
     * initiates the subscription procedure for the connected user
     * @return true if the subscription procedure has been sucessfull and the user is now subscribed to their desired subscription, false otherwise
     */
    private boolean subscribe() {
        if(!isLoggedIn()) {
            return false;
        }
            
        
        System.out.println("Please choose the subscription ID you'd like to subscribe to: ");
        int subID = -1;
        while(subID == -1) {
            try {
                subID = Integer.parseInt(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Wrong subID, only numbers are valid.");    
                System.out.println("Please choose the subscription ID you'd like to subscribe to: ");
            }
        }
        Subscription sub = readSub(subID);
        while(sub == null) {
            System.out.println("[ERROR] Invalid sub ID, please enter a valid sub ID.");
            System.out.println("Please choose the subscription ID you'd like to subscribe to: ");
            subID = -1;
            while(subID == -1) {
                try {
                    subID = Integer.parseInt(scan.nextLine());
                }
                catch(Exception e){
                    System.out.println("Wrong subID, only numbers are valid.");    
                    System.out.println("Please choose the subscription ID you'd like to subscribe to: ");
                }
            }
            sub = readSub(subID);
        }

        this.userSubDetails = new UserSubscriptionDetails(connectedUser, sub);

        writeIntegerToTransCounter(UserSubscriptionDetails.COUNTER);

        String message = "Subscribed successfully, end date of subscription is: " + userSubDetails.getEndDate();
        returnToGUIMessage(message);
        return updateSubDetailsInDatabase();
    }

    /**
     * initiates the adding a content to the library procedure for the connected user
     * @return true if the addition of content to the library has been sucessfull or if the content already exists, false otherwise
     */
    private boolean addContentToLibrary() {
        if(!isLoggedIn()) {
            return false;
        }           

        Content content = readContent(); 
        String message;   
        if(!this.userLibrary.addContent(content))
            message = "Content " + content.getName() + " (ID: " + content.getID() + ") is already in the library.";
        else
            message = "Content " + content.getName() + " (ID: " + content.getID() + ") has been successfully added to the library.";

        returnToGUIMessage(message);    
        return updateLibraryInDatabase();
    }

    /**
     * initializes the databases if necessary
     */
    public void initDatabases() {
        //#region init id files
        // init content last id database
        initDatabaseFromPath(LAST_CONTENT_ID_DATABASE_FILE_PATH, true);

        // init transaction last id database
        initDatabaseFromPath(LAST_TRANSACTION_ID_DATABASE_FILE_PATH, true);        

        // init subscription details last id database
        initDatabaseFromPath(LAST_SUBSCRIPTION_ID_DATABASE_FILE_PATH, true);
        //#endregion

        //#region init main objects database files
        // init contents database
        initDatabaseFromPath(CONTENTS_DATABASE_FILE_PATH, false);        

        // init users database
        initDatabaseFromPath(USERS_DATABASE_FILE_PATH, false);

        // init subs database
        initDatabaseFromPath(SUBS_DATABASE_FILE_PATH, false);
        //#endregion
        
        //#region init helper database files
        // init usernames hashset database, if a new database has been created, insert empty hashset into it
        if(initDatabaseFromPath(USERNAMES_HASHSET_DATABASE_FILE_PATH, true))
            insertObjectIntoDatabase(new HashSet<String>(), USERNAMES_HASHSET_DATABASE_FILE_PATH);        
        //#endregion

        //#region insert hard-coded subs and update database
        // insert hard-coded subs
        insertObjectIntoDatabase(new Subscription((float)0, (float)0), SUBS_DATABASE_FILE_PATH);
        insertObjectIntoDatabase(new Subscription((float)15, (float)1), SUBS_DATABASE_FILE_PATH);
        insertObjectIntoDatabase(new Subscription((float)40, (float)3), SUBS_DATABASE_FILE_PATH);
        insertObjectIntoDatabase(new Subscription((float)70, (float)6), SUBS_DATABASE_FILE_PATH);
        insertObjectIntoDatabase(new Subscription((float)120, (float)12), SUBS_DATABASE_FILE_PATH);

        // update subs counter in the database
        writeIntegerToSubCounter(Subscription.COUNTER);        
        //#endregion

        //#region init connected objects databases
        // init users subs details database
        initDatabaseFromPath(USERS_SUBS_DETAILS_DATABASE_FILE_PATH, false);     

        // init notify user database
        initDatabaseFromPath(NOTIFY_USER_DATABASE_FILE_PATH, false);             

        // init library database
        initDatabaseFromPath(LIBRARIES_DATABASE_FILE_PATH, false);        
        //#endregion
    }

    /**
     * initializes a database, given its path
     * @param isFile represents if the path is a regular file or not
     * @param path represents the path of the database file
     * @return true if a new file has been created, false otherwise
     */
    public boolean initDatabaseFromPath(String path, boolean isFile) {
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
                    return true;             }
                else {
                    // creates the directory in the given path
                    file.mkdirs();
                    return true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * register a new user, ask for details in signup, default the user to free
     * @return the newly created and registered User
     */
    public User register()
    {

        // get username from user
        System.out.println("Please enter your desired username: ");
        String username = scan.nextLine();
        while(username.isBlank() || !username.matches("\\S+")) {
            System.out.println("Invalid username, please try again (no spaces or blank).");
            System.out.println("Please enter your desired username: ");
            username = scan.nextLine();
        }
        while(!this.isUsernameValid(username))
        {
            System.out.println("[ERROR]: Username is taken.");
            System.out.println("Please enter your desired username: ");
            username = scan.nextLine();
            while(username.isBlank() || !username.matches("\\S+")) {
                System.out.println("Invalid username, please try again (no spaces or blank).");
                System.out.println("Please enter your desired username: ");
                username = scan.nextLine();
            }
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
        //scan.close();

        // create user object from the given parameters
        User user = new User(username, password, name, paymentMethod);

        // inserts the user into the user database and inserts the username into the username hashset
        if(!insertAndAddUser(user))
            return null;

        String message = "User " + user.getUsername() + " has been successfully created.";        
        returnToGUIMessage(message);
        // return the newly created user
        return user;
    }

    /**
     * returns to the gui with a message and notifies the user to take further actions in the gui
     * @param message represents the message to be displayed in the gui
     */
    public void returnToGUIMessage(String message) {
        System.out.println(message);
        this.c.returnToGUIMessage(message); 
        returnToGUIMessage();       
    }

    /**
     * notifies the user to take further actions in the gui
     */
    public void returnToGUIMessage() {
        System.out.println("Please return to the GUI for further actions.");
        this.c.returnToGUIMessage();
    }

    /**
     * inities the procedure to create a content
     * @return the newly created content, null otherwise
     */
    public Content createContent() {

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
        float length = -1;
        while(length == -1) {
            try {
                length = Float.parseFloat(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Wrong length, only float numbers are valid.");    
                System.out.println("Please enter your desired length: ");
            }
        }
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
                int season = -1;
                while(season == -1) {
                    try {
                        season = Integer.parseInt(scan.nextLine());
                    }
                    catch(Exception e){
                        System.out.println("Wrong season, only numbers are valid.");    
                        System.out.println("Please enter your desired season: ");
                    }
                }

                // get episode from user
                System.out.println("Please enter your desired episode: ");
                int episode = -1;
                while(episode == -1) {
                    try {
                        episode = Integer.parseInt(scan.nextLine());
                    }
                    catch(Exception e){
                        System.out.println("Wrong episode, only numbers are valid.");    
                        System.out.println("Please enter your desired episode: ");
                    }
                }

                // create TVShow object from the given parameters
                content =  new TVShow(format, subtitlesFileName, name, length, season, episode);
                break;

            default:                
                content =  null;                
                return content;
        }
        //#endregion

        // finished input from user, close input scanner
        //scan.close();

        // inserts the user into the user database and inserts the username into the username hashset
        if(!insertObjectIntoDatabase(content, YYYFlixSystem.CONTENTS_DATABASE_FILE_PATH))
            return null;

        writeIntegerToContentCounter(Content.COUNTER);

        String message = "Content " + content.getName() + " (ID: " + content.getID() + ") has been successfully created.";        
        returnToGUIMessage(message);

        // return the newly created user
        return content;

    }
    
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

        Library library = new Library(user);
        // insert the library object into the database
        if(!this.insertObjectIntoDatabase(library, LIBRARIES_DATABASE_FILE_PATH))
        {
            System.out.println("Register Failed, Please try again.");
            return false;
        }

        UserSubscriptionDetails userSubDetails = new UserSubscriptionDetails(user, readFreeSub());
        // insert the library object into the database
        if(!this.insertObjectIntoDatabase(userSubDetails, USERS_SUBS_DETAILS_DATABASE_FILE_PATH))
        {
            System.out.println("Register Failed, Please try again.");
            return false;
        }
        writeIntegerToTransCounter(Subscription.COUNTER);

        NotifyUser notifyUser = new NotifyUser(user.getUsername());
        // insert the notify user object into the database
        if(!this.insertObjectIntoDatabase(notifyUser, NOTIFY_USER_DATABASE_FILE_PATH))
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

    /**
     * writes the username hashset into the database
     * @param set represents the set to be written into the database
     * @return true if the insertion has been sucessfull, false otherwise
     */
    public boolean writeUsernameHashSet(Set<String> set)
    {
        // delete the previous hashset database
        File file = new File(USERNAMES_HASHSET_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        return insertObjectIntoDatabase(set, USERNAMES_HASHSET_DATABASE_FILE_PATH);
    }

    /**
     * reads the username hashset from the database
     * @return the read username hashset, null if the read has failed
     */
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
     * reads the sub counter from the database
     * @return the read sub counter, null if the read has failed
     */
    public Integer readSubCounter()
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(LAST_SUBSCRIPTION_ID_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            return (Integer) oi.readObject();

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
     * reads the content counter from the database
     * @return the read content counter, null if the read has failed
     */
    public Integer readContentCounter()
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(LAST_CONTENT_ID_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            return (Integer) oi.readObject();

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
     * reads the transaction counter from the database
     * @return the read transaction counter, null if the read has failed
     */
    public Integer readTransactionCounter()
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(LAST_TRANSACTION_ID_DATABASE_FILE_PATH));
            oi = new ObjectInputStream(fi);


            // the set that contains all the usernames inside the database
            return (Integer) oi.readObject();

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
     * writes the content counter to the database
     * @return true if the insertion has been sucessfull, false otherwise
     */
    public boolean writeIntegerToContentCounter(Integer count)
    {
        // read usernames hash set from database
        Integer cnt = this.readContentCounter();
        if(cnt == null)
            count = (Integer) 1;

        // delete the previous hashset database
        File file = new File(LAST_CONTENT_ID_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        return insertObjectIntoDatabase(count, LAST_CONTENT_ID_DATABASE_FILE_PATH);
    }

    /**
     * writes the sub counter to the database
     * @return true if the insertion has been sucessfull, false otherwise
     */    
    public boolean writeIntegerToSubCounter(Integer count)
    {
        // read usernames hash set from database
        Integer cnt = this.readSubCounter();
        if(cnt == null)
            count = (Integer) 1;

        // delete the previous hashset database
        File file = new File(LAST_SUBSCRIPTION_ID_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        return insertObjectIntoDatabase(count, LAST_SUBSCRIPTION_ID_DATABASE_FILE_PATH);
    }    

    /**
     * writes the transaction counter to the database
     * @return true if the insertion has been sucessfull, false otherwise
     */    
    public boolean writeIntegerToTransCounter(Integer count)
    {
        // read usernames hash set from database
        Integer cnt = this.readTransactionCounter();
        if(cnt == null)
            count = (Integer) 1;

        // delete the previous hashset database
        File file = new File(LAST_TRANSACTION_ID_DATABASE_FILE_PATH);
        file.delete();

        // insert the hashset into the hashset database
        return insertObjectIntoDatabase(count, LAST_TRANSACTION_ID_DATABASE_FILE_PATH);
    }


    /**
     * checks if the given username is not used, implemented with checking for key in hash set
     * @param username the given username to check
     * @return true if the username is not in the hash set, therefore no user is registered with that username in the database
     *         false if the username is in the hash set, therefore a user is already registered with that username in the database
     */
    public boolean isUsernameValid(String username) {

        HashSet<String> hashSet = readUsernamesHashSet();
        return !hashSet.contains(username);
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
            // check if the given object is a library
            else if (object instanceof Library) {
                // type-cast the object to library, inorder to get information of username for additional pathing
                Library obj = (Library) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.LIBRARIES_DATABASE_FILE_PATH, String.valueOf(obj.getUsername())
                                                      )
                                          );
            } 
            // check if the given object is a Subscription
            else if (object instanceof Subscription) {
                // type-cast the object to sub, inorder to get information of id for additional pathing
                Subscription obj = (Subscription) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.SUBS_DATABASE_FILE_PATH, String.valueOf(obj.getID())
                                                      )
                                          );
            }             
            else if (object instanceof UserSubscriptionDetails) {
                // type-cast the object to user, inorder to get information of username for additional pathing
                UserSubscriptionDetails obj = (UserSubscriptionDetails) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.USERS_SUBS_DETAILS_DATABASE_FILE_PATH, String.valueOf(obj.getUsername())
                                                      )
                                          );
            }    
            else if (object instanceof NotifyUser) {
                // type-cast the object to notify user, inorder to get information of username for additional pathing
                NotifyUser obj = (NotifyUser) object;

                // open file stream of user's database, sending path of database + additional file pathing
                fos = new FileOutputStream(
                                            objectPath(
                                                            YYYFlixSystem.NOTIFY_USER_DATABASE_FILE_PATH, String.valueOf(obj.getUsername())
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

    /**
     * inities the login procedure, to set a user as the connected user
     * @param username represents the given username of the user
     * @param password represents the given password of the user
     * @return true if the login has been sucessfull (a matching user with the given details has been found in the database), false otherwise
     */
    public boolean login(String username, String password)
    {
        // quick check if the username is even in the database, using the username hashset
        if(this.isUsernameValid(username)){
            System.out.println("[ERROR] Username is not in the database.");
            this.c.sayInvalidUsername();
            return false;
        }

        // a user with the matching username is inside the database
        // now we need to check for matching password

        // read the user with the matching username
        User user = readUser(username);

        if(!user.isPasswordCorrect(password))
        {
            System.out.println("[ERROR]: Login Failed, password is wrong.");
            this.c.sayIncorrectPassword();
            return false;
        }

        System.out.println("Login successful, welcome back " + user.getName() + "!");        
        this.connectedUser = user;
        this.userLibrary = readLibrary(this.connectedUser.getUsername()); 
        this.userSubDetails = readUserSubDetails(this.connectedUser.getUsername());   
        this.c.connectedUser(username);   
        this.c.sayHello();      
        return true;

    }
    
    /**
     * inities the login procedure, logs out the currently conencted user
     * @param user represents the user to logout
     * @return true if the logout has been sucessfull, false otherwise
     */
    public boolean logout(){
        if(isLoggedIn()) {
            this.c.sayBye();              
            this.connectedUser = null;
            this.c.connectedUser("");  
            return true;
        }
        else {
            return false;
        }                    

    }

    // TODO: make readObject and check for instance of
    /**
     * read notify user from the database that matches the username
     * @param username the username of the searched for user's notify user in the database
     * @return if a matching notify user is found, returns the notify user,if not, returns null
     */
    public NotifyUser readNotifyUser(String username)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(objectPath(NOTIFY_USER_DATABASE_FILE_PATH, username)) ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            NotifyUser notifyUser = (NotifyUser) oi.readObject();
            return notifyUser;

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
     * reads the free subscription in the database, which is the first subscription
     * @return the free subscription
     */
    public Subscription readFreeSub() {
        return readSub(1);
    }

    // TODO: make readObject and check for instance of
    /**
     * read sub from the database that matches the sub id
     * @param subID the sub id of the searched for sub in the database
     * @return if a matching sub is found, returns the sub,if not, returns null
     */
    public Subscription readSub(int subID)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(objectPath(SUBS_DATABASE_FILE_PATH, String.valueOf(subID))) ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            Subscription sub = (Subscription) oi.readObject();
            return sub;

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

    // TODO: make readObject and check for instance of
    /**
     * read user from the database that matches the username
     * @param username the username of the searched for user in the database
     * @return if a matching user is found, returns the user,if not, returns null
     */
    public UserSubscriptionDetails readUserSubDetails(String username)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(objectPath(USERS_SUBS_DETAILS_DATABASE_FILE_PATH, username)) ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            UserSubscriptionDetails sub = (UserSubscriptionDetails) oi.readObject();
            return sub;

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


    // TODO: make readObject and check for instance of
    /**
     * read user from the database that matches the username
     * @param username the username of the searched for user in the database
     * @return if a matching user is found, returns the user,if not, returns null
     */
    public Library readLibrary(String username)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            // open file stream of users database
            fi = new FileInputStream(objectPath(LIBRARIES_DATABASE_FILE_PATH, username)) ;

            // open object stream using the file stream
            oi = new ObjectInputStream(fi);

            // read User object from the object stream until a matching user is found
            Library library = (Library) oi.readObject();
            return library;

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
     * asks a user for content id and read content from the database that matches the content id
     * @return if a matching content is found, returns the content,if not, returns null
     */
    public Content readContent() {
        System.out.println("Please enter the wanted ContentID");
        int contentID = -1;
        while(contentID == -1) {
            try {
                contentID = Integer.parseInt(scan.nextLine());
            }
            catch(Exception e){
                System.out.println("Wrong contentID, only numbers are valid.");    
                System.out.println("Please enter the wanted ContentID");
            }
        }
        return readContent(contentID);
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
     * synchornized addition of content to the content list holder
     * @param content represents the given content to be added into the holder
     * @return true if the addition has been sucessfull, false otherwise
     */
    private synchronized boolean addContentToHolder(Content content) {
        return YYYFlixSystem.contentInLibraryHolder.add(content);
    }

    /**
     * read content from the database that matches the content id
     * @param contentID the content id of the searched for content in the database
     * @return if a matching content is found, returns the content,if not, returns null
     */
    public Runnable readContentTask(int contentID)
    {
        return () -> {
            Content content = readContent(contentID);
            addContentToHolder(content);
        };
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

    /**
     * deletes the database file of the given user
     * @param username represents the user we want to delete, username is the identifier
     * @return true only if the user's database file exists and has been deleted successfully, otherwise false
     */
    public boolean deleteNotifyUser(String username)
    {

        // file stream of given user's database file
        File file = new File(objectPath(NOTIFY_USER_DATABASE_FILE_PATH, username));

        // return the result of deleting the user's database file
        return file.delete();
    }      

    /**
     * deletes the database file of the given user
     * @param username represents the user we want to delete, username is the identifier
     * @return true only if the user's database file exists and has been deleted successfully, otherwise false
     */
    public boolean deleteSub(String username)
    {

        // file stream of given user's database file
        File file = new File(objectPath(USERS_SUBS_DETAILS_DATABASE_FILE_PATH, username));

        // return the result of deleting the user's database file
        return file.delete();
    }    
    

    /**
     * deletes the database file of the given user
     * @param username represents the user we want to delete, username is the identifier
     * @return true only if the user's database file exists and has been deleted successfully, otherwise false
     */
    public boolean deleteLibrary(String username)
    {

        // file stream of given user's database file
        File file = new File(objectPath(LIBRARIES_DATABASE_FILE_PATH, username));

        // return the result of deleting the user's database file
        return file.delete();
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
     * @param path represents the path of the object (not including the object itself)
     * @param ending represents last part of the path (the object itself)
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
     * updates an old copy of notify user in the database to be the new given copy
     * @param notifyUser the given notify user instance to be updated in the database
     * @return true if the update has been sucessful (the given notify user has been found in the database and has been removed), false otherwise
     */
    public boolean updateNotifyUserInDatabase(NotifyUser notifyUser) {

        String username = notifyUser.getUsername();
        // deletes the database of the current user, prepares for new database file
        if(!deleteNotifyUser(username))
        {
            System.out.println("User " + username + " has not been found inside the database, can't update the instance.");
            return false;
        }

        // inserts the newly updated user into the database
        if(!this.insertObjectIntoDatabase(notifyUser, NOTIFY_USER_DATABASE_FILE_PATH))
        {
            System.out.println("Failed to insert the updated user into database.");

            // user is now not in the database
            System.out.println("[ERROR] Fatal Error occured in database.");
            return false;
        }

        // user's details has been successfully updated in the database
        System.out.println("Details have been updated successfully.");
        return true;
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

            // user is now not in the database
            System.out.println("[ERROR] Fatal Error occured in database.");
            return false;
        }

        // user's details has been successfully updated in the database
        System.out.println("Details have been updated successfully.");
        return true;
    }

    /**
     * updates an old copy of library in the database to be the new given copy     
     * @return true if the update has been sucessful (the given user has been found in the database and has been removed), false otherwise
     */
    public boolean updateSubDetailsInDatabase() {

        // deletes the database of the current user, prepares for new database file
        if(!deleteSub(this.connectedUser.getUsername()))
        {
            System.out.println("Sub of " + this.connectedUser.getUsername() + " has not been found inside the database, can't update the instance.");
            return false;
        }

        // inserts the newly updated library into the database
        if(!this.insertObjectIntoDatabase(this.userSubDetails, USERS_SUBS_DETAILS_DATABASE_FILE_PATH))
        {
            System.out.println("Failed to insert the updated sub into database.");
            
            // user is now not in the database
            System.out.println("[ERROR] Fatal Error occured in database.");            
            return false;
        }

        // library's details has been successfully updated in the database
        System.out.println("Details have been updated successfully.");
        return true;
    }     

    /**
     * updates an old copy of library in the database to be the new given copy     
     * @return true if the update has been sucessful (the given user has been found in the database and has been removed), false otherwise
     */
    public boolean updateLibraryInDatabase() {

        // deletes the database of the current user, prepares for new database file
        if(!deleteLibrary(this.connectedUser.getUsername()))
        {
            System.out.println("Library of " + this.connectedUser.getUsername() + " has not been found inside the database, can't update the instance.");
            return false;
        }

        // inserts the newly updated library into the database
        if(!this.insertObjectIntoDatabase(this.userLibrary, LIBRARIES_DATABASE_FILE_PATH))
        {
            System.out.println("Failed to insert the updated library into database.");
            
            // user is now not in the database
            System.out.println("[ERROR] Fatal Error occured in database.");            
            return false;
        }

        // library's details has been successfully updated in the database
        System.out.println("Details have been updated successfully.");
        return true;
    }
}
