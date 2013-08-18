package org.mopo.repository.impl;

import org.b3log.latke.repository.AbstractRepository;
import org.json.JSONObject;
import org.mopo.model.App;
import org.mopo.repository.AppRepository;

public class AppRepositoryImpl extends AbstractRepository implements
        AppRepository {

    /**
     * Singleton.
     */
    private static final AppRepositoryImpl SINGLETON = new AppRepositoryImpl(
            App.APP);

    /**
     * Default constructor.
     * 
     * @param name
     *            the repostitory name
     */
    private AppRepositoryImpl(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the {@link AppRepositoryImpl} singleton.
     * 
     * @return singleton
     */
    public static AppRepositoryImpl getInstance() {
        return SINGLETON;
    }

}
