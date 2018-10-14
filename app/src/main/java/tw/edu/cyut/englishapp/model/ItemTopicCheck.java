package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

public class ItemTopicCheck {
    @SerializedName("tc_id")
    private String tc_id;
    @SerializedName("correct_ans")
    private String correct_ans;
    @SerializedName("uid")
    private String uid;
    @SerializedName("filename")
    private String filename;
    @SerializedName("ans")
    private String ans;
    @SerializedName("created_at")
    private String created_at;

    public ItemTopicCheck(String tc_id, String uid , String correct_ans, String ans, String created_at,String filename){
        this.tc_id=tc_id;
        this.uid=uid;
        this.ans=ans;
        this.created_at=created_at;
        this.correct_ans=correct_ans;
        this.filename=filename;
    }

    public String getTc_id() {
        return tc_id;
    }

    public String getCorrect_ans() {
        return correct_ans;
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

    public String getFilename() {
        return filename;
    }
}
