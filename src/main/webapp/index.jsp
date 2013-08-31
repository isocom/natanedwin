<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringInformation"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringContext"%>
<%@page import="com.appspot.natanedwin.service.user.UserCredentials"%>
<%@page import="com.appspot.natanedwin.service.user.UserManager"%>

<%
    UserManager userManager = SpringContext.INSTANCE.getBean(UserManager.class);
    UserCredentials userCredentials = userManager.discoverCredentials(request);
    userCredentials.isLoggedIn();

    // set credential to current user session
    if (userCredentials.isLoggedIn()) {
        request.getSession().setAttribute("userCredentials", userCredentials);
    } else {
        request.removeAttribute("userCredentials");
    }

    if (userCredentials.isLoggedIn() && !userCredentials.isGoogleCredentials()) {
        response.sendRedirect("/app/");
        return;
    }

    SpringInformation springInformation = SpringContext.INSTANCE.getBean(SpringInformation.class);
%>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="RPC, KD, kontrola dostępu, rejestracja czasu pracy, ISOCOM, RFID" />
    <meta name="description" content="Realizacjia kontroli dostępu i rejestracji czasu pracy w oparciu o chmurę usług." />
    <title>Logowanie do Aplikacji</title>
    <link rel="stylesheet" href="index/style.css">
</head>
<body>
    <h1 class="naglowek">.</h1>

    <form method="post" class="login">
        <p>
            <label for="login">Identyfikator:</label>
            <input type="text" name="login" id="login" value="identyfikator">
        </p>

        <p>
            <label for="password">Hasło:</label>
            <input type="password" name="password" id="password" value="4815162342">
        </p>

        <p class="login-submit">
            <button type="submit" class="login-button">Login</button>
        </p>

        <p class="forgot-password"><a href="#">Zapomniałeś hasła?</a></p>
        <p class="forgot-password"><a href="https://docs.google.com/document/d/1IHsfjAaVdmcjpvM4o-j6gSXZiTT8A0485p7GEVLiMuY/edit?usp=sharing" target="_blank">Instrukcja obsługi</a></p>
    </form>

    <%
        if (userCredentials.isLoggedIn()) {
            out.println("<section class=\"about\">");
            out.println("<p>Uwierzytelniono przy użyciu konta<br/><img src=\"index/google_logo_41.png\" /><br/>Zostałęś rozpoznany jako ");
            out.println(userCredentials.getFriendlyUserName());
            out.println("</p>");
            out.println("<p class=\"about-links\">");
            out.println("<a href=\"/app/\" target=\"_parent\">Uruchom</a>");
            out.println("<a href=\"" + userCredentials.getGoogleAccountLogout() + "\" target=\"_parent\">Wyloguj</a></p>");
            out.println("<p class=\"about-author\">");
            out.println("&copy; 2012&ndash;2013 <a href=\"http://bart.prokop.name/\" target=\"_blank\">Bartłomiej Piotr Prokop</a> - <a href=\"http://pl.linkedin.com/in/bartprokop/\" target=\"_blank\">LinkedIn</a></p>");
            out.println("</section>");
        } else {
            out.println("<section class=\"about\">");
            out.println("<p>Alternatywnie użyj uprawnień konta<br/><img src=\"index/google_logo_41.png\" /></p>");
            out.println("<p class=\"about-links\">");
            out.println("<a href=\"" + userCredentials.getGoogleAccountLogin() + "\" target=\"_parent\">Zaloguj</a>");
            out.println("<a href=\"https://support.google.com/accounts/answer/112802?hl=pl&ref_topic=2665423\" target=\"_blank\">Informacje</a></p>");
            out.println("<p class=\"about-author\">");
            out.println("&copy; 2012&ndash;2013 <a href=\"http://www.isocom.eu/\" target=\"_blank\">Wszystkie prawa zastrzeżone.</a> - <a href=\"http://pl.linkedin.com/in/bartprokop/\" target=\"_blank\">LinkedIn</a></p>");
            out.println("</section>");
        }
    %>    
</body>
</html>
