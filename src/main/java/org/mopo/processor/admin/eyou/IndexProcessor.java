package org.mopo.processor.admin.eyou;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.Latkes;
import org.b3log.latke.Keys.Server;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.servlet.renderer.freemarker.FreeMarkerRenderer;


/**
 * Admin control index.
 * 
 * @author zezhou
 * 
 */
@RequestProcessor
public final class IndexProcessor {

    private static final Logger LOGGER = Logger.getLogger(IndexProcessor.class
            .getName());

    @RequestProcessing(value = {"/eyou/admin","/eyou/admin/index", "/**/ant/*/path" }, method = HTTPRequestMethod.GET)
    public void index(final HTTPRequestContext context) {
        LOGGER.entering(IndexProcessor.class.getSimpleName(), "index");

        final AbstractFreeMarkerRenderer render = new FreeMarkerRenderer();
        context.setRenderer(render);

        render.setTemplateName("/eyou/index.ftl");
        final Map<String, Object> dataModel = render.getDataModel(); 
        LOGGER.exiting(IndexProcessor.class.getSimpleName(), "index");
    }
    
    
}
