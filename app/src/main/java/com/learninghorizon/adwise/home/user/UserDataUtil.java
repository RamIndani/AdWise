package com.learninghorizon.adwise.home.user;

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

    public void setUser(User user){
        this.user = user;
    }

    public String getUserEmailId() throws Exception{
        if(null != user){
            return user.getEmail();
        }
        throw new Exception("Missing user");
    }

}
