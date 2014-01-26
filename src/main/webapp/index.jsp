<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringContext"%>
<%@page import="com.appspot.natanedwin.service.user.UserCredentials"%>
<%@page import="com.appspot.natanedwin.service.user.UserManager"%>
<%
    UserManager userManager = SpringContext.INSTANCE.getBean(UserManager.class);
    UserCredentials userCredentials = userManager.discoverCredentials(request);
    String h1, t1, h2, t2;
    if (userCredentials.isLoggedIn() && userCredentials.isGoogleCredentials()) {
        h1 = userCredentials.getGoogleAccountLogout();
        t1 = "Wyloguj się";
        h2 = "/app/";
        t2 = "Uruchom jako " + userCredentials.getPrincipalName();
    } else {
        h1 = userCredentials.getGoogleAccountLogin();
        t1 = "Zaloguj się do Google";
        h2 = "https://support.google.com/accounts/answer/112802?hl=pl&ref_topic=2665423";
        t2 = "Informacja";
    }

    // set credential to current user session
    if (userCredentials.isLoggedIn()) {
        request.getSession().setAttribute("userCredentials", userCredentials);
    } else {
        request.removeAttribute("userCredentials");
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Logowanie z kontem Google</title>
    </head>
    <body>
    <center>
        <img src="google_logo_41.png" alt="Google logo" /><br>
        <a href="<%=h1%>"><%=t1%></a><br>
        <a href="<%=h2%>"><%=t2%></a><br>
    </center>
</body>
</html>