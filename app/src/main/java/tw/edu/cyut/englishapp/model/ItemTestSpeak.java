package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemTestSpeak {
    @SerializedName("tid")
    private String tid;
    @SerializedName("qorder")
    private String qorder;
    @SerializedName("eid")
    private String eid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("ans")
    private String ans;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public ItemTestSpeak(String tid, String uid , String eid, String qorder, String ans, String created_at, String updated_at){
        this.tid=tid;
        this.uid=uid;
        this.eid=eid;
        this.qorder=qorder;
        this.ans=ans;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getTid() {
        return tid;
    }

    public String getQorder() {
        return qorder;
    }

    public String getEid() {
        return eid;
    }

    public String getUid() {
        return uid;
    }

    public String getAns() {
        return ans;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
