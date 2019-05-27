package io.goooler.pisciculturemanager.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 用户信息表
 */
@Entity
public class UserBean {

    /**
     * @username 登录的用户名
     * @password 登录的密码
     */

    @Id
    private String username;
    @NotNull
    private String password;

    public UserBean() {
    }

    @Generated(hash = 2016763543)
    public UserBean(String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
