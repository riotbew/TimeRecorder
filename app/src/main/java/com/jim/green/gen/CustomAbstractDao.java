package com.jim.green.gen;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.internal.DaoConfig;

public abstract class CustomAbstractDao<T, K> extends AbstractDao<T, K> {
    CustomAbstractDao(DaoConfig config) {
        super(config);
    }

    public CustomAbstractDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public void clearCache() {
        this.config.clearIdentityScope();
    }
}
