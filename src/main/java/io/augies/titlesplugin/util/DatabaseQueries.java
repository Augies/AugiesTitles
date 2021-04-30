package io.augies.titlesplugin.util;

public class DatabaseQueries {
    public static final String IF_SCHEMA_EXISTS = "SELECT 1 FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'augies_titles'";
}
