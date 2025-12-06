package com.javaedge.application.context;

import com.javaedge.application.user.UserDTO;

public class UserAppContextHolder {

    private static ThreadLocal<UserDTO> ssoUserThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer> appTypeLocal = new ThreadLocal<>();

    public static final Integer APP_TYPE_WEB = 1;
    public static final Integer APP_TYPE_MGR = 2;

    public static final Long MGR_SUPER_ADMIN_USER_ID = 1L;

    public static UserDTO getCurrentUser() {
        return ssoUserThreadLocal.get();
    }

    public static void setCurrentUser(UserDTO user, Integer appType) {
        ssoUserThreadLocal.set(user);
        appTypeLocal.set(appType);
    }


    public static boolean isWeb() {
        Integer type = appTypeLocal.get();
        if (APP_TYPE_WEB.equals(type)) {
            return true;
        }
        return false;
    }

    public static boolean isMgr() {
        return !isWeb();
    }

    public static boolean isMgrSuperAdmin() {
        UserDTO user = getCurrentUser();
        if (MGR_SUPER_ADMIN_USER_ID.equals(user.getId())) {
            return true;
        } else {
            return false;
        }
    }

    public static void clear() {
        ssoUserThreadLocal.remove();
        appTypeLocal.remove();
    }
}
