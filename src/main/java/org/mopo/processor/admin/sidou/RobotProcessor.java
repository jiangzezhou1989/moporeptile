package org.mopo.processor.admin.sidou;


import java.util.logging.Logger;

import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;



/**
 * Robot processor.
 * 
 * @author zezhou
 * 
 */
@RequestProcessor
public class RobotProcessor {

    private static final Logger LOGGER = Logger.getLogger(RobotProcessor.class
            .getName());

    @RequestProcessing(value = { "/robot", "/**/ant/*/path" }, method = HTTPRequestMethod.GET)
    public void index(final HTTPRequestContext context) {
        //自动登陆网站
        //抓取网页爬取数据
        //存储
       
        
    }  
  

  
}
