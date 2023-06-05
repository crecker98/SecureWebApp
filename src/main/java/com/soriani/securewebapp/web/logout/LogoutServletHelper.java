package com.soriani.securewebapp.web.logout;

import com.soriani.securewebapp.dao.cookie.CookieDao;
import com.soriani.securewebapp.utility.ApplicationException;
import com.soriani.securewebapp.web.login.GestoreSessioneLogin;
import com.soriani.securewebapp.web.login.LoginServletHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author christiansoriani on 22/10/22
 * @project SecureWebApp
 */
public final class LogoutServletHelper {

    private static final String COOKIE_UUID = "UUID";

    private static final LogoutServletHelper instance = new LogoutServletHelper();

    private static final String COOKIE_ERASE_VALUE = "";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_ZERO_DURATION = -1;

    private LogoutServletHelper() {

    }

    static LogoutServletHelper getInstance() {
        return instance;
    }

    /**
     * meotodo che effettua la cancellazione del cookie e l'invalidazione della sessione al momento del logout
     * @param request
     * @throws ApplicationException
     */
    public void logoutUtente(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {


        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }

            if(cookieMap.get(COOKIE_UUID) != null) {
                for (Cookie cookie : cookies) {
                    cookie.setValue(COOKIE_ERASE_VALUE);
                    cookie.setPath(COOKIE_PATH);
                    cookie.setMaxAge(COOKIE_ZERO_DURATION);
                    response.addCookie(cookie);
                }
                try {
                    CookieDao.getCookieDao().deleteCookieFromUsername(GestoreSessioneLogout.getUtenteLoggato(request).getUsername());

                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new ApplicationException("Errore di connesione");
                } catch (ApplicationException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        HttpSession session = request.getSession(true);
        session.invalidate();

    }

}
