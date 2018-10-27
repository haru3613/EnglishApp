package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

//Topic_speak
public class ItemTopicSpeak {
    @SerializedName("tid")
    private String tid;
    @SerializedName("uid")
    private String uid;
    @SerializedName("topic_day")
    private String topic_day;
    @SerializedName("topic_index")
    private String topic_index;
    @SerializedName("title")
    private String title;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("test_speak_filename")
    private String test_speak_filename;
    @SerializedName("test_speak_aid")
    private String test_speak_aid;

    public ItemTopicSpeak(String tid, String uid , String topic_day, String topic_index, String title, String created_at, String updated_at){
        this.tid=tid;
        this.uid=uid;
        this.topic_day=topic_day;
        this.topic_index=topic_index;
        this.title=title;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getTid() {
        return tid;
    }


    public String getUid() {
        return uid;
    }


    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getTopic_day() {
        return topic_day;
    }

    public String getTopic_index() {
        return topic_index;
    }
    public String gettest_speak_filename() {
        return test_speak_filename;
    }
    public String gettest_speak_aid() {
        return test_speak_aid;
    }
}
