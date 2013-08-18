package org.mopo.processor.admin.eyou;


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
import org.b3log.latke.util.Requests;
import org.json.JSONObject;
import org.mopo.service.EyouAppMgmtService;
import org.mopo.service.EyouAppQueryService;

/**
 * Eyou App console.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:09
 */
@RequestProcessor
public final class EyouAppProcessor {

    private static final Logger LOGGER = Logger.getLogger(EyouAppProcessor.class
            .getName());

    private static final EyouAppQueryService appService = EyouAppQueryService.getInstance();

    private static final EyouAppMgmtService appMgmtService = EyouAppMgmtService.getInstance();

    @RequestProcessing(value = "/eyou/admin/list", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void list(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        
        //get params    
       
        final JSONRenderer renderer = new JSONRenderer();     
        
        context.setRenderer(renderer);  

        JSONObject retJsonObject = new JSONObject();
        JSONObject apps = new JSONObject();
        final JSONObject requestJsonObject = new JSONObject() ;
        try {
            
            int pageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
            int pageSize = Integer.parseInt(request.getParameter("jtPageSize"));
            int currentNum = pageIndex/pageSize + 1;
            requestJsonObject.put("page",currentNum );
            requestJsonObject.put("pageSize",pageSize );
            apps = appService.getApps(requestJsonObject);
            retJsonObject.put("Result", "OK");
            retJsonObject.put("Records", apps.get("rslts"));

            LOGGER.info("retJsonObject: " + apps.get("rslts"));

        } catch (ServiceException e) {
            LOGGER.severe("get applist error");
        }

        renderer.setJSONObject(retJsonObject);

    }

    @RequestProcessing(value = "/eyou/admin/create", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void create(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();    
        // get post params
        try {            
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String icon = request.getParameter("icon");
            String url = request.getParameter("url");
            String des = request.getParameter("des");
            String pkg = request.getParameter("pkg");                 
            int type = Integer.parseInt(request.getParameter("type"));           
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));

            JSONObject appJson = new JSONObject();
            appJson.put("id", id);
            appJson.put("title", title);
            appJson.put("icon", icon);
            appJson.put("url", url);
            appJson.put("des", des);
            appJson.put("orderNum", orderNum);
            appJson.put("type", type);
            appJson.put("pkg", pkg);
            LOGGER.info("post json is: " + appJson.toString());
            String oId;           
            oId = appMgmtService.addApp(appJson);
            appJson.put(Keys.OBJECT_ID, oId);

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
    
    
    @RequestProcessing(value = "/eyou/admin/update", method = { HTTPRequestMethod.GET,
            HTTPRequestMethod.POST })
    public void update(final HTTPRequestContext context,
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        JSONObject retJsonObject = new JSONObject();       
        // get post params
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String icon = request.getParameter("icon");
            String url = request.getParameter("url");
            String des = request.getParameter("des");
            String pkg = request.getParameter("pkg");                 
            int type = Integer.parseInt(request.getParameter("type"));           
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));
            String oId =  request.getParameter(Keys.OBJECT_ID); 
            
            JSONObject appJson = new JSONObject();            
            appJson.put("id", id);
            appJson.put("title", title);
            appJson.put("icon", icon);
            appJson.put("url", url);
            appJson.put("des", des);
            appJson.put("orderNum", orderNum);
            appJson.put("type", type);
            appJson.put("pkg", pkg);
            appJson.put(Keys.OBJECT_ID, oId);
            LOGGER.info("post json is: " + appJson.toString());           
            oId = appMgmtService.updateApp(appJson);  
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
    
}
