package org.mopo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.util.Ids;
import org.json.JSONObject;
import org.mopo.repository.ShareRepository;
import org.mopo.repository.impl.ShareRepositoryImpl;

public class ShareMgmtService {
    
    
    private ShareRepository shareRepository = ShareRepositoryImpl.getInstance();
    
    /**
     * Add app by the specified requestJSONObject.
     * 
     * @param requestJSONObject the specified requestJSONObject.
     * @return the app oId.
     * @throws ServiceException serviceException
     */
    public String addShare(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = shareRepository.beginTransaction();

        try {
            String oId = Ids.genTimeMillisId();
            requestJSONObject.put(Keys.OBJECT_ID, oId);
            shareRepository.add(requestJSONObject);
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
    public String updateShare(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = shareRepository.beginTransaction();

        try {
            String oId =requestJSONObject.getString(Keys.OBJECT_ID); 
            shareRepository.update(oId, requestJSONObject);
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
     * Gets the {@link ShareMgmtService} singleton.
     * 
     * @return the singleton.
     */
    public static ShareMgmtService getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午10:36:08
     */
    private static class SingletonHolder{
        
        private static final ShareMgmtService SINGLETON = new ShareMgmtService();
        /**
         * Private Constructor.
         */
        private SingletonHolder() {
            
        }
    }


}
