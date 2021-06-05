package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects;

/**
 * Created by David on 9/14/2019 for Nexmii.
 */
public class Quote {

    private String quote;
    private String category;
    private String liked;
    private int itemId;

    public Quote() {

    }

    public String getQuote() {
        return quote;
    }

    public String getCategory() {
        return category;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }
}
