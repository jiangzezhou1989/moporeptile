package org.mopo.service;

import java.util.logging.Logger;


import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import org.mopo.model.EyouApp;
import org.mopo.repository.EyouAppRepository;
import org.mopo.repository.impl.EyouAppRepositoryImpl;

/**
 * App Query Service.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:49
 */
public final class EyouAppQueryService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(EyouAppQueryService.class
            .getName());

    private EyouAppRepository appRepository = EyouAppRepositoryImpl.getInstance();

    public JSONObject getApps(JSONObject requestJsonObject)
            throws ServiceException {
        JSONObject apps = null;
        try {

            final int pageSize = requestJsonObject.getInt("pageSize");
            final int currentPageNum = requestJsonObject.getInt("page"); 
            final Query query = new Query().setCurrentPageNum(currentPageNum)
                    .setPageSize(pageSize)
                    .addSort(EyouApp.ORDER_NUM, SortDirection.DESCENDING);           
            if(requestJsonObject.has(EyouApp.ORDER_NUM)) {
                query.setFilter(new PropertyFilter(EyouApp.ORDER_NUM,
                        FilterOperator.GREATER_THAN_OR_EQUAL, requestJsonObject.get(EyouApp.ORDER_NUM)));
            }
            apps = appRepository.get(query);

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
        return apps;
    }
    
    /**
     * Gets the {@link EyouAppQueryService} singleton.
     * 
     * @return the singleton
     */
    public static EyouAppQueryService getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    /**
     * Private constructor.
     */
    private EyouAppQueryService() {
        
    }
    
    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午9:50:55
     */
    private static final class SingletonHolder{
        
        /**
         * Singleton.
         */
        private static final EyouAppQueryService SINGLETON = new EyouAppQueryService();
        
        /**
         * Private constructor.
         */
        private SingletonHolder(){
            
        }
        
    }

}
