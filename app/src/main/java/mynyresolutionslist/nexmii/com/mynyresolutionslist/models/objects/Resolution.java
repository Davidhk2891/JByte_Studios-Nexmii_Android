package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects;


/**
 * Created by David on 12/23/2016.
 */

public class Resolution {

    private String title;
    private String content;
    private String recordDate;
    private String emojiAddress;
    private String emojiRevived;
    private int itemId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getEmojiAddress() {
        return emojiAddress;
    }

    public void setEmojiAddress(String emojiAddress) {
        this.emojiAddress = emojiAddress;
    }

    public String getEmojiRevived(){
        return emojiRevived;
    }

    public void setEmojiRevived(String emojiRevived){
        this.emojiRevived = emojiRevived;
    }
}
