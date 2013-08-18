package org.mopo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.util.Ids;
import org.json.JSONObject;
import org.mopo.repository.EyouAppRepository;
import org.mopo.repository.impl.EyouAppRepositoryImpl;

public class EyouAppMgmtService {
    
    
    private EyouAppRepository appRepository = EyouAppRepositoryImpl.getInstance();
    
    /**
     * Add app by the specified requestJSONObject.
     * 
     * @param requestJSONObject the specified requestJSONObject.
     * @return the app oId.
     * @throws ServiceException serviceException
     */
    public String addApp(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = appRepository.beginTransaction();

        try {
            String oId = Ids.genTimeMillisId();
            requestJSONObject.put(Keys.OBJECT_ID, oId);
            appRepository.add(requestJSONObject);
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
    public String updateApp(final JSONObject requestJSONObject) throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = appRepository.beginTransaction();

        try {
            String oId =requestJSONObject.getString(Keys.OBJECT_ID); 
            appRepository.update(oId, requestJSONObject);
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
     * Gets the {@link EyouAppMgmtService} singleton.
     * 
     * @return the singleton.
     */
    public static EyouAppMgmtService getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午10:36:08
     */
    private static class SingletonHolder{
        
        private static final EyouAppMgmtService SINGLETON = new EyouAppMgmtService();
        /**
         * Private Constructor.
         */
        private SingletonHolder() {
            
        }
    }


}
