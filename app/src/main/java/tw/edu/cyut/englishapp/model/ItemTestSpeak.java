package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemTestSpeak {
    @SerializedName("aid")
    private String aid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("eid")
    private String eid;
    @SerializedName("tid")
    private String tid;
    @SerializedName("mode")
    private String mode;
    @SerializedName("name")
    private String name;
    @SerializedName("mime")
    private String mime;
    @SerializedName("audio")
    private String audio;
    @SerializedName("reviews")
    private String reviews;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public ItemTestSpeak(String aid,String tid, String uid , String eid, String mode, String name,String mime,String audio,String reviews, String created_at, String updated_at){
        this.tid=tid;
        this.uid=uid;
        this.eid=eid;
        this.aid=aid;
        this.mode=mode;
        this.name=name;
        this.mime=mime;
        this.audio=audio;
        this.reviews=reviews;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getTid() {
        return tid;
    }


    public String getEid() {
        return eid;
    }

    public String getUid() {
        return uid;
    }


    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getAid() {
        return aid;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getMime() {
        return mime;
    }

    public String getAudio() {
        return audio;
    }

    public String getReviews() {
        return reviews;
    }
}
