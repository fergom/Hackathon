package com.a480.fernando.hackathon.notifications;

import com.a480.fernando.hackathon.dao.TokenDao;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Fernando on 04/05/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        TokenDao tokenDao = new TokenDao();
        tokenDao.setToken(FirebaseInstanceId.getInstance().getToken());
    }
}
