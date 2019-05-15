package io.goooler.pisciculturemanager.model;

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
