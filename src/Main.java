import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        //testUserLibrary();
        //testUserSub();
        //testUserRegister();
        //testSubCreate();
        //testLogin();
        //testDatabaseBug();
        //testPrintDatabases();
        testUserChangeDetails();
        //testDeleteFile();
    }

    public static void testUserLibrary()
    {
        System.out.println("Hello World");

        Content commercial1 = new Commercial("avi", "Commercial1.src", "Commercial1", (float)101.5, "Yossef Ishayev");
        Content commercial2 = new Commercial("avi", "Commercial2.src", "Commercial1", (float)111.5, "Yossef Ishayev");

        Content movie1 = new Movie("mp4", "Movie1.src", "Movie1", (float)121.5, "Yossef Ishayev");
        Content movie2 = new Movie("mp4", "Movie2.src", "Movie2", (float)130.5, "Yossef Ishayev");

        Content tvshow1 = new TVShow("mp4", "TVShow1.src", "TVShow1", (float)121.5, 1, 1);
        Content tvshow2 = new TVShow("mp4", "TVShow2.src", "TVShow2", (float)121.5, 1, 2);

        User user1 = new User("yakir", "123456", "Yakir Hasid", "VISA");
        User user2 = new User("yuval", "1234567", "Yuval Yacobi", "VISA");

        Library library1 = new Library(user1);

        library1.addContent(movie1);
        library1.addContent(movie2);
        library1.addContent(tvshow1);
        System.out.println(library1);
        library1.deleteContent(tvshow1);
        System.out.println(library1);
        library1.deleteContent(tvshow1);
        System.out.println(library1);
        
        Library library2 = new Library(user2);
    }

    public static void testUserSub()
    {
        System.out.println("Hello World");
        Subscription sub1=new Subscription((float)150,(float)12);
        Subscription sub2=new Subscription((float)100,(float)9);
        Subscription sub3=new Subscription((float)80,(float)6);

        User uv= new User("uv","1234","yuval yacobi","visa");
        User yakir= new User("yakir","1235","yakir hasid","paypal");
        UserSubscriptionDetails uv_sub= new UserSubscriptionDetails(uv,sub1);
        UserSubscriptionDetails yakir_sub= new UserSubscriptionDetails(yakir,sub3);
    }

    public static void testUserRegister()
    {
        YYYFlixSystem SYS= new YYYFlixSystem();
        SYS.register();
    }

    public static void testSubCreate()
    {
        Subscription sub1 = new Subscription((float)0, (float)0);
        Subscription sub2 = new Subscription((float)15, (float)1);
        Subscription sub3 = new Subscription((float)40, (float)3);
        Subscription sub4 = new Subscription((float)70, (float)6);
        Subscription sub5 = new Subscription((float)120, (float)12);
    }

    public static void testLogin()
    {
        // scanner object
        Scanner scan = new Scanner(System.in);

        // get username from input
        System.out.println("Please enter the username for login");
        String username = scan.nextLine();

        System.out.println("Please enter the password for login");
        String password = scan.nextLine();

        YYYFlixSystem SYS= new YYYFlixSystem();
        SYS.login(username, password);
    }

    private static void testDatabaseBug()
    {
        User uv= new User("uv","1234","yuval yacobi","visa");
        User yakir= new User("yakir","1235","yakir hasid","paypal");        

        YYYFlixSystem SYS= new YYYFlixSystem();

        // insert the user object into the database
        if(!SYS.insertObjectIntoDatabase(uv, "usersDatabase.dat"))
            System.out.println("Register Failed, Please try again.");
        
        if(!SYS.insertObjectIntoDatabase(yakir, "usersDatabase.dat"))
            System.out.println("Register Failed, Please try again.");            
    }

    private static void testPrintDatabases() {

        YYYFlixSystem SYS = new YYYFlixSystem();
        testPrintDatabases(SYS);
    }

    private static void testPrintDatabases(YYYFlixSystem SYS) {

        System.out.println("Users Database:");
        SYS.printUsers();
        
        System.out.println("Usernames HashSet Database");
        SYS.printUsernamesHashset();
    }

    private static void testUserChangeDetails() {
        YYYFlixSystem SYS= new YYYFlixSystem();
        
        User user1 = new User("testUpdateUser2", "123456", "Yakir Hasid", "VISA");
        testPrintDatabases(SYS);

        SYS.insertAndAddUser(user1);
        testPrintDatabases(SYS);

        SYS.changeName(user1, "New Name");
        testPrintDatabases(SYS);

        SYS.changePassword(user1, "12345", "New Password");
        testPrintDatabases(SYS);

        SYS.changePassword(user1, "123456", "New Password");
        testPrintDatabases(SYS);

        SYS.changePaymentMethod(user1, "New Payment Method");
        testPrintDatabases(SYS);

    }

    public static void testDeleteFile()
    {
        YYYFlixSystem SYS= new YYYFlixSystem();  
        User user1 = new User("testUpdateUser2", "123456", "Yakir Hasid", "VISA"); 
        try {
            Files.delete(Path.of(SYS.userPath(user1.getUsername())));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
