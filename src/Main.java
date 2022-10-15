import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        YYYFlixSystem system=new YYYFlixSystem();
        offmenu();
        Scanner scan=new Scanner(System.in);
        int option =Integer.parseInt(scan.nextLine());
        while(option!=10){
            System.out.println("in while operation");
            switch (option){
                case 1: register(system);
                    break;
                case 2:login(system);
                    break;
                case 3:if(system.connectedUser!=null){logout(system);}
                    break;
                case 4: addcontent(system);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 10:
                    break;
                default:System.out.println("wrong number try again");
                    break;
            }
            if(system.connectedUser==null)
                offmenu();
            else
                logmenu();
            option =Integer.parseInt(scan.nextLine());
        }
        System.out.println("good bye");
    }
    public static void offmenu(){
        System.out.println("choose your operation");
        System.out.println("1.register");
        System.out.println("2.login");
        System.out.println("10.exit\n");
    }
    public static void logmenu(){
        System.out.println("choose your operation");
        System.out.println("3.logout");
        System.out.println("4.add content");
        System.out.println("10.exit\n");
    }
    public static void register(YYYFlixSystem sys){
        sys.register();
    }
    public static void login(YYYFlixSystem sys) throws InterruptedException {
        sys.setVisible(true);
        System.out.println("waiting");
        while(sys.waiting==false){System.out.println("enter username and password and than press on submit.");
            Thread.sleep(5000);}
    }
    public static void logout(YYYFlixSystem sys){sys.logout();}

    public static void addcontent(YYYFlixSystem sys){sys.createContent();}





}
