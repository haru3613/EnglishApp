package tw.edu.cyut.englishapp.model;
import com.google.gson.annotations.SerializedName;

public class ItemCheckTopic {
    @SerializedName("aid")
    private String aid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("day")
    private String day;
    @SerializedName("filename")
    private String filename;
    @SerializedName("correct_ans")
    private String correct_ans;
    @SerializedName("created_at")
    private String created_at;

    public ItemCheckTopic(String aid, String uid , String day, String filename, String correct_ans, String created_at){
        this.aid=aid;
        this.uid=uid;
        this.day=day;
        this.filename=filename;
        this.correct_ans=correct_ans;
        this.created_at=created_at;
    }

    public String getAid() {
        return aid;
    }


    public String getUid() {
        return uid;
    }


    public String getDay() {
        return day;
    }

    public String getFilename() {
        return filename;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public String getCreated_at() {
        return created_at;
    }


}