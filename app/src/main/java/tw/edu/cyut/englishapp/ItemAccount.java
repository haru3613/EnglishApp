package tw.edu.cyut.englishapp;

import com.google.gson.annotations.SerializedName;

//user
public class ItemAccount {
    @SerializedName("uid")
    private String uid;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("names")
    private String names;
    @SerializedName("email")
    private String email;
    @SerializedName("sex")
    private String sex;
    @SerializedName("country")
    private String country;
    @SerializedName("education")
    private String education;
    @SerializedName("language")
    private String language;
    @SerializedName("o_language")
    private String o_language;
    @SerializedName("y_birthday")
    private String y_birthday;
    @SerializedName("m_birthday")
    private String m_birthday;
    @SerializedName("d_birthday")
    private String d_birthday;
    @SerializedName("y_leraning")
    private String y_leraning;
    @SerializedName("method_leraning")
    private String method_leraning;
    @SerializedName("backgound")
    private String backgound;
    @SerializedName("certification")
    private String certification;
    @SerializedName("name_certification")
    private String name_certification;
    @SerializedName("level_certification")
    private String level_certification;
    @SerializedName("level")
    private String level;
    @SerializedName("enable")
    private String enable;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;





    public ItemAccount(String uid, String username,String password,String names,String email,String sex,String country,String education,String language,String o_language,String y_birthday,String m_birthday,String d_birthday,String y_leraning,String method_leraning,String backgound,
                       String certification,String name_certification,String level_certification,String level,String enable,String created_at,String updated_at) {
        this.uid = uid;
        this.username = username;
        this.password=password;
        this.names=names;
        this.email=email;
        this.sex=sex;
        this.country=country;
        this.education=education;
        this.language=language;
        this.o_language=o_language;
        this.y_birthday=y_birthday;
        this.m_birthday=m_birthday;
        this.d_birthday=d_birthday;
        this.y_leraning=y_leraning;
        this.method_leraning=method_leraning;
        this.backgound=backgound;
        this.certification=certification;
        this.name_certification=name_certification;
        this.level_certification=level_certification;
        this.level=level;
        this.enable=enable;
        this.created_at=created_at;
        this.updated_at=updated_at;

    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNames() {
        return names;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public String getEducation() {
        return education;
    }

    public String getLanguage() {
        return language;
    }

    public String getO_language() {
        return o_language;
    }

    public String getY_birthday() {
        return y_birthday;
    }

    public String getM_birthday() {
        return m_birthday;
    }

    public String getD_birthday() {
        return d_birthday;
    }

    public String getY_leraning() {
        return y_leraning;
    }

    public String getMethod_leraning() {
        return method_leraning;
    }

    public String getBackgound() {
        return backgound;
    }

    public String getCertification() {
        return certification;
    }

    public String getName_certification() {
        return name_certification;
    }

    public String getLevel_certification() {
        return level_certification;
    }

    public String getLevel() {
        return level;
    }

    public String getEnable() {
        return enable;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
