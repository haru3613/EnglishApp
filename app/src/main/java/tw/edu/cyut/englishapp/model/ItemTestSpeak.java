package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemTestSpeak {
    @SerializedName("aid")
    private String aid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("filename")
    private String filename;
    @SerializedName("reviews")
    private String reviews;
    @SerializedName("created_at")
    private String created_at;

    public ItemTestSpeak(String aid, String uid , String filename,String reviews, String created_at){
        this.uid=uid;
        this.aid=aid;
        this.reviews=reviews;
        this.created_at=created_at;
        this.filename=filename;
    }


    public String getUid() {
        return uid;
    }


    public String getCreated_at() {
        return created_at;
    }

    public String getAid() {
        return aid;
    }


    public String getFilename() {
        return filename;
    }

    public String getReviews() {
        return reviews;
    }
}
