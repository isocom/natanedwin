package com.appspot.natanedwin.webapp.fps;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class AppletParamUtil {

    public static String toString(HttpServletRequest request) {
        StringBuilder retVal = new StringBuilder();
        Map parameterMap = request.getParameterMap();
        for (Object key : parameterMap.keySet()) {
            retVal.append("<param name=\"");
            retVal.append(key);
            retVal.append("\" value=\"");
            String[] values = (String[]) parameterMap.get(key);
            retVal.append(values[0]);
            retVal.append("\" />\n");
        }
        return retVal.toString();
    }
}
