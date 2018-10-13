package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//personal_exam_sort
public class ItemSort {
    @SerializedName("pesid")
    private String pesid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("qbank")
    private String qbank;
    @SerializedName("created_at")
    private String created_at;

    public ItemSort(String pesid,String uid ,String created_at,String qbank){
        this.pesid=pesid;
        this.uid=uid;
        this.qbank=qbank;
        this.created_at=created_at;
    }


    public String getUid() {
        return uid;
    }


    public String getQbank() {
        return qbank;
    }

    public String getCreated_at() {
        return created_at;
    }

}
