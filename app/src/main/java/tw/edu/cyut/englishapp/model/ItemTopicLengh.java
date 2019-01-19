package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

public class ItemTopicLengh {
    @SerializedName("COUNT(*)")
    private String num;

    public ItemTopicLengh(String num) {
        this.num = num;

    }

    public String getCount() {
        return num;
    }
}