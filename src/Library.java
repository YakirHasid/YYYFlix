import java.util.ArrayList;

public class Library {
    // fields
    private User user;
    private ArrayList<Content> contentList;
    public int size;

    /**
     * Content's public constructor
     * @param user represents the user which the current library is attached to
     */
    public Library(User user)
    {
        this.user = user;
        this.contentList = new ArrayList<Content>();
        this.size = 0;
    }

    // user getter
    public User getUser(){
        return user;
    }

    // content list getter
    public ArrayList<Content> getContentList() {
        return contentList;
    }

    /**
     * adds a content to the user's library
     * @param content represents the content that is being requested to be added into the library
     * @return false if the content is already in the library, true if the content has been added successfully (no validation)
     */
    public boolean addContent(Content content){
        // checks if the content is already inside the library
        if (contentList.contains(content))
            return false;

        // adds the content to the library
        contentList.add(content);

        // increases the size of the library
        this.size++;
        return true;
    }

    /**
     * deletes a content from the user's library
     * @param content represents the content that is being requested to be removed from the library
     * @return false if the content is not in the library, true if the content has been added successfully (no validation)
     */
    public boolean deleteContent(Content content){
        // checks if the content is not inside the library
        if (!contentList.contains(content))
            return false;

        // removes the content to the library
        contentList.remove(content);

        // decreases the size of the library
        this.size--;
        return true;
    }

    /**
     * searches a content in the user's library, via the received content's id
     * @param ID represents the ID of the content that is being requested to be searched for
     * @return the Content object that matches the given ID, returns null if no match is found
     */
    public Content searchContent(int ID){
        // foreach loop the content list
        for (Content content:contentList)
            // check for a match with the given id, as the object's id field is the identifier
            if(content.getID()==ID)
                return content;

        // no match has been found in the content list
        return null;
    }

    /**
     * toString method of the Library
     * @return a string that represents the Library
     */
    @Override
    public String toString() {
        return "Library{" +
                "user=" + user +
                ", contentList=" + contentList +
                ", size=" + size +
                '}';
    }
}

