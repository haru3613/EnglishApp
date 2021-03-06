package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemTest {
    @SerializedName("tid")
    private String tid;
    @SerializedName("qorder")
    private String qorder;
    @SerializedName("correct_ans")
    private String correct_ans;
    @SerializedName("uid")
    private String uid;
    @SerializedName("ans")
    private String ans;
    @SerializedName("created_at")
    private String created_at;

    public ItemTest(String tid, String uid , String correct_ans, String qorder, String ans, String created_at){
        this.tid=tid;
        this.uid=uid;
        this.qorder=qorder;
        this.ans=ans;
        this.created_at=created_at;
        this.correct_ans=correct_ans;
    }

    public String getTid() {
        return tid;
    }

    public String getQorder() {
        return qorder;
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

}
