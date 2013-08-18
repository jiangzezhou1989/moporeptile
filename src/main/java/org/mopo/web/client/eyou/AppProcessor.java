package org.mopo.web.client.eyou;



import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.json.JSONObject;
import org.mopo.model.App;
import org.mopo.service.EyouAppQueryService;

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

    private static final int DEFAULT_PAGE_SIZE = 100;
    
    private static final int DEFAULT_PAGE = 1;
    
    private static final EyouAppQueryService appQueryService = EyouAppQueryService.getInstance();
    
    /**
     *  Handler client request of app list.
     *  
     * @param context  the specified context
     * @param request  the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @throws Exception exception 
     */
    @RequestProcessing(value = { "/eyou/index", "/eyou" }, method = {HTTPRequestMethod.GET,HTTPRequestMethod.POST})
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        
        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);      
      
        
        final JSONObject ret = new JSONObject();
        JSONObject retJsonObject = new JSONObject();
        try {
            final JSONObject requestJSONObject = new JSONObject();
            requestJSONObject.put("page",DEFAULT_PAGE);
            requestJSONObject.put("pageSize",DEFAULT_PAGE_SIZE);
            requestJSONObject.put(App.ORDER_NUM,0);
            LOGGER.info("requestJSONObject :" + requestJSONObject);
            
            JSONObject apps = appQueryService.getApps(requestJSONObject);
            retJsonObject.put("code",200);
            retJsonObject.put("apps",apps.get("rslts"));
        } catch(final ServiceException e){
            retJsonObject.put("code",500);
            LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
        }
       renderer.setJSONObject(retJsonObject);
    }

}
