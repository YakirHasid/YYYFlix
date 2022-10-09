public class Main {
    public static void main(String[] args)
    {
        //testUserLibrary();
        //testUserSub();
        //testUserRegister();
        testSubCreate();
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
}
