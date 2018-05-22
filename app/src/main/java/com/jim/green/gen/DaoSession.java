package com.jim.green.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jim.recorder.model.CategoryType;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;

import com.jim.green.gen.CategoryTypeDao;
import com.jim.green.gen.DayCellDao;
import com.jim.green.gen.EventTypeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoryTypeDaoConfig;
    private final DaoConfig dayCellDaoConfig;
    private final DaoConfig eventTypeDaoConfig;

    private final CategoryTypeDao categoryTypeDao;
    private final DayCellDao dayCellDao;
    private final EventTypeDao eventTypeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoryTypeDaoConfig = daoConfigMap.get(CategoryTypeDao.class).clone();
        categoryTypeDaoConfig.initIdentityScope(type);

        dayCellDaoConfig = daoConfigMap.get(DayCellDao.class).clone();
        dayCellDaoConfig.initIdentityScope(type);

        eventTypeDaoConfig = daoConfigMap.get(EventTypeDao.class).clone();
        eventTypeDaoConfig.initIdentityScope(type);

        categoryTypeDao = new CategoryTypeDao(categoryTypeDaoConfig, this);
        dayCellDao = new DayCellDao(dayCellDaoConfig, this);
        eventTypeDao = new EventTypeDao(eventTypeDaoConfig, this);

        registerDao(CategoryType.class, categoryTypeDao);
        registerDao(DayCell.class, dayCellDao);
        registerDao(EventType.class, eventTypeDao);
    }
    
    public void clear() {
        categoryTypeDaoConfig.clearIdentityScope();
        dayCellDaoConfig.clearIdentityScope();
        eventTypeDaoConfig.clearIdentityScope();
    }

    public CategoryTypeDao getCategoryTypeDao() {
        return categoryTypeDao;
    }

    public DayCellDao getDayCellDao() {
        return dayCellDao;
    }

    public EventTypeDao getEventTypeDao() {
        return eventTypeDao;
    }

}
