package com.a480.fernando.hackathon.singleton;

import com.a480.fernando.hackathon.dao.DocumentDao;
import com.a480.fernando.hackathon.dao.EventsDao;
import com.a480.fernando.hackathon.dao.FeedbackDao;
import com.a480.fernando.hackathon.dao.InfoDao;
import com.a480.fernando.hackathon.dao.MapsDao;
import com.a480.fernando.hackathon.dao.MatchDao;
import com.a480.fernando.hackathon.dao.NewsDao;
import com.a480.fernando.hackathon.dao.NotificationsDao;
import com.a480.fernando.hackathon.dao.SpeakersDao;
import com.a480.fernando.hackathon.dao.UserDao;

/**
 * Created by Fernando on 04/05/2017.
 */

public class Singleton {

    private static DocumentDao documentDao = null;
    private static EventsDao eventsDao = null;
    private static FeedbackDao feedbackDao = null;
    private static InfoDao infoDao = null;
    private static MapsDao mapsDao = null;
    private static MatchDao matchDao = null;
    private static NewsDao newsDao = null;
    private static NotificationsDao notificationsDao = null;
    private static SpeakersDao speakersDao = null;
    private static UserDao userDao = null;

    public static DocumentDao getDocumentDaoInstance() {
        if(documentDao == null) {
            documentDao = new DocumentDao();
        }
        return documentDao;
    }

    public static EventsDao getEventsDaoInstance() {
        if(eventsDao == null) {
            eventsDao = new EventsDao();
        }
        return eventsDao;
    }

    public static FeedbackDao getFeedbackDaoInstance() {
        if(feedbackDao == null) {
            feedbackDao = new FeedbackDao();
        }
        return feedbackDao;
    }

    public static InfoDao getInfoDaoInstance() {
        if(infoDao == null) {
            infoDao = new InfoDao();
        }
        return infoDao;
    }

    public static MapsDao getMapsDaoInstance() {
        if(mapsDao == null) {
            mapsDao = new MapsDao();
        }
        return mapsDao;
    }

    public static MatchDao getMatchDaoInstance() {
        if(matchDao == null) {
            matchDao = new MatchDao();
        }
        return matchDao;
    }

    public static NewsDao getNewsDaoInstance() {
        if(newsDao == null) {
            newsDao = new NewsDao();
        }
        return newsDao;
    }

    public static NotificationsDao getNotificationsDaoInstance() {
        if(notificationsDao == null) {
            notificationsDao = new NotificationsDao();
        }
        return notificationsDao;
    }

    public static SpeakersDao getSpeakersDaoInstance() {
        if(speakersDao == null) {
            speakersDao = new SpeakersDao();
        }
        return speakersDao;
    }

    public static UserDao getUserDaoInstance() {
        if(userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }
    
}
