import java.util.ArrayList;

public class Library {
    // fields
    private User user;
    private ArrayList<Content> contentList;
    public int size;

    // public constructor
    public Library(User user)
    {
        this.user = user;
        this.contentList = new ArrayList<Content>();
        this.size = 0;
    }

    // get function for user field
    public User getUser(){
        return user;
    }

    // adds a content to the user's library
    public boolean addContent(Content content){
        if (contentList.contains(content)){
            return false;
        }
        else {
            contentList.add(content);
            this.size++;
        }
        return true;
    }

    // deletes a content from the user's library
    public boolean deleteContent(Content content){
        if (!contentList.contains(content)){
            return false;
        }
        else {
            contentList.remove(content);
            this.size--;
        }
        return true;
    }

    // searches a content in the user's library, via the received content's id
    public Content searchContent(int ID){
        for (Content content:contentList
             ) {
            if(content.getID()==ID){
                return content;
            }

        }
        return null;
    }

    // toString method of the object
    @Override
    public String toString() {
        return "Library{" +
                "user=" + user +
                ", contentList=" + contentList +
                ", size=" + size +
                '}';
    }
}

