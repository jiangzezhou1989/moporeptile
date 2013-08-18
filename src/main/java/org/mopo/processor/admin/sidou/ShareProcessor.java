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
import org.mopo.model.Share;
import org.mopo.service.ShareMgmtService;
import org.mopo.service.ShareQueryService;

/**
 * App console.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:09
 */
@RequestProcessor
public final class ShareProcessor {

    private static final Logger LOGGER = Logger.getLogger(ShareProcessor.class
            .getName());

    private static final ShareQueryService shareService = ShareQueryService.getInstance();

    private static final ShareMgmtService shareMgmtService = ShareMgmtService.getInstance();

    
    @RequestProcessing(value = "/share/list", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        
        //get params     
       
        final JSONRenderer renderer = new JSONRenderer();     
        
        context.setRenderer(renderer);  

        JSONObject retJsonObject = new JSONObject();
        JSONObject shares = new JSONObject();
        final JSONObject requestJsonObject = new JSONObject() ;
        try {
            
            int pageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
            int pageSize = Integer.parseInt(request.getParameter("jtPageSize"));
            int currentNum = pageIndex/pageSize + 1;
            requestJsonObject.put("page",currentNum );
            requestJsonObject.put("pageSize",pageSize );
            shares = shareService.getShares(requestJsonObject);
            retJsonObject.put("Result", "OK");
            retJsonObject.put("Records", shares.get("rslts"));

            LOGGER.info("retJsonObject: " + shares.get("rslts"));

        } catch (ServiceException e) {
            LOGGER.severe("get shares error");
        }

        renderer.setJSONObject(retJsonObject);

    }

    @RequestProcessing(value = "/share/create", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void create(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();
        // get post params
        try {
            String cnt = request.getParameter("cnt");           
          
            JSONObject shareJson = new JSONObject();
            shareJson.put(Share.CNT, cnt);           
            LOGGER.info("post json is: " + shareJson.toString());
            String oId;           
            oId = shareMgmtService.addShare(shareJson);
            shareJson.put(Keys.OBJECT_ID, oId);

            retJsonObject.put("Result", "OK");
            retJsonObject.put("Record", shareJson);
            renderer.setJSONObject(retJsonObject);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,e.getMessage());
            retJsonObject.put("Result", "ERROR");
            retJsonObject.put("Message", "信息填写不完整或填写错误!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
    
    @RequestProcessing(value = "/share/update", method = { HTTPRequestMethod.GET,
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
            String cnt = request.getParameter("cnt");           
            
            JSONObject shareJson = new JSONObject();
            shareJson.put(Share.CNT, cnt);           
            LOGGER.info("post json is: " + shareJson.toString());
            
            shareJson.put(Keys.OBJECT_ID, oId);
            oId = shareMgmtService.updateShare(shareJson);            

            retJsonObject.put("Result", "OK");
            retJsonObject.put("Record", shareJson);
            renderer.setJSONObject(retJsonObject);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,e.getMessage());
            retJsonObject.put("Result", "ERROR");
            retJsonObject.put("Message", "信息填写不完整或填写错误!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
    
}
