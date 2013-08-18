package org.mopo.processor.admin.sidou;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.Latkes;
import org.b3log.latke.Keys.Server;
import org.b3log.latke.model.User;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.servlet.renderer.freemarker.FreeMarkerRenderer;
import org.b3log.latke.util.Sessions;
import org.b3log.latke.util.Strings;
import org.json.JSONObject;
import org.mopo.model.Common;

import com.google.apphosting.utils.remoteapi.RemoteApiPb.Request;


/**
 * Admin control index.
 * 
 * @author zezhou
 * 
 */
@RequestProcessor
public final class UserProcessor {

    private static final Logger LOGGER = Logger.getLogger(UserProcessor.class
            .getName());

    @RequestProcessing(value = {"/login", "/**/ant/*/path" }, method = HTTPRequestMethod.GET)
    public void login(final HTTPRequestContext context) {
        LOGGER.entering(UserProcessor.class.getSimpleName(), "index");
        

        /*final AbstractFreeMarkerRenderer render = new FreeMarkerRenderer();
        context.setRenderer(render);

        render.setTemplateName("index.ftl");
        final Map<String, Object> dataModel = render.getDataModel();
        dataModel.put("staticServePath", "Hello, Latke!");
        // dataModel.put("staticServePath", "");
        // dataModel.put(Server.STATIC_SERVE_PATH,Latkes.getStaticPath());
        LOGGER.info(Server.STATIC_SERVE_PATH + ": " + Latkes.getStaticPath());
        LOGGER.exiting(LoginProcessor.class.getSimpleName(), "index");*/
    }
    
    /**
     * Tries to login with cookie.
     *
     * @param request the specified request
     * @param response the specified response
     */
    public static void tryLogInWithCookie(final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();

        if (null == cookies || 0 == cookies.length) {
            return;
        }

        try {
            for (int i = 0; i < cookies.length; i++) {
                final Cookie cookie = cookies[i];

                if (!"mopo-app".equals(cookie.getName())) {
                    continue;
                }

                final JSONObject cookieJSONObject = new JSONObject(cookie.getValue());
                String username = cookieJSONObject.getString(User.USER_NAME);
                String password = cookieJSONObject.getString(User.USER_PASSWORD);
                if(username == Common.USERNAME && password == Common.PASSWORD) {
                    
                }
               
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, "Parses cookie failed, clears the cookie[name=mopo-app]", e);

            final Cookie cookie = new Cookie("mopo-app", null);

            cookie.setMaxAge(0);
            cookie.setPath("/");

            response.addCookie(cookie);
        }
    }
    
}
