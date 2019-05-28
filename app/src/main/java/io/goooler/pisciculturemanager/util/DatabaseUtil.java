package io.goooler.pisciculturemanager.util;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.List;

import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.OverallDataBeanDao;
import io.goooler.pisciculturemanager.model.UserBean;
import io.goooler.pisciculturemanager.model.UserBeanDao;
import io.goooler.pisciculturemanager.model.WarnningDataBean;
import io.goooler.pisciculturemanager.model.WarnningDataBeanDao;

/**
 * 对 GreenDao 的简单封装，以便统一异常处理等操作
 * 现在有几个特定的方法，还有通用的异步插入方法，通配泛型
 */

public class DatabaseUtil {

    public static final int LATEST_ONE = 1;
    public static final int FIRST_INDEX = 0;

    public static void initDatabase() {
        insert(new OverallDataBean());
    }

    /**
     * 验证是否存在该用户，同步调用
     */
    public static boolean haveTheUser(String username) {
        return BaseApplication.getDaoSession().getUserBeanDao().
                queryBuilder().where(UserBeanDao.Properties.Username.
                eq(username)).build().list().size() != 0;
    }

    /**
     * 添加一名用户，同步调用
     * 这里添加用户名和密码做一次 trim 操作
     */
    public static void addUser(String username, String password) {
        BaseApplication.getDaoSession().getUserBeanDao().
                insert(new UserBean(username.trim(), password.trim()));
    }

    /**
     * 获取用户的信息，同步调用
     */
    public static UserBean getUser(String username) {
        return BaseApplication.getDaoSession().getUserBeanDao().
                queryBuilder().where(UserBeanDao.Properties.Username.
                eq(username)).build().list().get(0);
    }

    /**
     * 验证用户名和密码是否匹配，同步调用
     */
    public static boolean verifyUser(String username, String password) {
        return getUser(username).getPassword().equals(password);
    }

    /**
     * 同步查询，查所有参数的历史数据的最新一条
     *
     * @return 返回最新一条的数据
     */
    public static OverallDataBean getLatestOverallOne() {
        return getLatestOverall(LATEST_ONE).get(FIRST_INDEX);
    }

    /**
     * 同步查询，查所有参数的历史数据
     *
     * @param number 最新时间往前推 n 条数据
     * @return 返回数据集合
     */
    public static List<OverallDataBean> getLatestOverall(int number) {
        return BaseApplication.getDaoSession().getOverallDataBeanDao().queryBuilder().
                orderDesc(OverallDataBeanDao.Properties.Timestamp).limit(number).build().list();
    }

    /**
     * 异步查询，查所有参数的历史数据
     *
     * @param number 最新时间往前推 n 条数据
     * @return 返回数据集合，必须回调
     */
    public static void getLatestOverall(int number, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        asyncSession.queryList(BaseApplication.getDaoSession().getOverallDataBeanDao().queryBuilder().
                orderDesc(OverallDataBeanDao.Properties.Timestamp).limit(number).build());
    }

    /**
     * 同步查询，查预警消息的历史数据
     * 这里查询越界不会报错，仅返回可查询最大值
     *
     * @param number 最新时间往前推 n 条数据
     * @return 返回数据集合
     */
    public static List<WarnningDataBean> getLatestWarnning(int number) {
        return BaseApplication.getDaoSession().getWarnningDataBeanDao().queryBuilder().
                orderDesc(WarnningDataBeanDao.Properties.Timestamp).limit(number).build().list();
    }

    /**
     * 异步查询，查预警消息的历史数据，同步查询太多会阻塞主线程
     * 这里查询越界不会报错，仅返回可查询最大值
     *
     * @param number 最新时间往前推 n 条数据
     * @return 返回数据集合，必须回调
     */
    public static void getLatestWarnning(int number, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        asyncSession.queryList(BaseApplication.getDaoSession().getWarnningDataBeanDao().queryBuilder().
                orderDesc(WarnningDataBeanDao.Properties.Timestamp).limit(number).build());
    }


    /**
     * 异步任务但不回调
     */
    public static void insert(Object entity) {
        insertTrue(entity, null);
    }

    /**
     * 异步任务，需要回调
     *
     * @param entity 要插入的数据
     */
    public void insert(Object entity, AsyncOperationListener asyncOperationListener) {
        insertTrue(entity, asyncOperationListener);
    }

    /**
     * 真正实现插入的方法，仅内部调用
     * 异步任务，需要回调，支持单个插入或批量插入
     * bean 直接插入，list 取其中第一个对象通过反射判断类型
     * 这里使用 insertOrReplace 方法替代单纯的插入，可实现有则更新无则插入
     *
     * @param entity                 要插入的数据，可以是 list 或 bean
     * @param asyncOperationListener 接口回调
     */
    private static void insertTrue(Object entity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = BaseApplication.getDaoSession().startAsyncSession();
        asyncSession.setListener(asyncOperationListener);
        if (entity instanceof List) {
            if (!EmptyUtil.isEmpty((List) entity)) {
                asyncSession.insertOrReplaceInTx(((List) entity).get(FIRST_INDEX).getClass(),
                        (List) entity);
            }
        } else {
            asyncSession.insertOrReplace(entity);
        }
    }
}
