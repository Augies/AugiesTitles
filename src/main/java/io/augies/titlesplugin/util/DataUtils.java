package io.augies.titlesplugin.util;

import io.augies.titlesplugin.config.StorageConfiguration;

public class DataUtils {
    public static String getConnectionString(StorageConfiguration storageConfiguration){
        return String.format("jdbc:mysql://%s:%d?user=%s&password=%s",
                storageConfiguration.address,
                storageConfiguration.port,
                storageConfiguration.username,
                storageConfiguration.password
        );
    }
}
