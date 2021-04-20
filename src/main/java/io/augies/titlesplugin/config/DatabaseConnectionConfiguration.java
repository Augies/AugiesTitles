package io.augies.titlesplugin.config;

public class DatabaseConnectionConfiguration {
    private static final String jsonPath = "databaseConfig";
    public int port = 3306;
    public String address = "localhost";
    public String schemaName = "augiestitles";
    public String username = "root";
    public String password = "password";

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
