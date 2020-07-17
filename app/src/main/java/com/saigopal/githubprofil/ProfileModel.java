package com.saigopal.githubprofil;

public class ProfileModel {
    String UserID;
    String AvatarURL;

    public ProfileModel(String userID, String avatarURL) {
        UserID = userID;
        AvatarURL = avatarURL;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "UserID='" + UserID + '\'' +
                ", AvatarURL='" + AvatarURL + '\'' +
                '}';
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getAvatarURL() {
        return AvatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        AvatarURL = avatarURL;
    }
}
