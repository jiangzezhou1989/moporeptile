package org.mopo.service;

import java.util.logging.Logger;

import org.b3log.latke.Keys;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.repository.CompositeFilterOperator;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mopo.model.App;
import org.mopo.repository.AppRepository;
import org.mopo.repository.impl.AppRepositoryImpl;

import com.google.apphosting.api.DatastorePb.Query.Filter.Operator;

/**
 * App Query Service.
 * 
 * @author <a href="mailto:jiangzezhou1989@gmail.com">zezhou</a>
 * @version 2.0, 2013-6-14 , 下午9:41:49
 */
public final class AppQueryService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AppQueryService.class
            .getName());

    private AppRepository appRepository = AppRepositoryImpl.getInstance();

    public JSONObject getApps(JSONObject requestJsonObject)
            throws ServiceException {
        JSONObject apps = null;
        try {
            final int pageSize = requestJsonObject.getInt("pageSize");
            final int currentPageNum = requestJsonObject.getInt("page");
            final Query query = new Query().setCurrentPageNum(currentPageNum)
                    .setPageSize(pageSize)
                    .addSort(App.TYPE, SortDirection.ASCENDING)
                    .addSort(App.ORDER_NUM, SortDirection.DESCENDING);

            if (requestJsonObject.has(App.ORDER_NUM)) {
                query.setFilter(new PropertyFilter(App.ORDER_NUM,
                        FilterOperator.GREATER_THAN_OR_EQUAL, requestJsonObject
                                .get(App.ORDER_NUM)));
            }

            if (requestJsonObject.has(App.TYPE)
                    && requestJsonObject.has(App.ORDER_NUM)) {
                query.setFilter(CompositeFilterOperator.and(
                        new PropertyFilter(App.TYPE, FilterOperator.EQUAL,
                                requestJsonObject.get(App.TYPE)),
                        new PropertyFilter(App.ORDER_NUM,
                                FilterOperator.GREATER_THAN_OR_EQUAL,
                                requestJsonObject.get(App.ORDER_NUM))));
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
     * Get app by appid.
     * 
     * @param appid
     * @return app json object
     * @throws ServiceException
     *             service exception.
     */
    public JSONObject findByAppid(final String appid) throws ServiceException {
        JSONObject appJson = null;
        try {
            final Query query = new Query().setFilter(new PropertyFilter(
                    App.APP_ID, FilterOperator.EQUAL, appid));

            JSONArray apps = appRepository.get(query)
                    .getJSONArray(Keys.RESULTS);
            if (apps.length() >= 1) {
                appJson = apps.getJSONObject(0);
            }

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException e) {
            throw new ServiceException(e);
        }
        return appJson;
    }

    /**
     * Gets Appids.
     * 
     * @return
     * @throws ServiceException
     */
    public JSONArray getAppIds() throws ServiceException {
        JSONArray appids = new JSONArray();
        JSONArray apps = null;
        try {

            final Query query = new Query().addSort(App.ORDER_NUM,
                    SortDirection.DESCENDING);

            query.setFilter(new PropertyFilter(App.ORDER_NUM,
                    FilterOperator.GREATER_THAN_OR_EQUAL, 0));

            apps = appRepository.get(query).getJSONArray(Keys.RESULTS);

            for (int i = 0; i < apps.length(); i++) {
                JSONObject app = (JSONObject) apps.get(i);
                JSONObject appidOption = new JSONObject();
                appidOption.put(App.VALUE, app.get(App.APP_ID));
                appidOption.put(App.DISPLAY_TEXT, app.get(App.NAME));
                appids.put(appidOption);
            }
        } catch (RepositoryException re) {
            re.printStackTrace();
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
        return appids;
    }

    /**
     * get All Apps except for orderNum <0 and type < 0.
     * 
     * @return all apps
     */
    public JSONArray getAllApps() throws ServiceException {
        JSONArray apps = null;
        try {
            Query query = new Query().setFilter(new PropertyFilter(
                    App.ORDER_NUM, FilterOperator.GREATER_THAN_OR_EQUAL, 0));
            apps = appRepository.get(query).getJSONArray(Keys.RESULTS);

        } catch (RepositoryException re) {
            throw new ServiceException(re);
        } catch (JSONException je) {
            throw new ServiceException(je);
        }
        return apps;

    }

    /**
     * Gets the {@link AppQueryService} singleton.
     * 
     * @return the singleton
     */
    public static AppQueryService getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Private constructor.
     */
    private AppQueryService() {

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
        private static final AppQueryService SINGLETON = new AppQueryService();

        /**
         * Private constructor.
         */
        private SingletonHolder() {

        }

    }

}
