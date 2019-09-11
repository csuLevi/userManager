package com.etekcity.cloud.constant;

/**
 * 1.请求访问路径的常量
 * 2.请求格式的常量
 *
 * @author Levi
 * @date 2019/7/25/025
 * @since 0.0.1
 */
public class MethodConstant {
    /**
     * 请求的路径
     */
    public static final String REQUEST_PATH = "/user/v1";
    public static final String REGISTER_METHOD = "register";
    public static final String LOGIN_METHOD = "login";
    public static final String LOGOUT_METHOD = "logout";
    public static final String GET_USER_INFO_METHOD = "getUserInfo";
    public static final String UPDATE_USER_INFO_METHOD = "updateUserInfo";
    public static final String UPDATE_PASSWORD_METHOD = "updatePassword";
    public static final String AUTHORIZATION_REQUEST_HEADER = "X-Authorization";

    /**
     * 请求头由2部分组成:｛userId｝｛token｝
     */
    public static final int REQUEST_HEADER_LIST_LENGTH = 2;
}
