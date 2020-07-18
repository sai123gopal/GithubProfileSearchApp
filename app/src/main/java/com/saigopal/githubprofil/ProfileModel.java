package com.saigopal.githubprofil;

public class ProfileModel {
    String UserID;
    String AvatarURL;
    String URL;

    public ProfileModel(String userID, String avatarURL, String URL) {
        UserID = userID;
        AvatarURL = avatarURL;
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "UserID='" + UserID + '\'' +
                ", AvatarURL='" + AvatarURL + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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
