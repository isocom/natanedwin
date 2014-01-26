package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.service.user.UserCredentials;
import com.appspot.natanedwin.service.user.UserManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class UserAccountLoginServlet extends HttpServlet {

    @Autowired
    private UserManager userManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCredentials userCredentials = userManager.discoverCredentials(req);
        userCredentials.isLoggedIn();

        // set credential to current user session
        if (userCredentials.isLoggedIn()) {
            req.getSession().setAttribute("userCredentials", userCredentials);
        } else {
            req.removeAttribute("userCredentials");
        }

        if (userCredentials.isLoggedIn() && !userCredentials.isGoogleCredentials()) {
            resp.sendRedirect("/app/");
            return;
        }

        resp.sendRedirect("/");
    }

}
