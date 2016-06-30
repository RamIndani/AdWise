package com.learninghorizon.adwise.home.user;


import com.learninghorizon.adwise.home.spot.Spot;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ramnivasindani on 4/24/16.
 */
public class UserDataUtil {
    private static final UserDataUtil userDataUtil = new UserDataUtil();

    private UserDataUtil(){

    }

    public static UserDataUtil getInstance(){
        return userDataUtil;
    }
    private User user;


    public List<String> getFavorites(){
        if(null != user) {
            return user.getFavourites();
        }
        return null;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getUserEmailId() throws Exception{
        if(null != user){
            return user.getEmail();
        }
        throw new Exception("Missing user");
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void clear() {
        user.setEmail(null);
        user.setPassword(null);
        user = null;
    }

    public void removeFavorite(String beaconId) {
        Iterator itr = user.getFavourites().iterator();
        while(itr.hasNext()){
            if(itr.next().equals(beaconId)){
                itr.remove();
                break;
            }
        }
    }

    public void addFavorite(Spot spot){
        user.getFavourites().add(spot.getBeaconId());
    }
}
