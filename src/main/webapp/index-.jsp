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
        t2 = "Uruchom jako "+userCredentials.getPrincipalName();
    } else {
        h1 = userCredentials.getGoogleAccountLogin();
        t1 = "Zaloguj się do Google";
        h2 = "https://support.google.com/accounts/answer/112802?hl=pl&ref_topic=2665423";
        t2 = "Informacja";
    }
%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html lang="en"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <title>Paper Stack</title>
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>
    <body>
        <div class="container">
            <section id="content">
                <form action="/tools/UserAccountLoginServlet" method="post">
                    <h1>Logowanie</h1>
                    <div>
                        <input type="text" placeholder="Nazwa użytkownika" required="" id="username" />
                    </div>
                    <div>
                        <input type="password" placeholder="Hasło" required="" id="password" />
                    </div>
                    <div>
                        <input type="submit" value="Zaloguj" />
                        <a href="<%=h1%>"><%=t1%></a>
                        <a href="<%=h2%>"><%=t2%></a>
                    </div>
                </form><!-- form -->
                <div class="button">
                    <a href="https://docs.google.com/document/d/1IHsfjAaVdmcjpvM4o-j6gSXZiTT8A0485p7GEVLiMuY/export?format=pdf">Pobierz instrukcję</a>
                </div><!-- button -->
            </section><!-- content -->
        </div><!-- container -->
    </body>
</html>