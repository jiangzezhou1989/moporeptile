package org.mopo.service;

import java.util.logging.Logger;


import org.b3log.latke.Keys;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mopo.model.Ad;
import org.mopo.model.App;

import org.mopo.repository.AdsRepository;
import org.mopo.repository.impl.AdsRepositoryImpl;

/**
 * App Query Service.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:49
 */
public final class AdsQueryService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AdsQueryService.class
            .getName());

    private AdsRepository adsRepository = AdsRepositoryImpl.getInstance();
    
    private AppQueryService appService = AppQueryService.getInstance();

    public JSONObject getAds(JSONObject requestJsonObject)
            throws ServiceException {
        JSONObject ads = null;
        try {

            final int pageSize = requestJsonObject.getInt("pageSize");
            final int currentPageNum = requestJsonObject.getInt("page"); 
            final Query query = new Query().setCurrentPageNum(currentPageNum)
                    .setPageSize(pageSize)
                    .addSort(Ad.ORDER_NUM, SortDirection.DESCENDING); 
            ads = adsRepository.get(query);

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
        return ads;
    }
    
    
    public JSONArray getAdsAndApps(JSONObject requestJsonObject)
            throws ServiceException {
        JSONArray adsAndApps = new JSONArray();
        try {

            final int pageSize = requestJsonObject.getInt("pageSize");
            final int currentPageNum = requestJsonObject.getInt("page"); 
            final Query query = new Query().setCurrentPageNum(currentPageNum)
                    .setPageSize(pageSize)
                    .addSort(Ad.ORDER_NUM, SortDirection.DESCENDING); 
            if (requestJsonObject.has(Ad.ORDER_NUM)) {
                query.setFilter(new PropertyFilter(Ad.ORDER_NUM,
                        FilterOperator.GREATER_THAN_OR_EQUAL, requestJsonObject
                                .get(Ad.ORDER_NUM)));
            }
            JSONObject ads = adsRepository.get(query);
            JSONArray adsJsonArray = ads.getJSONArray(Keys.RESULTS);
            JSONArray apps = appService.getAllApps();           
            for(int i = 0; i < adsJsonArray.length(); i++) {
                JSONObject ad = (JSONObject)adsJsonArray.get(i);
                for(int j = 0; j < apps.length(); j++) {
                    JSONObject app = (JSONObject)apps.get(j);
                    if(app.getString(App.APP_ID).trim().equals(ad.getString(Ad.APP_ID))) {
                        app.put(Ad.AD,ad.getString(Ad.AD) );
                        adsAndApps.put(app);
                        break;
                    }
                }
            }
            return adsAndApps;

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
       
    }
    
    /**
     * Gets the {@link AdsQueryService} singleton.
     * 
     * @return the singleton
     */
    public static AdsQueryService getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    /**
     * Private constructor.
     */
    private AdsQueryService() {
        
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
        private static final AdsQueryService SINGLETON = new AdsQueryService();
        
        /**
         * Private constructor.
         */
        private SingletonHolder(){
            
        }
        
    }

}
