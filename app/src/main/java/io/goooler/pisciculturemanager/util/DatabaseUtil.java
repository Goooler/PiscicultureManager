package io.goooler.pisciculturemanager.util;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.List;

import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.OverallDataBeanDao;

/**
 * 对 GreenDao 的简单封装，以便统一异常处理等操作
 * 现在专门用作 OverallDataBean 对应的数据库的各种操作
 * TODO: 以后考虑以这部分加入泛型，以便更具备通用性
 */

public class DatabaseUtil {

    public static final int LATEST_ONE = 1;
    public static final int FIRST_INDEX = 0;

    public static void insertTrue(OverallDataBean bean, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        asyncSession.insert(bean);
    }

    /**
     * 异步任务但不回调
     *
     * @param o 要插入的数据
     */
    public static void insert(Object o) {
        insertTrue(o, null);
    }

    /**
     * 异步任务，需要回调
     *
     * @param o 要插入的数据
     */
    public void insert(Object o, AsyncOperationListener asyncOperationListener) {
        insertTrue(o, asyncOperationListener);
    }

    /**
     * 真正实现插入的方法，仅内部调用
     * 异步任务，需要回调，支持单个插入或批量插入
     * <p>
     * 这里使用 insertOrReplace 方法替代单纯的插入，可实现有则更新无则插入
     *
     * @param o                      要插入的数据，可是 list 或 bean
     * @param asyncOperationListener 接口回调
     */
    private static void insertTrue(Object o, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        if (o instanceof List) {
            asyncSession.insertOrReplaceInTx(OverallDataBean.class, (List<OverallDataBean>) o);
        } else {
            asyncSession.insertOrReplace(o);
        }
    }

    /**
     * 同步查询，
     *
     * @return 返回最新一条的数据
     */
    public static OverallDataBean getLatestOne() {
        return getLatest(LATEST_ONE).get(FIRST_INDEX);
    }

    /**
     * 同步查询，
     *
     * @param number 最新时间往前推 n 条数据
     * @return 返回数据集合
     */
    public static List<OverallDataBean> getLatest(int number) {
        return BaseApplication.getDaoSession().getOverallDataBeanDao().queryBuilder().
                orderDesc(OverallDataBeanDao.Properties.Timestamp).limit(number).build().list();
    }
}
