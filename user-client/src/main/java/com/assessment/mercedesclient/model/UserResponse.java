package com.assessment.mercedesclient.model;

public class UserResponse {
    private User user;

    public UserResponse(User user) {
        this.user = user;
    }


    UserResponse(){

    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
