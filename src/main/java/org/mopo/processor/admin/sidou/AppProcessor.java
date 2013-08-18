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

import org.json.JSONObject;
import org.mopo.service.AppMgmtService;
import org.mopo.service.AppQueryService;
import org.mopo.utils.StringUtils;

/**
 * App console.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:09
 */
@RequestProcessor
public final class AppProcessor {

    private static final Logger LOGGER = Logger.getLogger(AppProcessor.class
            .getName());

    private static final AppQueryService appService = AppQueryService.getInstance();

    private static final AppMgmtService appMgmtService = AppMgmtService.getInstance();

    @RequestProcessing(value = "/app/list", method = { HTTPRequestMethod.GET,
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
            String type = request.getParameter("type");
            if(!StringUtils.isEmpty("jtSorting")) {
                
            }
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
    
    private void getSorting(JSONObject retJsonObject, String sorting) {
        String[] sortDesc = sorting.split(" ");
        if(sortDesc.length ==2) {
          /*  if(){
                
            }*/
        }
        
    }

    @RequestProcessing(value = "/app/create", method = { HTTPRequestMethod.GET,
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
            String name = request.getParameter("name");
            String icon = request.getParameter("icon");
            String apk = request.getParameter("apk");
            int size = Integer.parseInt(request.getParameter("size"));
            String des = request.getParameter("des");
            String imgs = request.getParameter("imgs");
            if(!checkImgs(imgs)) {
                throw new Exception();
            }
            int type = Integer.parseInt(request.getParameter("type"));
           // boolean isAndroid = optBoolean(request.getParameter("isAndroid")); 
           // String publishedTime = request.getParameter("publishedTime");
           // Date publishedTime = dateFormat(request.getParameter("publishedTime"));
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));

            JSONObject appJson = new JSONObject();
            appJson.put("appid", appid);
            appJson.put("name", name);
            appJson.put("icon", icon);
            appJson.put("apk", apk);
            appJson.put("size", size);
            appJson.put("des", des);
            appJson.put("imgs", imgs);
            //appJson.put("isAndroid", String.valueOf(isAndroid));
           // appJson.put("publishedTime", publishedTime);
            appJson.put("orderNum", orderNum);
            appJson.put("type", type);
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
            retJsonObject.put("Message", "信息填写不完整或填写错误或应用已经存在!!");
            renderer.setJSONObject(retJsonObject);
        }

    }
    
    
    @RequestProcessing(value = "/app/update", method = { HTTPRequestMethod.GET,
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
            String name = request.getParameter("name");
            String icon = request.getParameter("icon");
            String apk = request.getParameter("apk");
            int size = Integer.parseInt(request.getParameter("size"));
            String des = request.getParameter("des");
            String imgs = request.getParameter("imgs");
            if(!checkImgs(imgs)) {
                throw new Exception();
            }
            int type = Integer.parseInt(request.getParameter("type"));
            //boolean isAndroid = optBoolean(request.getParameter("isAndroid")); 
           // String publishedTime = request.getParameter("publishedTime");
           // Date publishedTime = dateFormat(request.getParameter("publishedTime"));
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));
            
            JSONObject appJson = new JSONObject();
            appJson.put("appid", appid);
            appJson.put("name", name);
            appJson.put("icon", icon);
            appJson.put("apk", apk);
            appJson.put("size", size);
            appJson.put("des", des);
            appJson.put("imgs", imgs);
           // appJson.put("isAndroid", String.valueOf(isAndroid));
           // appJson.put("publishedTime", publishedTime);
            appJson.put("orderNum", orderNum);
            appJson.put("type", type);
            LOGGER.info("post json is: " + appJson.toString());
            appJson.put(Keys.OBJECT_ID, oId);
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
    

    /**
     * check img forat 
     * format like img,img1,img2
     * @param imgs
     */
    public boolean checkImgs(String imgs) {
       String[] imagArray = imgs.split(",");
       if(imagArray.length >=2) {
           return true;
       }
       return false;
    }
    
    private boolean optBoolean(String str) {
        if (str.trim() == "true") {
            return true;
        }
        return false;
    }
    
}
