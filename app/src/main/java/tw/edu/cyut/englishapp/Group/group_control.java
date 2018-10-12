package tw.edu.cyut.englishapp.Group;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.cyut.englishapp.Backgorundwork;
import tw.edu.cyut.englishapp.LoginActivity;
import tw.edu.cyut.englishapp.PreTestActivity;
import tw.edu.cyut.englishapp.R;
import tw.edu.cyut.englishapp.model.ItemTopicSpeak;

import static tw.edu.cyut.englishapp.LoginActivity.KEY;

public class group_control extends AppCompatActivity  {
    private ImageView bt_topic_speak,image_background,bt_next,bt_speak_start,bt_stop_speak,bt_speak_talker;
    private TextView text_count;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private Integer count_topic=0,count_record=0;
    private String day,uid,level,index,username,fname;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private boolean playPause;
    private String[][] day_array= {{"103", "zhuang2JS", "qiu3JH", "er3CL", "hou2JY", "ai2JS", "san3JH", "li3CL", "sha3JY", "ci2JS", "zen2JH", "guan3CL", "zi2JY", "yv2JS", "fou2JH", "mei3CL", "nuan3JY", "jv3JS", "shei3JH", "pei2CL", "seng2JY", "ce2JS", "pan2JH", "bei3CL", "ben3JY", "nuan2JS", "lai3JH", "suo2CL", "he3JY", "wen2JS", "duan2JH", "mo3CL", "chi2JY", "kou3JS", "qiao3JH", "deng4CL", "tie1JY", "ming4JS", "zhun1JH", "suan4CL", "yue1JY", "zun4JS", "hun4JH", "hei1CL", "cai1JY", "zui4JS", "ruo1JH", "yvn4CL", "qia1JY", "xing4JS", "kong1JH", "zhui1CL", "shai4JY", "fei1JS", "ying1JH", "ne4CL", "guang1JY", "zei4JS", "feng4JH", "hui4CL", "rou4JY", "chong1JS", "bin1JH", "geng1CL", "yong4JY", "zha1JS", "lao4JH", "ding4CL", "tuan1JY", "xin4JS", "ca2JH", "zheng4CL", "mi1JY", "zan2JS", "yuan3JH", "nue3CL", "teng2JY", "bian1JS", "liao2JH", "zan1CL", "fen4JY", "dong3JS", "cha2JH", "cou4CL", "a1JY", "zhun3JS", "miao1JH", "yo3CL", "hun2JY", "bi4JS", "ne2JH", "zu1CL", "gan4JY", "tiao2JS", "xia4JH", "shun3CL", "te4JY", "hua1JS", "eng1JH", "duo2CL", "suo3JY", "ri1JS", "cuo3JH", "zhuo2CL"},
        {"103", "heng3JS", "rang2JH", "zi3CL", "liang3JY", "tao2JS", "run2JH", "cao3CL", "chu3JY", "yi3JS", "pa2JH", "bie3CL", "di2JY", "ming3JS", "wai3JH", "sou2CL", "kan2JY", "nian3JS", "ou3JH", "fo3CL", "shua2JY", "qiao2JS", "juan2JH", "yan3CL", "pin2JY", "zhua2JS", "dong2JH", "fou3CL", "nang3JY", "diu2JS", "ge3JH", "keng2CL", "mu3JY", "zhui2JS", "guo2JH", "pin1CL", "xian1JY", "seng1JS", "cui4JH", "za1CL", "dian4JY", "yan4JS", "long1JH", "dan1CL", "hua4JY", "qie1JS", "nao1JH", "eng4CL", "huang4JY", "zhen4JS", "kuo4JH", "wu4CL", "ren1JY", "shuo1JS", "qiong4JH", "qie4CL", "cun1JY", "pu1JS", "yo4JH", "dei4CL", "wai1JY", "gong4JS", "bei1JH", "can1CL", "pie4JY", "reng4JS", "ca1JH", "lun4CL", "dao4JY", "jie3JS", "run1JH", "xv4CL", "chen1JY", "e4JS", "zhuan2JH", "xian3CL", "mie2JY", "ang1JS", "ceng4JH", "tai3CL", "luan2JY", "cen1JS", "men2JH", "dou3CL", "zhua1JY", "ti1JS", "qv4JH", "shuan2CL", "ya4JY", "rui2JS", "feng1JH", "kan4CL", "po2JY", "xie3JS", "qian4JH", "cuan2CL", "tan3JY", "nuo3JS", "po4JH", "zei1CL", "hou1JY", "ya2JS", "tuan3JH", "kuai1CL"},
        {"103", "shuang3JS", "you3JH", "sang2CL", "wu3JY", "you2JS", "ken3JH", "qing2CL", "tao3JY", "xiang3JS", "yuan2JH", "jin3CL", "zuo2JY", "bi3JS", "shai2JH", "cang2CL", "miao3JY", "pi3JS", "sai2JH", "geng3CL", "pou2JY", "tai2JS", "ri3JH", "cui2CL", "kun2JY", "mou3JS", "hen3JH", "yao2CL", "chuai2JY", "ting3JS", "kuai3JH", "ben2CL", "nai2JY", "tong2JS", "zuo3JH", "bai1CL", "guan1JY", "kou4JS", "wan1JH", "shua4CL", "qiu4JY", "lang1JS", "le4JH", "jiang4CL", "ben1JY", "hei4JS", "tang1JH", "re1CL", "bang4JY", "qin1JS", "chai4JH", "ai1CL", "niu1JY", "fu4JS", "jing4JH", "xia1CL", "cen4JY", "kui4JS", "bu1JH", "pian1CL", "zhei4JY", "guan4JS", "wo4JH", "chang4CL", "nie1JY", "xiao4JS", "gun1JH", "chao4CL", "er1JY", "chang1JS", "yong2JH", "zen1CL", "guai2JY", "yao3JS", "cou3JH", "lue4CL", "sang3JY", "en4JS", "miu2JH", "rui4CL", "jia4JY", "xiu1JS", "lao2JH", "reng1CL", "zao3JY", "dun3JS", "pen2JH", "er4CL", "guo1JY", "jiong2JS", "zuan3JH", "gao2CL", "ne1JY", "kuan3JS", "ru1JH", "he4CL", "lan2JY", "tiao4JS", "lian2JH", "ze3CL", "ha1JY", "yao4JS", "mang1JH", "e2CL"},
        {"103", "sai3JS", "che2JH", "mang3CL", "beng2JY", "xiao2JS", "da3JH", "weng2CL", "rou3JY", "yai3JS", "dan2JH", "yvn3CL", "kua3JY", "a3JS", "bu3JH", "sui2CL", "gong3JY", "huai3JS", "pie2JH", "dui2CL", "gong2JY", "kuan2JS", "shuo3JH", "tui3CL", "shu2JY", "xie2JS", "suan3JH", "ze2CL", "lue3JY", "yang2JS", "yv3JH", "fang3CL", "lin2JY", "wa3JS", "qin2JH", "he1CL", "jv1JY", "nai4JS", "niao1JH", "gui1CL", "kuang4JY", "piao4JS", "lou1JH", "zhuan4CL", "bie1JY", "liao4JS", "sui4JH", "nen1CL", "bao4JY", "tai4JS", "shang1JH", "xiong4CL", "huan1JY", "chuang4JS", "zhui4JH", "ao1CL", "beng4JY", "sai1JS", "ping4JH", "fang1CL", "sao4JY", "ji4JS", "she1JH", "cuan1CL", "hui1JY", "cuo4JS", "pian4JH", "e1CL", "kang4JY", "chou3JS", "sheng4JH", "min2CL", "zong3JY", "dou4JS", "pi2JH", "chuai1CL", "fa1JY", "cun3JS", "de2JH", "fu3CL", "pou4JY", "zhu3JS", "bie2JH", "cao1CL", "jin4JY", "shi1JS", "sa4JH", "shuai3CL", "dun2JY", "jing1JS", "kan3JH", "lue2CL", "gong1JY", "qia2JS", "ti3JH", "min4CL", "diu1JY", "me2JS", "hou4JH", "liang2CL", "tian1JY", "weng1JS", "ben4JH", "kuo1CL"},
        {"103", "zang2JS", "yvn2JH", "ke3CL", "shai3JY", "chang2JS", "zun3JH", "xi2CL", "cai3JY", "gan2JS", "za3JH", "shi3CL", "eng2JY", "shou2JS", "deng2JH", "ka3CL", "dou2JY", "hu3JS", "tiao3JH", "rao2CL", "luan3JY", "lu3JS", "zhao2JH", "chai2CL", "tang3JY", "na3JS", "niang2JH", "zhang3CL", "de3JY", "si3JS", "ru2JH", "chao3CL", "zhu2JY", "tui2JS", "can3JH", "bi1CL", "wai4JY", "zou1JS", "zhun4JH", "bin4CL", "ceng1JY", "jiu4JS", "pa4JH", "xi4CL", "lei1JY", "ka4JS", "lu1JH", "men4CL", "a4JY", "ta4JS", "nuo1JH", "chu1CL", "dang4JY", "du4JS", "tai1JH", "lan1CL", "gao4JY", "shun1JS", "meng1JH", "qi4CL", "xin1JY", "tuo4JS", "shei4JH", "zhuan1CL", "yv4JY", "juan4JS", "tui1JH", "pou1CL", "tiao1JY", "dui3JS", "dong4JH", "yai2CL", "zhuang1JY", "neng4JS", "zui2JH", "cang1CL", "pin3JY", "shu3JS", "fu1JH", "qin4CL", "pao1JY", "xia2JS", "fei3JH", "chuang2CL", "hai3JY", "lai2JS", "tong1JH", "mao4CL", "zhong3JY", "da1JS", "lang4JH", "qiu2CL", "hun3JY", "kang2JS", "kou1JH", "zhu1CL", "shao4JY", "zeng2JS", "pang1JH", "cha4CL", "qia4JY", "la2JS", "shan2JH", "gai3CL"},
        {"103", "zha3JS", "ni3JH", "chun2CL", "cong3JY", "wo2JS", "du2JH", "lvan3CL", "sao3JY", "guai3JS", "wai2JH", "song2CL", "hou2JY", "qi2JS", "niang3JH", "hou3CL", "bei2JY", "ling3JS", "nai3JH", "zou2CL", "pen3JY", "piao2JS", "su3JH", "ren2CL", "jiao3JY", "tan2JS", "la3JH", "yong3CL", "chai3JY", "nao2JS", "kai2JH", "zang3CL", "ao2JY", "leng3JS", "cun2JH", "quan4CL", "run4JY", "xue1JS", "an1JH", "mo4CL", "lie4JY", "jie1JS", "shu4JH", "po1CL", "qvn4JY", "chuan1JS", "di1JH", "duo4CL", "song1JY", "cai4JS", "pan1JH", "gen4CL", "jiong4JY", "cheng1JS", "duan4JH", "nian4CL", "qv1JY", "que1JS", "keng4JH", "zhuo4CL", "hun1JY", "chan1JS", "tao4JH", "cha1CL", "hong4JY", "shui1JS", "jie4JH", "shuang4CL", "kuang1JY", "nin3JS", "qiu1JH", "su4CL", "fan2JY", "shou4JS", "zeng4JH", "zhan1CL", "biao1JY", "yue4JS", "cui1JH", "zhen3CL", "duo3JY", "yai1JS", "dang2JH", "shen1CL", "pie3JY", "sa2JS", "piao1JH", "ge2CL", "keng1JY", "chu2JS", "qian3JH", "chuo3CL", "pen1JY", "jian4JS", "gu2JH", "ke4CL", "kai3JY", "hao1JS", "xiang2JH", "ma4CL", "die2JY", "quan3JS", "chu4JH", "ou2CL"},
        {"103", "xiong3JS", "shao2JH", "bin3CL", "chan2JY", "zhi2JS", "nv3JH", "le3CL", "dian3JY", "te3JS", "fen2JH", "cou2CL", "du3JY", "lv2JS", "diao2JH", "sheng3CL", "xiu3JY", "zai3JS", "chong2JH", "han2CL", "qv3JY", "qian2JS", "hai2JH", "en3CL", "cheng2JY", "zei3JS", "guo3JH", "zhai3CL", "shen2JY", "sui3JS", "lian3JH", "heng2CL", "jie2JY", "guan2JS", "han3JH", "nou1CL", "bei4JY", "le1JS", "wo1JH", "chen4CL", "pao4JY", "de1JS", "nv4JH", "qing4CL", "ding1JY", "zhai1JS", "mi4JH", "ting4CL", "ti4JY", "dei1JS", "du1JH", "ce4CL", "bu4JY", "man1JS", "hen4JH", "suan1CL", "dai4JY", "nei4JS", "ping1JH", "nen4CL", "zang1JY", "lvan1JS", "zai4JH", "ya1CL", "kao1JY", "diu4JS", "huan4JH", "gan1CL", "zhi4JY", "nei3JS", "beng1JH", "ke2CL", "gao1JY", "gang3JS", "fo2JH", "keng3CL", "ca4JY", "rong2JS", "heng4JH", "diao3CL", "pa1JY", "mang4JS", "cu4JH", "leng2CL", "zhang4JY", "chi1JS", "xian4JH", "kuang2CL", "dai3JY", "qing1JS", "dao2JH", "ran1CL", "ding3JY", "ren3JS", "suan2JH", "hui3CL", "dao1JY", "duan1JS", "shang4JH", "wei2CL", "geng4JY", "bang2JS", "zha2JH", "sang1CL"},
        {"103", "shou3JS", "fu2JH", "ma3CL", "lao3JY", "pang2JS", "na2JH", "mao3CL", "po3JY", "ran3JS", "zhou2JH", "kao2CL", "ha3JY", "xing2JS", "ta2JH", "tie3CL", "nao3JY", "tang2JS", "an2JH", "cuan3CL", "gen3JY", "bi2JS", "miu3JH", "die3CL", "kui3JY", "se2JS", "zhuai2JH", "wen3CL", "jiong3JY", "shui2JS", "wan2JH", "ye3CL", "ruo3JY", "huan2JS", "lei2JH", "da4CL", "en1JY", "zhuang4JS", "gun4JH", "shou1CL", "tong4JY", "se1JS", "kao4JH", "sheng1CL", "yin4JY", "ang4JS", "wa1JH", "chui4CL", "sang4JY", "pin4JS", "te1JH", "zheng1CL", "nuan1JY", "tu4JS", "nong1JH", "heng1CL", "qian1JY", "gai4JS", "xuan4JH", "shao1CL", "zen4JY", "jue1JS", "nong4JH", "guo4CL", "su1JY", "nue1JS", "zhan4JH", "zong1CL", "biao4JY", "gui3JS", "you4JH", "kui2CL", "gou3JY", "san4JS", "gua1JH", "bai3CL", "reng2JY", "pu3JS", "yi1JH", "ying2CL", "quan1JY", "kua4JS", "sen2JH", "gua3CL", "deng1JY", "sun3JS", "rang4JH", "kua2CL", "shu1JY", "wen4JS", "qvn1JH", "bin2CL", "gou4JY", "chuan3JS", "han4JH", "weng3CL", "dang1JY", "xue2JS", "mian1JH", "pu2CL", "li1JY", "re4JS", "wang2JH", "zhi1CL"},
        {"103", "bai2JS", "pao3JH", "gou2CL", "sa3JY", "huang2JS", "bang3JH", "zai2CL", "sen3JY", "hei2JS", "shang3JH", "e3CL", "bian2JY", "bao3JS", "quan2JH", "qv2CL", "mie3JY", "liao3JS", "kong2JH", "dao3CL", "kun3JY", "hong2JS", "bu2JH", "kao3CL", "xv3JY", "bing2JS", "gao3JH", "ding2CL", "reng3JY", "rou2JS", "ang3JH", "ta3CL", "dei2JY", "jian2JS", "nu3JH", "tou4CL", "lun1JY", "wei4JS", "shun4JH", "dai1CL", "mie4JY", "yvn1JS", "duo1JH", "yuan4CL", "mei4JY", "pen4JS", "ze1JH", "shuan4CL", "zai1JY", "qiao1JS", "leng4JH", "ling1CL", "zhong1JY", "shuai4JS", "kuan1JH", "ga4CL", "tao1JY", "hou4JS", "chuo4JH", "kun1CL", "cu1JY", "chuan4JS", "fei4JH", "zhuo1CL", "jiao1JY", "qiang4JS", "hai4JH", "zun1CL", "guang4JY", "man4JS", "zhei2JH", "die4CL", "cao2JY", "peng4JS", "zhei1JH", "que2CL", "zen3JY", "mu2JS", "qi1JH", "nen3CL", "wei1JY", "lie1JS", "pian2JH", "zhen1CL", "xiong2JY", "yang4JS", "he2JH", "ku4CL", "sha4JY", "yin3JS", "sou3JH", "chan4CL", "te2JY", "juan3JS", "long2JH", "kuang3CL", "teng1JY", "xiang1JS", "chao2JH", "hang3CL", "na1JY", "bie4JS", "gen1JH", "zhuan3CL"},
        {"103", "ye2JS", "zan3JH", "duan3CL", "ban2JY", "pian3JS", "zhuo3JH", "lie2CL", "pan3JY", "yo2JS", "ba3JH", "jiu3CL", "ga3JY", "zhe2JS", "ceng2JH", "qie2CL", "wang3JY", "kuai2JS", "zhua3JH", "fang2CL", "zheng3JY", "qvn3JS", "luo2JH", "cui3CL", "xue3JY", "cheng3JS", "hui2JH", "ping3CL", "shi2JY", "ha2JS", "ti2JH", "seng3CL", "hua2JY", "chuai3JS", "ping2JH", "shai1CL", "ou4JY", "jian1JS", "guai4JH", "pi1CL", "shui4JY", "die1JS", "jvn4JH", "xiu4CL", "chun4JY", "yuan1JS", "mai4JH", "zhua4CL", "cong1JY", "jin1JS", "sa1JH", "cang4CL", "niang1JY", "zhong4JS", "dui1JH", "ye4CL", "fou1JY", "sai4JS", "gang4JH", "you1CL", "bian4JY", "tun1JS", "ming1JH", "lv4CL", "ai4JY", "zuan4JS", "rao1JH", "zeng1CL", "che4JY", "zi1JS", "zang4JH", "kuo3CL", "lia2JY", "tang4JS", "bao1JH", "long3CL", "ni4JY", "guang3JS", "nan4JH", "me1CL", "chong3JY", "ka1JS", "feng2JH", "yin1CL", "miu1JY", "nin4JS", "dian2JH", "jiu1CL", "hen1JY", "pou3JS", "nei1JH", "niu2CL", "wa4JY", "ban3JS", "re2JH", "cheng4CL", "ma2JY", "rao3JS", "seng4JH", "zei2CL", "lu2JY", "shao3JS", "chuo1JH", "zhong2CL"},
        {"103", "jin2JS", "dang3JH", "jiang3CL", "ri2JY", "kang3JS", "ga2JH", "ceng3CL", "yue3JY", "biao2JS", "gui2JH", "gei3CL", "tuo2JY", "nu2JS", "peng3JH", "zuan2CL", "jvn3JY", "shuang2JS", "en2JH", "bian3CL", "qiong3JY", "gei2JS", "pa3JH", "ken2CL", "qia3JY", "mian2JS", "cai2JH", "xi3CL", "dan3JY", "ku3JS", "qiang3JH", "er2CL", "pai2JY", "men3JS", "yang3JH", "fa4CL", "rang1JY", "kuai4JS", "shuan1JH", "bo1CL", "huai4JY", "nu4JS", "hang4JH", "gu1CL", "tui4JY", "zi4JS", "song4JH", "guai1CL", "zhang1JY", "zui1JS", "yi4JH", "gui4CL", "san1JY", "gou1JS", "she4JH", "kui1CL", "dun4JY", "pu4JS", "yo1JH", "luan4CL", "mai1JY", "nuo4JS", "xiao1JH", "zuo4CL", "chou1JY", "pei1JS", "yai4JH", "mou1CL", "tian4JY", "lou4JS", "za4JH", "sha2CL", "biao3JY", "de4JS", "cen2JH", "zhou4CL", "she3JY", "tuo3JS", "fan1JH", "pan4CL", "jiang1JY", "nv2JS", "ruan3JH", "kang1CL", "yue2JY", "pie1JS", "shuang1JH", "gun2CL", "ru3JY", "mou2JS", "jian3JH", "hen2CL", "xuan1JY", "niu4JS", "zha4JH", "ai3CL", "tu2JY", "wang1JS", "kou2JH", "bang1CL", "lian1JY", "neng2JS", "chang3JH", "sou4CL"},
        {"103", "xia3JS", "yi2JH", "can2CL", "ji3JY", "jiao2JS", "man2JH", "pei3CL", "zao2JY", "chen3JS", "jv2JH", "qi3CL", "wu2JY", "shui3JS", "run3JH", "fan3CL", "zeng3JY", "gua2JS", "song3JH", "gu3CL", "jia3JY", "nan2JS", "tie2JH", "zun2CL", "dei3JY", "sun2JS", "xian2JH", "shan3CL", "yin2JY", "cen3JS", "zhi3JH", "ying3CL", "zu2JY", "gai2JS", "tong3JH", "chi4CL", "dou1JY", "zhao1JS", "diao4JH", "ruan4CL", "fo1JY", "zhuai1JS", "shen4JH", "che1CL", "liu4JY", "bing1JS", "teng4JH", "shuo4CL", "kan1JY", "gai1JS", "zhu4JH", "huai1CL", "fen1JY", "shi4JS", "cuan4JH", "ran4CL", "ce1JY", "lin4JS", "hao4JH", "ku1CL", "fan4JY", "tuo1JS", "ha4JH", "si4CL", "dong1JY", "luo4JS", "neng1JH", "cao4CL", "lv1JY", "chou4JS", "yan2JH", "zou4CL", "se3JY", "shei2JS", "hu4JH", "shuan3CL", "xing1JY", "chen2JS", "hang1JH", "jue2CL", "ga1JY", "an4JS", "gun3JH", "jing3CL", "mei2JY", "diao1JS", "tian3JH", "jue4CL", "tuan2JY", "tie4JS", "fen3JH", "yong1CL", "ba1JY", "hua3JS", "liang4JH", "qiang1CL", "xi1JY", "qiong2JS", "zao4JH", "pang3CL", "ci1JY", "shuo2JS", "yv1JH", "bao2CL"},
        {"103", "ting2JS", "qvn2JH", "ca3CL", "guang2JY", "chui3JS", "huan3JH", "rui3CL", "ruan2JY", "zhen2JS", "meng3JH", "peng2CL", "jing2JY", "xin3JS", "hei3JH", "cong2CL", "shuai2JY", "ya3JS", "geng2JH", "zui3CL", "nian2JY", "qin3JS", "min3JH", "zhai2CL", "lan3JY", "ka2JS", "fa3JH", "zou3CL", "xin2JY", "chun3JS", "nan3JH", "shua3CL", "fei2JY", "san2JS", "feng3JH", "hu1CL", "se4JY", "luo1JS", "shuai1JH", "xiang4CL", "zuan1JY", "xv1JS", "rao4JH", "ling4CL", "ke1JY", "xie4JS", "jia1JH", "pai4CL", "shan4JY", "ken1JS", "huang1JH", "miao4CL", "chong4JY", "tan1JS", "jiao4JH", "cun4CL", "zan4JY", "kai1JS", "ye1JH", "xiong1CL", "ba4JY", "sen4JS", "gua4JH", "rui1CL", "kuan4JY", "nou4JS", "hai1JH", "chun1CL", "bai4JY", "chuang3JS", "liu1JH", "xiu2CL", "que4JY", "qiang2JS", "xue4JH", "zhan2CL", "nie3JY", "shan1JS", "jiu2JH", "can4CL", "fo4JY", "beng3JS", "lai1JH", "ning2CL", "suo1JY", "ken4JS", "xuan3JH", "xing3CL", "zong2JY", "tun3JS", "pao2JH", "ren4CL", "wan3JY", "nou2JS", "ruan1JH", "sheng2CL", "si1JY", "wo3JS", "sao1JH", "fang4CL", "pi4JY", "ning1JS", "ang2JH", "ou1CL"},
        {"104", "gan3JS", "chou2JH", "zhan3CL", "liu3JY", "gang2JS", "xuan2JH", "fa2CL", "rang3JY", "tian2JS", "zhou3JH", "mi2CL", "chan3JY", "dai2JS", "di3JH", "tou3CL", "chi3JY", "tun2JS", "ji2JH", "niao3CL", "ruo2JY", "lun3JS", "teng3JH", "niao2CL", "chuo2JY", "ci3JS", "zhei3JH", "cuo2CL", "lia3JY", "zhun2JS", "hong3JH", "bo3CL", "lang2JY", "da2JS", "hao3JH", "gei1CL", "peng1JY", "sun4JS", "cong4JH", "lvan4CL", "rong1JY", "kun4JS", "sha1JH", "pang4CL", "rou1JY", "zhe1JS", "kong4JH", "weng4CL", "sou1JY", "pai1JS", "ri4JH", "mu1CL", "wang4JY", "xie1JS", "bing4JH", "zhou1CL", "gei4JY", "chai1JS", "dun1JH", "nue4CL", "yang1JY", "ban1JS", "xvn4JH", "suo4CL", "kua1JY", "zong4JS", "zao1JH", "rong4CL", "chuai4JY", "huai2JS", "li4JH", "zheng2CL", "ban4JY", "che3JS", "bo2JH", "bing3CL", "juan1JY", "zhao4JS", "za2JH", "huang3CL", "jv4JY", "shen3JS", "xvn1JH", "zhe3CL", "shei1JY", "hu2JS", "ting1JH", "cu2CL", "pai3JY", "tou1JS", "di4JH", "ta1CL", "jia2JY", "deng3JS", "wu1JH", "jiang2CL", "ruo4JY", "bo4JS", "yan1JH", "chui2CL", "hou3JY", "ji1JS", "ying4JH", "cuo1CL", "eng3JY"},
        {"104", "jvn2JS", "wei3JH", "xv2CL", "tu3JY", "hang2JS", "shang2JH", "diu3CL", "ao3JY", "a2JS", "lou3JH", "zhuang3CL", "xvn2JY", "si2JS", "zhang2JH", "piao3CL", "me3JY", "nang2JS", "ba2JH", "que3CL", "cha3JY", "ni2JS", "xvn3JH", "she2CL", "jue3JY", "re3JS", "mai2JH", "chuan2CL", "rong3JY", "ku2JS", "xiao3JH", "shun2CL", "kuo2JY", "zhui3JS", "cu3JH", "ge1CL", "zhe4JY", "ning4JS", "fou4JH", "wen1CL", "dui4JY", "tuan4JS", "qiong1JH", "chuang1CL", "tun4JY", "zhuai4JS", "cou1JH", "kai4CL", "mo1JY", "ci4JS", "zhai4JH", "yao1CL", "pei4JY", "lia1JS", "han1JH", "qiao4CL", "chui1JY", "ao4JS", "hou1JH", "zu4CL", "mao1JY", "dian1JS", "mian4JH", "tu1CL", "ru4JY", "jiong1JS", "sun1JH", "ze4CL", "lei4JY", "meng2JS", "wan4JH", "cang3CL", "gu4JY", "sui1JS", "qing3JH", "ran2CL", "sen1JY", "chao1JS", "hong1JH", "dan4CL", "kong3JY", "nin1JS", "nong2JH", "zhuai3CL", "ge4JY", "nie4JS", "hao2JH", "tan4CL", "gang1JY", "ce3JS", "qie3JH", "gen2CL", "zuo1JY", "jvn1JS", "sao2JH", "la4CL", "shua1JY", "lin1JS", "zu3JH", "tou2CL", "nang1JY", "wa2JS", "su2JH", "zhao3CL", "an3JY"}};
    private String topic_url;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
        bt_next.setVisibility(View.INVISIBLE);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_speak_talker.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_control);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        day=sharedPreferences.getString("day",null);
        level=sharedPreferences.getString("level",null);
        image_background = findViewById(R.id.image_background);
        bt_topic_speak = findViewById(R.id.bt_topic_speak);
        bt_speak_talker = findViewById(R.id.bt_speak_talker);
        bt_speak_start = findViewById(R.id.bt_speak_start);
        bt_stop_speak = findViewById(R.id.bt_stop_speak);
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_next = findViewById(R.id.bt_next);
        bt_next.setVisibility(View.INVISIBLE);
        bt_next.setEnabled(false);
        text_count = findViewById(R.id.text_count);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        if (!level.equals("Teacher")){
            if (day.equals("0")){
                //insert to topic speak
                String type = "BuildTestData";
                Backgorundwork backgorundwork = new Backgorundwork(group_control.this);
                backgorundwork.execute(type,uid);
                //pre-test
                Intent ToPreTest=new Intent(group_control.this,PreTestActivity.class);
                startActivity(ToPreTest);
                finish();
            }
            else{
                //get index
                LoadTopicSpeak(uid);
//                if (Integer.parseInt(index) >Integer.parseInt(day_array[Integer.parseInt(day)][0])){
//                    Toast.makeText(getApplicationContext(), "Today is finish", Toast.LENGTH_LONG).show();
//                    //Todo into the activity  "Today is finish "
//                    Intent ToLogin=new Intent(group_control.this,LoginActivity.class);
//                    startActivity(ToLogin);
//                    finish();
//                }
            }
        }
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        //

    }

    public void bt_next(View view) {
        String index_add=Integer.toString(Integer.parseInt(index)+1);
        Backgorundwork backgorundwork = new Backgorundwork(this);
        backgorundwork.execute("Upload_record",mFileName,uid,index_add,fname);

    }

    public void bt_recode_playing(View view) {
        startPlaying();
    }

    public void bt_recode_start(View view) {
        count_record+=1;
        if(count_record<4){
            bt_next.setVisibility(View.INVISIBLE);
            bt_next.setEnabled(false);
            bt_speak_talker.setVisibility(View.INVISIBLE);
            bt_speak_talker.setEnabled(false);
            mFileName = getExternalCacheDir().getAbsolutePath();
            fname=uid+"_d"+day+"_"+index;
            mFileName += "/"+fname+".3gp";
            startRecording();
            Toast.makeText(getApplicationContext(), "Start Recording", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "You can't record more than three times,please click \"GO\".", Toast.LENGTH_LONG).show();
        }

    }


    public void bt_recode_stop(View view) {
        bt_stop_speak.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(false);
        bt_speak_start.setVisibility(View.VISIBLE);
        bt_speak_start.setEnabled(true);
        bt_speak_talker.setVisibility(View.VISIBLE);
        bt_speak_talker.setEnabled(true);
        bt_next.setVisibility(View.VISIBLE);
        bt_next.setEnabled(true);
        stopRecording();
        Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_LONG).show();
    }

    public void bt_topic_speak(View view) {
        topic_url="http://140.122.63.99/topic_audio/all_audio/"+day_array[Integer.parseInt(day)-1][Integer.parseInt(index)]+".wav";
        count_topic+=1;
        if(count_topic<4){
            if (!playPause) {
                Toast.makeText(getApplicationContext(), "Topic is playing", Toast.LENGTH_SHORT).show();
                if (initialStage) {
                    new Player().execute(topic_url);
                } else {
                    if (!mPlayer.isPlaying())
                        mPlayer.start();
                }

                playPause = true;

            } else {
                Toast.makeText(getApplicationContext(), "Stop playing", Toast.LENGTH_SHORT).show();
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }

                playPause = false;
            }


        }else{
            Toast.makeText(getApplicationContext(), "You can't play more than three times.", Toast.LENGTH_LONG).show();

        }
    }

    public void LoadTopicSpeak(final String uid){
        String url = "http://140.122.63.99/app/load_topic_speak.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response:",response);
                        try {
                            byte[] u = response.getBytes(
                                    "UTF-8");
                            response = new String(u, "UTF-8");
                            Log.d(ContentValues.TAG, "Response " + response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<ItemTopicSpeak> posts = new ArrayList<ItemTopicSpeak>();
                            posts = Arrays.asList(mGson.fromJson(response, ItemTopicSpeak[].class));
                            index=posts.get(0).getTopic_index();
                            Log.e(LOG_TAG, "My index~"+index);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //do stuffs with response erro
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",uid);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(group_control.this);
        requestQueue.add(stringRequest);
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    private void startRecording() {
        bt_speak_start.setEnabled(false);
        bt_speak_start.setVisibility(View.INVISIBLE);
        bt_stop_speak.setEnabled(true);
        bt_stop_speak.setVisibility(View.VISIBLE);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName);
        Log.e(LOG_TAG, "prepare() failed"+mFileName);


        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            Log.d("EEEEEEEEEEEEEEEE","setDataSource="+strings[0]);
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(strings[0]);
                Log.d("EEEEEEEEEEEEEEEE","setDataSource="+strings[0]);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }

}
