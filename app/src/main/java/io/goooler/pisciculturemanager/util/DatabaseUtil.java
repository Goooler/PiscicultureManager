package io.goooler.pisciculturemanager.util;

import org.greenrobot.greendao.DaoException;

import java.util.List;

import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.OverallDataBean;

/**
 * 对 GreenDao 的简单封装，以便统一异常处理等操作
 */

public class DatabaseUtil {

    public static void insert(OverallDataBean bean) {
        try {
            BaseApplication.getDaoSession().getOverallDataBeanDao().insert(bean);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void insert(List<OverallDataBean> beans) {
        try {
            BaseApplication.getDaoSession().getOverallDataBeanDao().insertInTx(beans);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
