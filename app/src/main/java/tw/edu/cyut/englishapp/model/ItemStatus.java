package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//exam_status
public class ItemStatus {
    @SerializedName("esid")
    private String esid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("eid")
    private String eid;
    @SerializedName("status")
    private String status;
    @SerializedName("step")
    private String step;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;


    public ItemStatus(String esid,String uid ,String eid,String status,String created_at,String updated_at,String step){
        this.esid=esid;
        this.uid=uid;
        this.eid=eid;
        this.status=status;
        this.step=step;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getEsid() {
        return esid;
    }

    public String getUid() {
        return uid;
    }

    public String getEid() {
        return eid;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getStep(){
        return step;
    }
}
