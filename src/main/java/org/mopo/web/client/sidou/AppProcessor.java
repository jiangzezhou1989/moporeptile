package org.mopo.web.client.sidou;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.model.Pagination;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.json.JSONObject;
import org.mopo.model.App;
import org.mopo.service.AppQueryService;

/**
 * Handler client request.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午10:34:38
 */
@RequestProcessor
public final class AppProcessor {

    private static final Logger LOGGER = Logger.getLogger(AppProcessor.class
            .getName());

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final AppQueryService appQueryService = AppQueryService
            .getInstance();

    /**
     * Handler client request of app list.
     * 
     * @param context
     *            the specified context
     * @param request
     *            the specified HTTP servlet request
     * @param response
     *            the specified HTTP servlet response
     * @throws Exception
     *             exception
     */
    @RequestProcessing(value = { "/", "/list" }, method = HTTPRequestMethod.POST)
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject ret = new JSONObject();
        JSONObject retJsonObject = new JSONObject();
        // retJsonObject.
        try {
            final JSONObject requestJSONObject = Requests
                    .parseRequestJSONObject(request, response);
            requestJSONObject.put("pageSize", DEFAULT_PAGE_SIZE);
            requestJSONObject.put(App.ORDER_NUM, 0);
            LOGGER.info("requestJSONObject :" + requestJSONObject);

            JSONObject apps = appQueryService.getApps(requestJSONObject);
            retJsonObject.put("code", 200);
            retJsonObject.put("apps", apps.get("rslts"));
        } catch (final ServiceException e) {
            retJsonObject.put("code", 500);
            LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
        }
        renderer.setJSONObject(retJsonObject);
    }

    @RequestProcessing(value = { "/share" }, method = HTTPRequestMethod.POST)
    public void share(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    }

}
