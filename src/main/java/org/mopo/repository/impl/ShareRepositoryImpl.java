package org.mopo.repository.impl;

import org.b3log.latke.repository.AbstractRepository;
import org.mopo.model.Share;
import org.mopo.repository.ShareRepository;

public class ShareRepositoryImpl extends AbstractRepository implements
        ShareRepository {

    /**
     * Singleton.
     */
    private static final ShareRepositoryImpl SINGLETON = new ShareRepositoryImpl(
            Share.SHARE);

    /**
     * Default constructor.
     * 
     * @param name
     *            the repostitory name
     */
    private ShareRepositoryImpl(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the {@link ShareRepositoryImpl} singleton.
     * 
     * @return singleton
     */
    public static ShareRepositoryImpl getInstance() {
        return SINGLETON;
    }

}
