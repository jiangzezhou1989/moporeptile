package org.mopo.repository.impl;

import org.b3log.latke.repository.AbstractRepository;
import org.mopo.model.Ad;
import org.mopo.repository.AdsRepository;

public class AdsRepositoryImpl extends AbstractRepository implements
        AdsRepository {

    /**
     * Singleton.
     */
    private static final AdsRepositoryImpl SINGLETON = new AdsRepositoryImpl(
            Ad.AD);

    /**
     * Default constructor.
     * 
     * @param name
     *            the repostitory name
     */
    private AdsRepositoryImpl(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the {@link AdsRepositoryImpl} singleton.
     * 
     * @return singleton
     */
    public static AdsRepositoryImpl getInstance() {
        return SINGLETON;
    }

}
