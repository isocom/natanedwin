<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringInformation"%>
<%@page import="com.appspot.natanedwin.service.spring.SpringContext"%>
<%@page import="com.appspot.natanedwin.service.user.UserCredentials"%>
<%@page import="com.appspot.natanedwin.service.user.UserManager"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <title>Aplikacja ISOCOM</title>
        <meta name="keywords" content="RPC, KD, kontrola dostępu, rejestracja czasu pracy, ISOCOM, RFID" />
        <meta name="description" content="Realizacjia kontroli dostępu i rejestracji czasu pracy w oparciu o chmurę usług." />
        <link href="styles.css" rel="stylesheet" type="text/css" media="screen" />
        <link rel="stylesheet" href="nivo-slider.css" type="text/css" media="screen" />
    </head>
    <%
        UserManager userManager = SpringContext.INSTANCE.getBean(UserManager.class);
        UserCredentials userCredentials = userManager.discoverCredentials(request);
        boolean isLoggedIn = userCredentials.isLoggedIn();

        // set credential to current user session
        if (isLoggedIn) {
            request.getSession().setAttribute("userCredentials", userCredentials);
        } else {
            request.removeAttribute("userCredentials");
        }

        SpringInformation springInformation = SpringContext.INSTANCE.getBean(SpringInformation.class);
    %>
    <body>
        <div id="bg2"></div>
        <div id="wrapper1">
            <div id="menu">
                <ul>
                    <li><a href="http://www.isocom.eu/index.html">ISOCOM</a></li>
                    <li><a href="#" class="active">Aplikacja</a></li>
                    <%
                        if (!userCredentials.isGoogleCredentials()) {
                            String loginUrl = userCredentials.getGoogleAccountLogin();
                            out.println("<li><a href=\"" + loginUrl + "\">Zaloguj&nbsp;z</a></li>");
                            out.println("<li><a href=\"" + loginUrl + "\"><img style=\"vertical-align: middle;\" src=\"images/google_logo_41.png\"/></a></li>");
                        }
                        if (userCredentials.isGoogleCredentials()) {
                            out.println("<li><a href=\"/app/\">Uruchom</a></li>");
                            out.println("<li><a href=\"" + userCredentials.getGoogleAccountLogout() + "\">Wyloguj</a></li>");
                        }
                    %>
                    <li><a href="http://java-in-cloud.blogspot.com/">Nasz Blog</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div id="logo">
                <%
                    if (isLoggedIn) {
                        out.println("<h1><a href=\"/app/\">Witaj " + userCredentials.getFriendlyUserName() + ",</a></h1>");
                        out.println("<a href=\"/app/\"><small>Wejdź do najfajniejszej aplikacji kontroli dostępu.</small></a>");
                    } else {
                        out.println("<h1><a href=\"#\">Witaj, nie zostałeś rozpoznany jako użytkownik konta Google</a></h1>");
                        out.println("<a href=\"#\"><small>Zaloguj się z użyciem konta Google lub przy użyciu twojego indywidualnego loginu i hasła.</small></a>");
                    }
                %>
            </div>

            <div id="content_bg_top"></div>
            <div id="content_box">
                <div id="header">
                    <div id="wrapper">
                        <div id="slider-wrapper">        
                            <div id="slider" class="nivoSlider">
                                <img src="images/header.jpg" alt="" />
                                <img src="images/header2.jpg" alt=""/>
                                <img src="images/header3.jpg" alt="" />
                                <img src="images/header4.jpg" alt="" />
                            </div>        
                        </div>

                        <script type="text/javascript" src="lib/jquery-1.4.3.min.js"></script>
                        <script type="text/javascript" src="lib/jquery.nivo.slider.pack.js"></script>
                        <script type="text/javascript">
                            $(window).load(function() {
                                $('#slider').nivoSlider();
                            });
                        </script>
                    </div>

                </div>
                <div id="column_box">
                    <div id="column1">
                        <h2>Logowanie</h2>
                        <img src="images/img1.png" alt="" title="" style="float: left; padding-right: 20px;"/>
                        <%
                            out.println("<form method=\"post\">");
                            out.println("Użytkownik: <input type=\"text\" name=\"login\">");
                            out.println("Hasło: <input type=\"password\" name=\"password\">");
                            out.println("<input type=\"submit\" value=\"Zaloguj do aplikacji\">");
                            out.println("</form>");
                        %>
                        <p><a href="#">Logowanie tradycyjne</a> 
                            Zostaniesz zalogowany przy uzyciu indywidualnej nazwy użytkownika i hasła
                        </p>
                    </div>
                    <div id="column2">
                        <h2>Company News</h2>
                        <img src="images/img2.png" alt="" title="" style="float: left; padding-right: 20px;"/>
                        <p><a href="#">Donec interdum dignissim risus et vestibulum.</a>   
                            Donec eget nisi id magna tempor laoreet. Etiam egesa </p>
                    </div>
                    <div id="column3">
                        <h2>Potrzebna Pomoc?</h2>
                        <img src="images/img3.png" alt="" title="" style="float: left; padding-right: 20px;"/>
                        <p><a href="#">Skorzystaj z dokumentacji.</a>  
                            Znajdziesz tam wiele informacji dotyczących samej aplikacji, jak też sposobu jej użytkowania.</p>
                    </div>
                    <div class="clear"></div>
                </div>
                <div id="download_box">
                    <div id="download_left"><p><a href="#">Cras hendrerit est nec libero ornare sagittis. Donec interdum dignissim risus et vestibulum.</a><br /><br />
                            A mauris suscipit porta aliquet turpis interdum. Donec eget nisi id magna tempor laoreet. Etiam egestas pulvinar nunc, eu dictum est sagittis vel. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Integer turpis est, vulputate at cursus a, suscipit quis diam. Cras condimentum metus ut neque lobortis </p>	
                    </div>
                    <div id="download_right"><a href="#">Download</a></div>

                </div>
                <div id="footer_top">
                    <div id="footer_column1">
                        <h3>Recent posts</h3>
                        <div class="footer_text">
                            <p><a href="#">Suspendisse rutrum interdum lacinia.</a>
                                Suspendisse tempus aliquet elit sit amet pellentesque. Donec iaculis pulvinar mauris, ac vulputate justo pretium quis. </p>
                            <br />
                            <p><a href="#">Quisque luctus, mi ornare malesuada</a>
                                Suspendisse tempus aliquet elit sit amet pellentesque. Donec iaculis pulvinar </p>
                        </div>
                    </div>
                    <div id="footer_column2">
                        <h3>Share with Others</h3>
                        <div class="footer_text">
                            <div class="foot_pad">
                                <div class="link1"><a href="#">Subscribe to Blog</a></div>
                                <div class="link2"><a href="#">Be a fan on Facebook</a></div>
                                <div class="link3"><a href="#">RSS Feed</a></div>
                                <div class="link4"><a href="#">Follow us on Twitter</a></div>
                            </div>
                        </div>
                    </div>
                    <div id="footer_column3">
                        <h3>Informacja o applikacji</h3>
                        <div class="footer_text">
                            <div class="foot_pad">
                                <ul class="ls">
                                    <li><a href="#">Lokalizacja: <%=request.getHeader("X-AppEngine-City")%>, <%=request.getHeader("X-AppEngine-Country")%></a></li>
                                    <li><a href="#">Wersja: <%=springInformation.getVersion()%></a></li>
                                    <li><a href="/info/">Informacja o serwerze</a></li>
                                    <li><a href="http://www.isocom.eu/m2-sites/natanedwin/">Dokumentacja techniczna</a></li>
                                    <li><a href="https://appengine.google.com/">Panel kontrolny aplikacji</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div id="content_bg_bot"></div>
            <div id="footer_bot">
                <p>Copyright  2011. <!-- Do not remove -->Designed by <a href="http://www.myfreecsstemplates.com/" title="Free CSS Templates">Free CSS Templates</a><!-- end --></p>
                <p><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a> | <a href="http://validator.w3.org/check/referer" title="This page validates as XHTML 1.0 Transitional"><abbr title="eXtensible HyperText Markup Language">XHTML</abbr></a> | <a href="http://jigsaw.w3.org/css-validator/check/referer" title="This page validates as CSS"><abbr title="Cascading Style Sheets">CSS</abbr></a></p>
            </div>
        </div>
    </body>
</html>
