package io.augies.titlesplugin.config;

public class DatabaseConnectionConfiguration {
    public int port = 3306;
    public String address = "localhost";
    public String username = "root";
    public String password = "password";

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
