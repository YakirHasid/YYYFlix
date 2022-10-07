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
    public Content searchContent(int ID){
        for (Content content:contentList
             ) {
            if(content.getID()==ID){
                return content;
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return "Library{" +
                "user=" + user +
                ", contentList=" + contentList +
                ", size=" + size +
                '}';
    }
}

