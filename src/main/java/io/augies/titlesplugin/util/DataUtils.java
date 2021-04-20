package io.augies.titlesplugin.util;

import io.augies.titlesplugin.config.DatabaseConnectionConfiguration;

public class DataUtils {
    public static String getConnectionString(DatabaseConnectionConfiguration databaseConnectionConfiguration){
        return String.format("jdbc:mysql://%s:%d?user=%s&password=%s",
                databaseConnectionConfiguration.getAddress(),
                databaseConnectionConfiguration.getPort(),
                databaseConnectionConfiguration.getUsername(),
                databaseConnectionConfiguration.getPassword()
        );
    }
}
