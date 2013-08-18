package org.mopo.web.client.sidou;



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
import org.json.JSONArray;
import org.json.JSONObject;
import org.mopo.model.Ad;
import org.mopo.service.AdsQueryService;

/**
 * Handler client request.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午10:34:38
 */
@RequestProcessor
public final class AdsProcessor {

    private static final Logger LOGGER = Logger.getLogger(AdsProcessor.class
            .getName());

    private static final int DEFAULT_PAGE_SIZE = 10;
    
    private static final int DEFAULT_PAGE = 1;
    
    private static final AdsQueryService adsQueryService = AdsQueryService.getInstance();
    
    /**
     *  Handler client request of app list.
     *  
     * @param context  the specified context
     * @param request  the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @throws Exception exception 
     */
    @RequestProcessing(value = {"/ads"}, method = HTTPRequestMethod.POST)
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        
        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);    
        JSONObject retJsonObject = new JSONObject();
       // retJsonObject.
        try {
            final JSONObject requestJSONObject = new JSONObject();
            requestJSONObject.put("pageSize",DEFAULT_PAGE_SIZE);
            requestJSONObject.put("page",DEFAULT_PAGE);
            requestJSONObject.put(Ad.ORDER_NUM,0);          
            
            JSONArray apps = adsQueryService.getAdsAndApps(requestJSONObject);
            retJsonObject.put("code",200);
            retJsonObject.put("apps",apps);
        } catch(final ServiceException e){
            retJsonObject.put("code",500);
            LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
        }
       renderer.setJSONObject(retJsonObject);
    }

}
