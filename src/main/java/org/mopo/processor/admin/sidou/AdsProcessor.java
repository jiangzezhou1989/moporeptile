package org.mopo.processor.admin.sidou;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.Keys;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mopo.service.AdsMgmtService;
import org.mopo.service.AdsQueryService;
import org.mopo.service.AppQueryService;

/**
 * App console.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:09
 */
@RequestProcessor
public final class AdsProcessor {

    private static final Logger LOGGER = Logger.getLogger(AdsProcessor.class
            .getName());

    private static final AdsQueryService adsService = AdsQueryService.getInstance();

    private static final AdsMgmtService adsMgmtService = AdsMgmtService.getInstance();

    private static final AppQueryService appService = AppQueryService.getInstance();
    
    @RequestProcessing(value = "/ads/list", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        
        //get params     
       
        final JSONRenderer renderer = new JSONRenderer();     
        
        context.setRenderer(renderer);  

        JSONObject retJsonObject = new JSONObject();
        JSONObject ads = new JSONObject();
        final JSONObject requestJsonObject = new JSONObject() ;
        try {
            
            int pageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
            int pageSize = Integer.parseInt(request.getParameter("jtPageSize"));
            int currentNum = pageIndex/pageSize + 1;
            requestJsonObject.put("page",currentNum );
            requestJsonObject.put("pageSize",pageSize );
            ads = adsService.getAds(requestJsonObject);
            retJsonObject.put("Result", "OK");
            retJsonObject.put("Records", ads.get("rslts"));

            LOGGER.info("retJsonObject: " + ads.get("rslts"));

        } catch (ServiceException e) {
            LOGGER.severe("get applist error");
        }

        renderer.setJSONObject(retJsonObject);

    }

    @RequestProcessing(value = "/ads/create", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void create(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();
        // get post params
        try {
            String appid = request.getParameter("appid");           
            String ad = request.getParameter("ad");          
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));

            JSONObject adJson = new JSONObject();
            adJson.put("appid", appid);
            adJson.put("ad", ad);           
            adJson.put("orderNum", orderNum);           
            LOGGER.info("post json is: " + adJson.toString());
            String oId;           
            oId = adsMgmtService.addAd(adJson);
            adJson.put(Keys.OBJECT_ID, oId);

            retJsonObject.put("Result", "OK");
            retJsonObject.put("Record", adJson);
            renderer.setJSONObject(retJsonObject);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,e.getMessage());
            retJsonObject.put("Result", "ERROR");
            retJsonObject.put("Message", "信息填写不完整或填写错误!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
    
    @RequestProcessing(value = "/ads/update", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void update(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();
        // get post params
        try {
            String oId  = request.getParameter(Keys.OBJECT_ID);
            String appid = request.getParameter("appid");           
            String ad = request.getParameter("ad");          
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));

            JSONObject appJson = new JSONObject();
            appJson.put("appid", appid);
            appJson.put("ad", ad);           
            appJson.put("orderNum", orderNum);    
            
            LOGGER.info("post json is: " + appJson.toString());
            
            appJson.put(Keys.OBJECT_ID, oId);
            oId = adsMgmtService.updateAd(appJson);            

            retJsonObject.put("Result", "OK");
            retJsonObject.put("Record", appJson);
            renderer.setJSONObject(retJsonObject);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,e.getMessage());
            retJsonObject.put("Result", "ERROR");
            retJsonObject.put("Message", "信息填写不完整或填写错误!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
    @RequestProcessing(value = "/ads/appids", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void appids(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();
        // get post params
        try {                    
            JSONArray appidsOptions = appService.getAppIds(); 
            retJsonObject.put("Result", "OK");
            retJsonObject.put("Options", appidsOptions);
            renderer.setJSONObject(retJsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,e.getMessage());
            retJsonObject.put("Result", "ERROR");
            retJsonObject.put("Message", "信息填写不完整或填写错误!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
}
