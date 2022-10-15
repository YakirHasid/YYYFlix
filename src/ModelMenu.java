public class ModelMenu {
    private String connectedUser;
    private String username;
    private String password;
    public ModelMenu(String connectedUser, String username, String password) {
        this.connectedUser = connectedUser;
        this.username = username;
        this.password = password;
    }
    public String getConnectedUser() {
        return connectedUser;
    }
    public void setConnectedUser(String connectedUser) {
        this.connectedUser = connectedUser;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
   }