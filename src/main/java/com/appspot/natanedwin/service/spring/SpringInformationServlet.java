/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTimeZone;

/**
 *
 * @author prokob01
 */
public class SpringInformationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doIt(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doIt(req, resp);
    }

    protected void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        try {
            out.println("<h1>Information servlet</h1>");

            // Spring Information
            out.println("<table border=1><tr><th colspan=2>Spring Framework Information</th></tr>");
            out.println("<tr><td>ApplicationContext</td><td>" + SpringContext.INSTANCE.getApplicationContext() + "</td></tr>");
            SpringInformation springInformation = SpringContext.INSTANCE.getBean(SpringInformation.class);
            out.println("<tr><td>SpringInformation.getVersion()</td><td>" + springInformation.getApplicationVersion() + "</td></tr>");
            out.println("<tr><td>SpringInformation.getSystemInformation()</td><td><pre>" + springInformation.getSystemInformation() + "</pre></td></tr>");
            out.println("<tr><td>ApplicationContext.getId()</td><td>" + SpringContext.INSTANCE.getApplicationContext().getId() + "</td></tr>");
            out.println("<tr><td>ApplicationContext.getDisplayName()</td><td>" + SpringContext.INSTANCE.getApplicationContext().getDisplayName() + "</td></tr>");
            out.println("<tr><td>ApplicationContext.getStartupDate()</td><td>" + SpringContext.INSTANCE.getApplicationContext().getStartupDate() + "</td></tr>");
            out.println("<tr><td>ApplicationContext.getParent()</td><td>" + SpringContext.INSTANCE.getApplicationContext().getParent() + "</td></tr>");
            out.println("</table><hr>");

            // HTTP HEADERS
            out.println("<table border=1><tr><th colspan=2>Headers passed to App Engine</th></tr>");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String nextElement = headerNames.nextElement();
                out.println("<tr><td>" + nextElement + "</td><td>" + request.getHeader(nextElement) + "</td></tr>");
            }
            out.println("</table><hr>");

            // HTTP Request
            out.println("<table border=1><tr><th colspan=2>HTTP Request</th></tr>");
            out.println("<tr><td>request.getContextPath</td><td>" + request.getContextPath() + "</td></tr>");
            out.println("<tr><td>request.getLocalAddr</td><td>" + request.getLocalAddr() + "</td></tr>");
            out.println("<tr><td>request.getLocalName</td><td>" + request.getLocalName() + "</td></tr>");
            out.println("<tr><td>request.getLocalPort</td><td>" + request.getLocalPort() + "</td></tr>");
            out.println("<tr><td>request.getPathInfo</td><td>" + request.getPathInfo() + "</td></tr>");
            out.println("<tr><td>request.getPathTranslated</td><td>" + request.getPathTranslated() + "</td></tr>");
            out.println("<tr><td>request.getProtocol</td><td>" + request.getProtocol() + "</td></tr>");
            out.println("<tr><td>request.getRemoteAddr</td><td>" + request.getRemoteAddr() + "</td></tr>");
            out.println("<tr><td>request.getRemoteHost</td><td>" + request.getRemoteHost() + "</td></tr>");
            out.println("<tr><td>request.getRemotePort</td><td>" + request.getRemotePort() + "</td></tr>");
            out.println("<tr><td>request.getRemoteUser</td><td>" + request.getRemoteUser() + "</td></tr>");
            out.println("<tr><td>request.getRequestURI</td><td>" + request.getRequestURI() + "</td></tr>");
            out.println("<tr><td>request.getRequestedSessionId</td><td>" + request.getRequestedSessionId() + "</td></tr>");
            out.println("<tr><td>request.getScheme</td><td>" + request.getScheme() + "</td></tr>");
            out.println("<tr><td>request.getServerName</td><td>" + request.getServerName() + "</td></tr>");
            out.println("<tr><td>request.getServerPort</td><td>" + request.getServerPort() + "</td></tr>");
            out.println("<tr><td>request.getServletPath</td><td>" + request.getServletPath() + "</td></tr>");
            out.println("</table><hr>");

            // HTTP Session
            HttpSession session = request.getSession(false);
            out.println("<table border=1><tr><th colspan=2>HTTP Session</th></tr>");
            out.println("<tr><td>getSession()</td><td>" + session + "</td></tr>");
            if (session != null) {
                out.println("<tr><td>getSession().getId()</td><td>" + session.getId() + "</td></tr>");
                out.println("<tr><td>getSession().getCreationTime()</td><td>" + session.getCreationTime() + "</td></tr>");
                out.println("<tr><td>getSession().getLastAccessedTime()</td><td>" + session.getLastAccessedTime() + "</td></tr>");
                out.println("<tr><td>getSession().getMaxInactiveInterval()</td><td>" + session.getMaxInactiveInterval() + "</td></tr>");
                out.println("<tr><td>getSession().getServletContext()</td><td>" + session.getServletContext() + "</td></tr>");
                out.println("<tr><td>getSession().isNew()</td><td>" + session.isNew() + "</td></tr>");
                Enumeration<String> attributeNames = session.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    String nextElement = attributeNames.nextElement();
                    out.println("<tr><td>" + nextElement + "</td><td>" + session.getAttribute(nextElement) + "</td></tr>");
                }
            }
            out.println("</table><hr>");

            // Request parameters
            out.println("<table border=1><tr><th colspan=2>The request parameters</th></tr>");
            @SuppressWarnings("rawtypes")
            Map parameterMap = request.getParameterMap();
            for (Object key : parameterMap.keySet()) {
                out.print("<tr><td>" + key + "</td><td>");
                String[] values = (String[]) parameterMap.get(key);
                for (int i = 0; i < values.length; i++) {
                    out.print(values[i]);
                    if (i < values.length - 1) {
                        out.print(", ");
                    }
                }
                out.println("</td></tr>");
            }
            out.println("</table><hr>");

            // HTTP Request Content
            out.println("<b>The request content</b><br><pre>");
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
            out.println("</pre><hr>");

            // JAVA ENVIROMENT
            out.println("<table border=1><tr><th colspan=2>JAVA system properties</th></tr>");
            out.println("<tr><td>java.version</td><td>" + System.getProperty("java.version") + "</td></tr>");
            out.println("<tr><td>java.vendor</td><td>" + System.getProperty("java.vendor") + "</td></tr>");
            out.println("<tr><td>java.vendor.url</td><td>" + System.getProperty("java.vendor.url") + "</td></tr>");
            out.println("<tr><td>java.home</td><td>" + System.getProperty("java.home") + "</td></tr>");
            out.println("<tr><td>java.vm.specification.version</td><td>" + System.getProperty("java.vm.specification.version") + "</td></tr>");
            out.println("<tr><td>java.vm.specification.vendor</td><td>" + System.getProperty("java.vm.specification.vendor") + "</td></tr>");
            out.println("<tr><td>java.vm.specification.name</td><td>" + System.getProperty("java.vm.specification.name") + "</td></tr>");
            out.println("<tr><td>java.vm.version</td><td>" + System.getProperty("java.vm.version") + "</td></tr>");
            out.println("<tr><td>java.vm.vendor</td><td>" + System.getProperty("java.vm.vendor") + "</td></tr>");
            out.println("<tr><td>java.vm.name</td><td>" + System.getProperty("java.vm.name") + "</td></tr>");
            out.println("<tr><td>java.specification.version</td><td>" + System.getProperty("java.specification.version") + "</td></tr>");
            out.println("<tr><td>java.specification.vendor</td><td>" + System.getProperty("java.specification.vendor") + "</td></tr>");
            out.println("<tr><td>java.specification.name</td><td>" + System.getProperty("java.specification.name") + "</td></tr>");
            out.println("<tr><td>java.class.version</td><td>" + System.getProperty("java.class.version") + "</td></tr>");
            out.println("<tr><td>java.class.path</td><td>" + System.getProperty("java.class.path") + "</td></tr>");
            out.println("<tr><td>java.library.path</td><td>" + System.getProperty("java.library.path") + "</td></tr>");
            out.println("<tr><td>java.io.tmpdid</td><td>" + System.getProperty("java.io.tmpdir") + "</td></tr>");
            out.println("<tr><td>java.compiler</td><td>" + System.getProperty("java.compiler") + "</td></tr>");
            out.println("<tr><td>java.ext.dirs</td><td>" + System.getProperty("java.ext.dirs") + "</td></tr>");
            out.println("<tr><td>os.name</td><td>" + System.getProperty("os.name") + "</td></tr>");
            out.println("<tr><td>os.arch</td><td>" + System.getProperty("os.arch") + "</td></tr>");
            out.println("<tr><td>os.version</td><td>" + System.getProperty("os.version") + "</td></tr>");
            out.println("<tr><td>file.separator</td><td>" + System.getProperty("file.separator") + "</td></tr>");
            out.println("<tr><td>path.separator</td><td>" + System.getProperty("path.separator") + "</td></tr>");
            out.println("<tr><td>line.separator</td><td>" + System.getProperty("line.separator") + "</td></tr>");
            out.println("<tr><td>user.dir</td><td>" + System.getProperty("user.dir") + "</td></tr>");
            out.println("<tr><td>user.home</td><td>" + System.getProperty("user.home") + "</td></tr>");
            out.println("<tr><td>user.name</td><td>" + System.getProperty("user.name") + "</td></tr>");
            out.println("</table><hr>");

            out.println("<h2>DateTimeZone.getAvailableIDs()</h2>");
            for (String s : DateTimeZone.getAvailableIDs()) {
                out.println(s + ", ");
            }

            out.println("<h2>Locale.getAvailableLocales()</h2>");
            for (Locale l : Locale.getAvailableLocales()) {
                out.println(l + ", ");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.println("</body></html>");
            out.flush();
            out.close();
        }
    }
}
