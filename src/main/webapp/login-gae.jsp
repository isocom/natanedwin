<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringContext"%>
<%@page import="com.appspot.natanedwin.service.user.UserCredentials"%>
<%@page import="com.appspot.natanedwin.service.user.UserManager"%>
<%
    UserManager userManager = SpringContext.INSTANCE.getBean(UserManager.class);
    UserCredentials userCredentials = userManager.discoverCredentials(request);
    boolean alreadyLoggedIn = (userCredentials.isLoggedIn() && userCredentials.isGoogleCredentials());

    if (userCredentials.isLoggedIn()) {
        request.getSession().setAttribute("userCredentials", userCredentials);
    } else {
        request.removeAttribute("userCredentials");
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Logowanie z kontem Google</title>

        <!-- The stylesheets -->
        <link rel="stylesheet" href="assets/css/login-gae.css" />
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700" />

        <!--[if lt IE 9]>
          <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    </head>
    <body>
        <c:set var="X" value="<%=alreadyLoggedIn%>" />
        <h1>Logowanie z kontem <img src="assets/img/google_logo_41.png" alt="Google logo" style="vertical-align: bottom;" /></h1>
        <div id="main">
            <c:if test="${X}">
                <div id="avatar" style="no-background-image:url(test.jpg)"></div>
                <p class="greeting">Witaj, <b><%= userCredentials.getPrincipalName()%></b></p>
                <p class="register_info">Kliknij <a href="/app/">ten link aby uruchomić</a> aplikację <b>ISOCOM</b>.</p>
                <a href="<%= userCredentials.getGoogleAccountLogout()%>" class="logoutButton">Wyloguj</a>
            </c:if>
            <c:if test="${!X}">
                <a href="<%= userCredentials.getGoogleAccountLogin()%>" class="googleLoginButton">Sign in with Google</a>
            </c:if>
        </div>

        <p class="note">Autor aplikacji: <a href="http://bart.prokop.name/">Bartłomiej Prokop</a>.</p>
        <footer>
            <h2><i>Aplikacja:</i> natanedwin.appspot.com</h2>
            <a class="tzine" href="https://support.google.com/accounts/answer/112802?hl=pl&ref_topic=2665423">Informacja o użyciu <i>konta <b>Google</b></i> do logowania.</a>
        </footer>
    </body>
</html>