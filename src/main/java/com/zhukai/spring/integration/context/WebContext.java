package com.zhukai.spring.integration.context;

import com.zhukai.spring.integration.commons.Request;
import com.zhukai.spring.integration.commons.Session;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhukai on 17-1-12.
 */
public class WebContext {

    public static final String JSESSIONID = "JSESSIONID";

    private static Map<String, Method> webMethods;

    private static ThreadLocal<Request> request = new ThreadLocal();

    private static Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    public static String getSessionId() {
        if (getRequest() == null) {
            return null;
        }
        return getRequest().getCookie(JSESSIONID);
    }

    public static Request getRequest() {
        return request.get();
    }

    public static void setRequest(Request request) {
        WebContext.request.set(request);
    }

    public static Session getSession() {
        if (sessions.get(getSessionId()) == null) {
            sessions.put(getSessionId(), new Session(getSessionId()));
        }
        return sessions.get(getSessionId());
    }

    public static Map<String, Method> getWebMethods() {
        return webMethods;
    }

    public static void setWebMethods(Map<String, Method> webMethods) {
        WebContext.webMethods = webMethods;
    }

    public static void clear() {
        request.remove();
    }

}
