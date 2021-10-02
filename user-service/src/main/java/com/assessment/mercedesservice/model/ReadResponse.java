package com.assessment.mercedesservice.model;

import java.util.List;

public class ReadResponse {
        private List<User> users;
        public ReadResponse(){}
        public ReadResponse(List<User> users) {
            this.users = users;
        }
        public List<User> getUsers() {
            return users;
        }
        public void setUsers(List<User> users) {
            this.users = users;
        }
}
