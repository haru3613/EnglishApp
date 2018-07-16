package tw.edu.cyut.englishapp;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("eid")
    private String eid;
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("uid")
    private String uid;
    @SerializedName("topic_count")
    private String topic_count;
    @SerializedName("topic")
    private String topic;
    @SerializedName("enable")
    private String enable;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("until_at")
    private String until_at;




    public Item(String eid, String type, String title,String content,String topic_count,String topic,String enable,String created_at, String uid,String until_at,String updated_at) {
        this.eid = eid;
        this.type = type;
        this.title=title;
        this.content=content;
        this.topic_count=topic_count;
        this.topic=topic;
        this.enable=enable;
        this.created_at=created_at;
        this.until_at=until_at;
        this.updated_at=updated_at;
        this.uid=uid;
    }

    public String getEid(){
        return eid;
    }
    public String getType(){
        return type;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }public String getTopic_count(){
        return topic_count;
    }
    public String getTopic(){
        return topic;
    }
    public String getEnable(){
        return enable;
    }
    public String getCreated_at(){
        return created_at;
    }
    public String getUntil_at(){
        return until_at;
    }
    public String getUpdated_at(){
        return updated_at;
    }

    public String getUid() {
        return uid;
    }

}