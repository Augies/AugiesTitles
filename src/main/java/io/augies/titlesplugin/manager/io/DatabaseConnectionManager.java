package io.augies.titlesplugin.manager.io;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.DatabaseConnectionConfiguration;
import io.augies.titlesplugin.util.DataUtils;
import io.augies.titlesplugin.util.DatabaseQueries;
import io.augies.titlesplugin.util.IoUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConnectionManager {
    private final TitlesPlugin plugin;
    private String connectionString = "";
    private Connection connection = null;

    public DatabaseConnectionManager(){
        plugin = TitlesPlugin.getInstance();
        DatabaseConnectionConfiguration databaseConnectionConfiguration = plugin.getJsonManager().getDatabaseConnectionConfiguration();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connectionString = DataUtils.getConnectionString(databaseConnectionConfiguration);
            if(canSuccessfullyConnect()){
                plugin.logInfo("Successfully connected to database");
            }else{
                plugin.logError("FAILED TO CONNECT TO THE DATABASE. UNLOADING PLUGIN");
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            }

            confirmInitialization();
        }catch(Exception e){
            e.printStackTrace();
            TitlesPlugin.getInstance().logError("FAILED TO LOAD JDBC DRIVER NOTHING WILL WORK");
            Bukkit.getServer().getPluginManager().disablePlugin(TitlesPlugin.getInstance());
        }
    }

    private boolean canSuccessfullyConnect(){
        try {
            connection = DriverManager.getConnection(connectionString);
            return true;
        } catch (SQLException e) {
            plugin.logError(e.getMessage());
            return false;
        }
    }

    /**
     * Queries the database to confirm whether or not the database has already been initialized.
     * If not, it gets initialized.
     */
    private void confirmInitialization(){
        boolean didExist = false;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(DatabaseQueries.IF_SCHEMA_EXISTS);
            if(resultSet.next()){
                didExist = resultSet.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(!didExist){
            initializeSchema();
        }
    }

    private void initializeSchema(){
        plugin.logInfo("Initializing Database...");

        String queryFileContents = IoUtils.getContentsOfResource("db_initialization.sql");

        List<String> queries = Arrays.asList(queryFileContents.split(";"));
        queries = queries.stream().map(String::trim).collect(Collectors.toList()); //trim each one

        for(String query : queries){
            runQuery(query);
        }
        plugin.logInfo("Successfully Initialized Database");
    }

    /**
     * Run a query in the database. Only use this for initialization, as it returns nothing.
     * @param query the query to execute
     */
    public void runQuery(String query){
        try {
            connection.createStatement().execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
