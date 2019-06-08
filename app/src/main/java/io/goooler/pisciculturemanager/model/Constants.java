package io.goooler.pisciculturemanager.model;

import androidx.annotation.IntDef;

/**
 * 常量集合
 */
public class Constants {
    //数据库库名
    public static final String DB_NAME = "Manager.db";
    public static final String NULL_STRING = "";
    public static final String NULL_OBJECT = null;
    //存储用户信息的 sp
    public static final String SP_USERINFO = "user_config";
    //判断启动状态的 sp
    public static final String SP_RUNINFO = "run_config";
    public static final String SP_FIRST_RUN = "firstRun";
    public static final String BASE_ACTIVITY = "BaseActivity";
    public static final String FINISH_ALL_ACTIVITY = "finished all activities";
    public static final String COORDINATES = "coordinates";
    public static final String PARAMS = "params";
    public static final String DATA = "data";
    public static final String KEY_AND_VALUE = "keyAndValue";
    public static final String LABLE = "lable";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String MIPMAP = "mipmap";
    // MainActivity 下发跳转
    public static final String GOTO_FRAGMENT_ID = "gotoFragmentId";
    //一小时包含的毫秒数
    public static final int ONE_HOUR_MILLISECONDS = 60 * 60 * 1000;
    //最近的24条数据
    public static final int LATEST_24 = 24;

    public static final String ALTER_PASSWORD = "alterPassword";

    /**
     * PendingIntent 请求码
     */
    public static final int DEFAULT_NOTIFICATION_ID = 0;
    public static final int SERVICE_REQUEST_CODE = 1;
    public static final int ACTIVITY_REQUEST_CODE = 2;

    /**
     * 下发跳转首页的 fragment 约定的 id
     */
    public static final int NULL_FRAGMENT_ID = -1;
    public static final int OVERALL_FRAGMENT_ID = 0;
    public static final int DETAIL_FRAGMENT_ID = 1;
    public static final int NOTIFICATION_FRAGMENT_ID = 2;
    public static final int PERSON_FRAGMENT_ID = 3;

    /**
     * 首页几个 item 对应的位置，也是 OverallDataBean 里参数对应的 id
     */
    public static final int OXYGEN_POS = 0;
    public static final int TEMPERATURE_POS = 1;
    public static final int PH_POS = 2;
    public static final int NITROGEN_POS = 3;
    public static final int NITRITE_POS = 4;

    @IntDef({OVERALL_FRAGMENT_ID, DETAIL_FRAGMENT_ID, NOTIFICATION_FRAGMENT_ID, PERSON_FRAGMENT_ID})
    public @interface FragmentId {
    }

    @IntDef({OXYGEN_POS, TEMPERATURE_POS, PH_POS, NITROGEN_POS, NITRITE_POS})
    public @interface ItemPosition {
    }
}
