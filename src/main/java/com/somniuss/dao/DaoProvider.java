package com.somniuss.dao;

import com.somniuss.dao.impl.NewsDaoImpl;
import com.somniuss.dao.impl.SQLSoundeffectsDao;
import com.somniuss.dao.impl.SQLUserDao;

public class DaoProvider {
	
    private static final DaoProvider instance = new DaoProvider();

    private final UserDao userDao = new SQLUserDao();
    private final SoundeffectsDao soundeffectsDao = new SQLSoundeffectsDao();
    private final NewsDao newsDao = new NewsDaoImpl();

    private DaoProvider() {}

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SoundeffectsDao getSoundeffectsDao() {  
        return soundeffectsDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }
}
