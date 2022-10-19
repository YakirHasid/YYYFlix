import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    // fields
    private String username;
    private ArrayList<Integer> contentIDList;

    /**
     * Content's public constructor
     * @param user represents the user which the current library is attached to
     */
    public Library(User user)
    {
        this.username = user.getUsername();
        this.contentIDList = new ArrayList<Integer>();
    }

    // user getter
    public String getUsername(){
        return username;
    }

    // content list getter
    public ArrayList<Integer> getContentIDList() {
        return contentIDList;
    }
    
    /**
     * adds a content to the user's library
     * @param content represents the content that is being requested to be added into the library
     * @return false if the content is already in the library, true if the content has been added successfully (no validation)
     */
    public boolean addContent(Content content){
        if(content == null)
            return false;
            
        // checks if the content is already inside the library
        if (contentIDList.contains(content.getID()))
            return false;

        // adds the content to the library
        contentIDList.add(content.getID());

        return true;
    }

    /**
     * deletes a content from the user's library
     * @param content represents the content that is being requested to be removed from the library
     * @return false if the content is not in the library, true if the content has been added successfully (no validation)
     */
    public boolean deleteContent(Content content){
        // checks if the content is not inside the library
        if (!contentIDList.contains(content.getID()))
            return false;

        // removes the content to the library
        contentIDList.remove(content.getID());

        return true;
    }
    
    /**
     * searches a content in the user's library, via the received content's id
     * @param ID represents the ID of the content that is being requested to be searched for
     * @return the contentID that matches the given ID, returns null if no match is found
     */
    public Integer searchContentID(int ID){
        // foreach loop the content list
        for (Integer contentID:contentIDList)
            // check for a match with the given id, as the object's id field is the identifier
            if(contentID==ID)
                return contentID;

        // no match has been found in the content list
        return null;
    }

    public String libraryHeader(String name) {
        // title for display
        String displayText =  name + "'s (" + username + ") Library:" + "\n" + "Content List:\n";

        return displayText;        
    }

    /**
     * toString method of the Library
     * @return a string that represents the Library
     */
    @Override
    public String toString() {
        // title for display
        String displayText =  username + "'s" + " Library:" + "\n" + "ContentID List:\n";

        // foreach content, add to display
        for (Integer contentID :
                contentIDList) {
            displayText += contentID + "\n";
        }

        // total size of library
        displayText += "Total Library Size = " + this.contentIDList.size() + "\n";

        return displayText;
    }
}

