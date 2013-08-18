package org.mopo.repository.impl;

import org.b3log.latke.repository.AbstractRepository;
import org.mopo.model.EyouApp;
import org.mopo.repository.EyouAppRepository;

public class EyouAppRepositoryImpl extends AbstractRepository implements
        EyouAppRepository {

    /**
     * Singleton.
     */
    private static final EyouAppRepositoryImpl SINGLETON = new EyouAppRepositoryImpl(
            EyouApp.EYOU_APP);

    /**
     * Default constructor.
     * 
     * @param name
     *            the repostitory name
     */
    private EyouAppRepositoryImpl(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the {@link EyouAppRepositoryImpl} singleton.
     * 
     * @return singleton
     */
    public static EyouAppRepositoryImpl getInstance() {
        return SINGLETON;
    }

}
