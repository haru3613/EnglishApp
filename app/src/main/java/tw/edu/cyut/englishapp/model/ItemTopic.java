package tw.edu.cyut.englishapp.model;

import com.google.gson.annotations.SerializedName;

public class ItemTopic {
    @SerializedName("tid")
    private String tid;
    @SerializedName("pinyin")
    private String pinyin;
    @SerializedName("phonetic")
    private String phonetic;
    @SerializedName("name")
    private String name;
    @SerializedName("mime")
    private String mime;
    @SerializedName("audio")
    private String audio;
    @SerializedName("ans")
    private String ans;
    @SerializedName("qbank")
    private String qbank;
    @SerializedName("qorder")
    private String qorder;
    @SerializedName("qenable")
    private String qenable;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public ItemTopic(String tid,String pinyin ,String phonetic,String name,String mime,String audio,String ans,String qbank,String qorder,String qenable,String created_at,String updated_at){
        this.tid=tid;
        this.pinyin=pinyin;
        this.phonetic=phonetic;
        this.name=name;
        this.mime=mime;
        this.audio=audio;
        this.ans=ans;
        this.qbank=qbank;
        this.qorder=qorder;
        this.qenable=qenable;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public String getTid() {
        return tid;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getName() {
        return name;
    }

    public String getMime() {
        return mime;
    }

    public String getAudio() {
        return audio;
    }

    public String getAns() {
        return ans;
    }

    public String getQbank() {
        return qbank;
    }

    public String getQorder() {
        return qorder;
    }

    public String getQenable() {
        return qenable;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
