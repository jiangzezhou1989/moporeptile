package org.mopo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.util.Ids;
import org.json.JSONObject;
import org.mopo.repository.AdsRepository;
import org.mopo.repository.impl.AdsRepositoryImpl;

public class AdsMgmtService {
    
    
    private AdsRepository adsRepository = AdsRepositoryImpl.getInstance();
    
    /**
     * Add app by the specified requestJSONObject.
     * 
     * @param requestJSONObject the specified requestJSONObject.
     * @return the app oId.
     * @throws ServiceException serviceException
     */
    public String addAd(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = adsRepository.beginTransaction();

        try {
            String oId = Ids.genTimeMillisId();
            requestJSONObject.put(Keys.OBJECT_ID, oId);
            adsRepository.add(requestJSONObject);
            transaction.commit();

            return oId;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e);
        }
    }
    
    /**
     * Update app by the specified requestJSONObject.
     * 
     * @param requestJSONObject the specified requestJSONObject
     * @return the object oId.
     * @throws ServiceException serviceException
     */
    public String updateAd(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = adsRepository.beginTransaction();

        try {
            String oId =requestJSONObject.getString(Keys.OBJECT_ID); 
            adsRepository.update(oId, requestJSONObject);
            transaction.commit();

            return oId;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e);
        }
    }
    
    /**
     * Gets the {@link AdsMgmtService} singleton.
     * 
     * @return the singleton.
     */
    public static AdsMgmtService getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午10:36:08
     */
    private static class SingletonHolder{
        
        private static final AdsMgmtService SINGLETON = new AdsMgmtService();
        /**
         * Private Constructor.
         */
        private SingletonHolder() {
            
        }
    }


}
