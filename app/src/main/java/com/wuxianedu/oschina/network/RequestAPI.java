package com.wuxianedu.oschina.network;

/**
 * 请求API
 */
public class RequestAPI {

    //基础URL
    public static final String BASE_URL="http://git.oschina.net/api/v3/";
	
    public static final String FEATURED="projects/featured";  //推荐项目
    public static final String POPULAR="projects/popular";    //热门项目
    public static final String LATEST="projects/latest";      //最近更新
    public static final String DYNAMIC = "events/user/";      //动态
    public static final String LANGUAGE ="projects/languages";  //语言
    public static final String SHAKE = "projects/random?luck=1"; //摇一摇
    public static final String LOGIN = "session";    //登录

    //http://git.oschina.net/api/v3/events/user/123123
    //http://git.oschina.net/api/v3/user/123/projects
    //http://git.oschina.net/api/v3/user/123/stared_projects
    //http://git.oschina.net/api/v3/user/123/watched_projects

    /**
     * 拼接完整URL
     * @param relativeUrl
     * @return
     */
    public static String getAbsoluteUrl(String relativeUrl) {
        return RequestAPI.BASE_URL + relativeUrl;
    }

    public static String getReadmeURL(int projectId) {
        return "projects/"+projectId + "/readme";
    }

}
