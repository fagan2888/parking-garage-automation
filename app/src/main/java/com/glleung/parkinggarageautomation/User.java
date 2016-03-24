package com.glleung.parkinggarageautomation;

/**
 * Created by deanrexines on 3/2/16.
 */
public class User {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
        private Vehicle vehicle;

        public User() {}

        public User(String firstName,
                    String lastName,
                    String email,
                    String phone,
                    String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }

        public Vehicle getCar(){
            return vehicle;
        }
}//end User

