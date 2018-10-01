package tw.edu.cyut.englishapp;

import com.google.gson.annotations.SerializedName;

//exam
public class Item {

    @SerializedName("eid")
    private String eid;
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("enable")
    private String enable;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("qbank")
    private String qbank;




    public Item(String eid, String type, String title,String content,String enable,String created_at,String qbank,String updated_at) {
        this.eid = eid;
        this.type = type;
        this.title=title;
        this.content=content;
        this.enable=enable;
        this.created_at=created_at;
        this.updated_at=updated_at;
        this.qbank=qbank;
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
    }
    public String getEnable(){
        return enable;
    }
    public String getCreated_at(){
        return created_at;
    }
    public String getUpdated_at(){
        return updated_at;
    }
    public String getQbank(){
        return qbank;
    }
}