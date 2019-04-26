package com.jason.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static void createSession(HttpServletRequest request, boolean flag, int inactiveIntervalTime, String key, Object obj){
        HttpSession session = request.getSession(flag);
        if(session != null){
            session.setMaxInactiveInterval(inactiveIntervalTime);
            session.setAttribute(key, obj);
        }
    }

    public static <T> T getSessionAttribute(HttpServletRequest request, boolean flag, String key, Class<T> T){
        HttpSession session = request.getSession(flag);
        if(session != null){
            return (T) session.getAttribute(key);
        }
        return null;
    }
}
