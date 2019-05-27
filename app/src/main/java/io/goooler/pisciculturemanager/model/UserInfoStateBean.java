package io.goooler.pisciculturemanager.model;

/**
 * 记录用户登录状态
 */
public class UserInfoStateBean {
    private String username;
    private boolean saved;

    public UserInfoStateBean(String username, boolean saved) {
        this.username = username;
        this.saved = saved;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSaved() {
        return saved;
    }
}
