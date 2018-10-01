package tw.edu.cyut.englishapp;

import com.google.gson.annotations.SerializedName;

//personal_exam_sort
public class ItemSort {
    @SerializedName("pesid")
    private String pesid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("step")
    private String step;
    @SerializedName("qbank")
    private String qbank;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public ItemSort(String pesid,String uid ,String step,String created_at,String updated_at,String qbank){
        this.pesid=pesid;
        this.uid=uid;
        this.qbank=qbank;
        this.step=step;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getPesid() {
        return pesid;
    }

    public String getUid() {
        return uid;
    }

    public String getStep() {
        return step;
    }

    public String getQbank() {
        return qbank;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
