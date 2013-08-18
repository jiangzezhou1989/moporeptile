package org.mopo.service;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.util.Ids;
import org.json.JSONObject;
import org.mopo.model.App;
import org.mopo.repository.AppRepository;
import org.mopo.repository.impl.AppRepositoryImpl;


public class AppMgmtService {

    private AppRepository appRepository = AppRepositoryImpl.getInstance();

    private AppQueryService appQueryService = AppQueryService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(AppMgmtService.class.getName());

    /**
     * Add app by the specified requestJSONObject.
     * 
     * @param requestJSONObject
     *            the specified requestJSONObject.
     * @return the app oId.
     * @throws ServiceException
     *             serviceException
     */
    public String addApp(final JSONObject requestJSONObject)
            throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = appRepository.beginTransaction();

        try {
            String appid = requestJSONObject.getString(App.APP_ID);
            if (StringUtils.isEmpty(appid)) {
                throw new ServiceException("应用id不可以为空!!");
            }
            JSONObject appJson = appQueryService.findByAppid(appid);
            LOGGER.info("get json by appid" + appJson);
            if (appJson != null) {
                throw new ServiceException("应用已经存在!!");
            }
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
     * @param requestJSONObject
     *            the specified requestJSONObject
     * @return the object oId.
     * @throws ServiceException
     *             serviceException
     */
    public String updateApp(final JSONObject requestJSONObject)
            throws ServiceException {
        // TODO: add article args check

        final Transaction transaction = appRepository.beginTransaction();

        try {
            String oId = requestJSONObject.getString(Keys.OBJECT_ID);
            JSONObject originalApp = appRepository.get(oId);
            String updateAppid = (String) requestJSONObject.get(App.APP_ID);
            JSONObject updateApp = appQueryService.findByAppid(updateAppid);
            if (updateApp != null
                    && !updateApp.getString(App.APP_ID).equals(
                            originalApp.getString(App.APP_ID))) {
                throw new ServiceException("应用已经存在!!");
            }
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
     * Gets the {@link AppMgmtService} singleton.
     * 
     * @return the singleton.
     */
    public static AppMgmtService getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午10:36:08
     */
    private static class SingletonHolder {

        private static final AppMgmtService SINGLETON = new AppMgmtService();

        /**
         * Private Constructor.
         */
        private SingletonHolder() {

        }
    }

}
