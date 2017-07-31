package com.wuxianedu.oschina.bean;
//                .-~~~~~~~~~-._       _.-~~~~~~~~~-.
//            __.'              ~.   .~              `.__
//          .'//                  \./                  \\`.
//        .'//                     |                     \\`.
//      .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
//    .'//.-"                 `-.  |  .-'                 "-.\\`.
//  .'//______.============-..   \ | /   ..-============.______\\`.
//.'______________________________\|/______________________________`.

import java.io.Serializable;

/**
 * Created by scy on 2016/12/1.
 */

public class User implements Serializable{

    /**
     * id : 1101925
     * username : Aknow
     * name : Aknow
     * bio : null
     * weibo : null
     * blog : null
     * theme_id : 1
     * state : active
     * created_at : 2016-11-09T19:21:50+08:00
     * portrait : null
     * email : miaojinxiang@126.com
     * new_portrait : http://secure.gravatar.com/avatar/011b303ff70ee6e5f6a5409f6a24f23b?s=40&d=mm
     * follow : {"followers":0,"starred":0,"following":0,"watched":0}
     * private_token : zm6oCp3oeNPNpKJh6HVM
     * is_admin : false
     * can_create_group : true
     * can_create_project : true
     * can_create_team : true
     */

    private int id;
    private String username;
    private String name;
    private Object bio;
    private Object weibo;
    private Object blog;
    private int theme_id;
    private String state;
    private String created_at;
    private Object portrait;
    private String email;
    private String new_portrait;
    private FollowBean follow;
    private String private_token;
    private boolean is_admin;
    private boolean can_create_group;
    private boolean can_create_project;
    private boolean can_create_team;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getWeibo() {
        return weibo;
    }

    public void setWeibo(Object weibo) {
        this.weibo = weibo;
    }

    public Object getBlog() {
        return blog;
    }

    public void setBlog(Object blog) {
        this.blog = blog;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Object getPortrait() {
        return portrait;
    }

    public void setPortrait(Object portrait) {
        this.portrait = portrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this.new_portrait = new_portrait;
    }

    public FollowBean getFollow() {
        return follow;
    }

    public void setFollow(FollowBean follow) {
        this.follow = follow;
    }

    public String getPrivate_token() {
        return private_token;
    }

    public void setPrivate_token(String private_token) {
        this.private_token = private_token;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isCan_create_group() {
        return can_create_group;
    }

    public void setCan_create_group(boolean can_create_group) {
        this.can_create_group = can_create_group;
    }

    public boolean isCan_create_project() {
        return can_create_project;
    }

    public void setCan_create_project(boolean can_create_project) {
        this.can_create_project = can_create_project;
    }

    public boolean isCan_create_team() {
        return can_create_team;
    }

    public void setCan_create_team(boolean can_create_team) {
        this.can_create_team = can_create_team;
    }

    public static class FollowBean implements Serializable{
        /**
         * followers : 0
         * starred : 0
         * following : 0
         * watched : 0
         */

        private int followers;
        private int starred;
        private int following;
        private int watched;

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getStarred() {
            return starred;
        }

        public void setStarred(int starred) {
            this.starred = starred;
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }
    }
}
