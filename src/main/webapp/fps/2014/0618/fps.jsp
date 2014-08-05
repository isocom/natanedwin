<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wydruk paragonu</title>
    </head>
    <body>
        <div>
            <applet code="applet.Applet" width="400" height="180" archive="fps-applet-1.0.jar">
                <param name="api-key" value="<%= request.getParameter("api-key") %>" />
                <param name="documentId" value="<%= request.getParameter("documentId") %>" />
            </applet>
        </div>
    </body>
</html>
