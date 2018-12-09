package org.danktronics.jdca.entities.impl;

import org.danktronics.jdca.entities.User;
import org.json.JSONObject;

public class UserImpl implements User {
    private String id;
    private String username;
    private String avatar;
    private boolean bot;
    private boolean admin;

    public UserImpl(JSONObject data) {
        this.id = data.getString("id");
        this.username = data.getString("username");
        this.avatar = data.getString("avatar");
        this.bot = data.getBoolean("bot");
        this.admin = data.has("admin") ? data.getBoolean("admin") : null;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isAdmin() {
        return admin;
    }
}
