package tw.edu.cyut.englishapp;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemSpeak {
    @SerializedName("tid")
    private String tid;
    @SerializedName("eid")
    private String eid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("topic_num")
    private String topic_num;
    @SerializedName("title")
    private String title;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public ItemSpeak(String tid,String uid ,String eid,String topic_num,String title,String created_at,String updated_at){
        this.tid=tid;
        this.uid=uid;
        this.eid=eid;
        this.topic_num=topic_num;
        this.title=title;
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

    public String getTopic_num() {
        return topic_num;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
