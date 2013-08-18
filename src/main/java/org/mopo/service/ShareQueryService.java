package org.mopo.service;

import java.util.logging.Logger;

import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;

import org.b3log.latke.service.ServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import org.mopo.repository.ShareRepository;
import org.mopo.repository.impl.ShareRepositoryImpl;

/**
 * App Query Service.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:49
 */
public final class ShareQueryService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger
            .getLogger(ShareQueryService.class.getName());

    private ShareRepository shareRepository = ShareRepositoryImpl.getInstance();

    public JSONObject getShares(JSONObject requestJsonObject)
            throws ServiceException {
        JSONObject shares = null;
        try {

            final int pageSize = requestJsonObject.getInt("pageSize");
            final int currentPageNum = requestJsonObject.getInt("page");
            final Query query = new Query().setCurrentPageNum(currentPageNum)
                    .setPageSize(pageSize);

            shares = shareRepository.get(query);

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
        return shares;
    }

    /**
     * Gets the {@link ShareQueryService} singleton.
     * 
     * @return the singleton
     */
    public static ShareQueryService getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Private constructor.
     */
    private ShareQueryService() {

    }

    /**
     * Singleton holder.
     * 
     * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
     * @version 2.0, 2013-6-14 , 下午9:50:55
     */
    private static final class SingletonHolder {

        /**
         * Singleton.
         */
        private static final ShareQueryService SINGLETON = new ShareQueryService();

        /**
         * Private constructor.
         */
        private SingletonHolder() {

        }

    }

}
