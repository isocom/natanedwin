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
%>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Przykładowa integracja</title>
</head>
<body>
    <p>
        Integracja jest bardzo prosta. Należy dostarczyć kod strony WWW, który zostanie wgrany do katalogu:
        http://natanedwin.appspot.com/branding/TWOJA_NAZWA/*.
    </p>
    <p>
        Przykładowe integracje:
    </p>
    <ul>
        <li>Źródła tej strony - 101.zip</li>
        <li>Integracja ze stroną http://www.isocom.eu/ - isocom.zip</li>
    </ul>
    <p>
        Na stronie należy zawrzeć formularz, który powinien wyglądać jak ten poniżej. Zwróć uwagę, że nie jest użyty atrybut action.
    </p>
    <form method="post">
            <label for="login">Identyfikator:</label>
            <input type="text" name="login" id="login">

            <label for="password">Hasło:</label>
            <input type="password" name="password" id="password">

            <button type="submit">Login</button>
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
            out.println("<p>Alternatywnie użyj uprawnień konta<br/><img src=\"index/google_logo_41.png\" alt=\"Google logo\"/></p>");
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
