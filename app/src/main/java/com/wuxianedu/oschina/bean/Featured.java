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
 * Created by shicunyu on 2016/11/28.
 */

public class Featured implements Serializable{

    /**
     * id : 621587
     * name : SD.CMS
     * description : 使用RazorEngine做模板/全站静态页/内容管理系统/快速开发企业站
     * default_branch : master
     * owner : {"id":372841,"username":"lishilei0523","email":"lishilei0523@163.com","name":"黄昏MMM","state":"active","created_at":"2015-04-24T09:32:19+08:00","portrait":"uploads/41/372841_lishilei0523.png?1439795747","new_portrait":"http://git.oschina.net/uploads/41/372841_lishilei0523.png?1439795747"}
     * public : true
     * path : SlamDunk_CMS
     * path_with_namespace : lishilei0523/SlamDunk_CMS
     * issues_enabled : true
     * pull_requests_enabled : true
     * wiki_enabled : true
     * created_at : 2015-11-09T22:12:50+08:00
     * namespace : {"address":"192.168.2.55","avatar":null,"created_at":"2015-04-24T09:32:19+08:00","description":"","email":null,"enterprise_id":0,"id":365801,"level":0,"location":null,"name":"lishilei0523","owner_id":372841,"path":"lishilei0523","public":null,"updated_at":"2016-06-27T01:14:59+08:00","url":null}
     * last_push_at : 2016-11-27T11:41:58+08:00
     * parent_id : null
     * fork? : false
     * forks_count : 7
     * stars_count : 4
     * watches_count : 7
     * language : C#
     * paas : null
     * stared : null
     * watched : null
     * relation : null
     * recomm : 1
     * parent_path_with_namespace : null
     */

    private int id;
    private String name;
    private String description;
    private String default_branch;
    private OwnerBean owner;
    private boolean publicX;
    private String path;
    private String path_with_namespace;
    private boolean issues_enabled;
    private boolean pull_requests_enabled;
    private boolean wiki_enabled;
    private String created_at;
    private NamespaceBean namespace;
    private String last_push_at;
    private Object parent_id;
    private boolean fork;
    private int forks_count;
    private int stars_count;
    private int watches_count;
    private String language;
    private Object paas;
    private Object stared;
    private Object watched;
    private Object relation;
    private int recomm;
    private Object parent_path_with_namespace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public OwnerBean getOwner() {
        return owner;
    }

    public void setOwner(OwnerBean owner) {
        this.owner = owner;
    }

    public boolean isPublicX() {
        return publicX;
    }

    public void setPublicX(boolean publicX) {
        this.publicX = publicX;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_with_namespace() {
        return path_with_namespace;
    }

    public void setPath_with_namespace(String path_with_namespace) {
        this.path_with_namespace = path_with_namespace;
    }

    public boolean isIssues_enabled() {
        return issues_enabled;
    }

    public void setIssues_enabled(boolean issues_enabled) {
        this.issues_enabled = issues_enabled;
    }

    public boolean isPull_requests_enabled() {
        return pull_requests_enabled;
    }

    public void setPull_requests_enabled(boolean pull_requests_enabled) {
        this.pull_requests_enabled = pull_requests_enabled;
    }

    public boolean isWiki_enabled() {
        return wiki_enabled;
    }

    public void setWiki_enabled(boolean wiki_enabled) {
        this.wiki_enabled = wiki_enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public NamespaceBean getNamespace() {
        return namespace;
    }

    public void setNamespace(NamespaceBean namespace) {
        this.namespace = namespace;
    }

    public String getLast_push_at() {
        return last_push_at;
    }

    public void setLast_push_at(String last_push_at) {
        this.last_push_at = last_push_at;
    }

    public Object getParent_id() {
        return parent_id;
    }

    public void setParent_id(Object parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStars_count() {
        return stars_count;
    }

    public void setStars_count(int stars_count) {
        this.stars_count = stars_count;
    }

    public int getWatches_count() {
        return watches_count;
    }

    public void setWatches_count(int watches_count) {
        this.watches_count = watches_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getPaas() {
        return paas;
    }

    public void setPaas(Object paas) {
        this.paas = paas;
    }

    public Object getStared() {
        return stared;
    }

    public void setStared(Object stared) {
        this.stared = stared;
    }

    public Object getWatched() {
        return watched;
    }

    public void setWatched(Object watched) {
        this.watched = watched;
    }

    public Object getRelation() {
        return relation;
    }

    public void setRelation(Object relation) {
        this.relation = relation;
    }

    public int getRecomm() {
        return recomm;
    }

    public void setRecomm(int recomm) {
        this.recomm = recomm;
    }

    public Object getParent_path_with_namespace() {
        return parent_path_with_namespace;
    }

    public void setParent_path_with_namespace(Object parent_path_with_namespace) {
        this.parent_path_with_namespace = parent_path_with_namespace;
    }

    public static class OwnerBean implements Serializable{
        /**
         * id : 372841
         * username : lishilei0523
         * email : lishilei0523@163.com
         * name : 黄昏MMM
         * state : active
         * created_at : 2015-04-24T09:32:19+08:00
         * portrait : uploads/41/372841_lishilei0523.png?1439795747
         * new_portrait : http://git.oschina.net/uploads/41/372841_lishilei0523.png?1439795747
         */

        private int id;
        private String username;
        private String email;
        private String name;
        private String state;
        private String created_at;
        private String portrait;
        private String new_portrait;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getNew_portrait() {
            return new_portrait;
        }

        public void setNew_portrait(String new_portrait) {
            this.new_portrait = new_portrait;
        }
    }

    public static class NamespaceBean implements Serializable{
        /**
         * address : 192.168.2.55
         * avatar : null
         * created_at : 2015-04-24T09:32:19+08:00
         * description :
         * email : null
         * enterprise_id : 0
         * id : 365801
         * level : 0
         * location : null
         * name : lishilei0523
         * owner_id : 372841
         * path : lishilei0523
         * public : null
         * updated_at : 2016-06-27T01:14:59+08:00
         * url : null
         */

        private String address;
        private Object avatar;
        private String created_at;
        private String description;
        private Object email;
        private int enterprise_id;
        private int id;
        private int level;
        private Object location;
        private String name;
        private int owner_id;
        private String path;
        private Object publicX;
        private String updated_at;
        private Object url;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public int getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(int enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Object getPublicX() {
            return publicX;
        }

        public void setPublicX(Object publicX) {
            this.publicX = publicX;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }
}
