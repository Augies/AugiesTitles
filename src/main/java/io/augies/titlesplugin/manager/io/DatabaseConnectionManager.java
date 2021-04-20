package io.augies.titlesplugin.manager.io;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.DatabaseConnectionConfiguration;
import io.augies.titlesplugin.util.DataUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private final TitlesPlugin plugin;
    private final String connectionString;
    private final DatabaseConnectionConfiguration databaseConnectionConfiguration;

    public DatabaseConnectionManager(){
        plugin = TitlesPlugin.getInstance();
        databaseConnectionConfiguration = plugin.getJsonManager().getDatabaseConnectionConfiguration();
        String connString = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connString = DataUtils.getConnectionString(databaseConnectionConfiguration);
            canSuccessfullyConnect();
        }catch(Exception e){
            TitlesPlugin.getInstance().logError("FAILED TO LOAD JDBC DRIVER NOTHING WILL WORK");
            Bukkit.getServer().getPluginManager().disablePlugin(TitlesPlugin.getInstance());
        }
        connectionString = connString;
    }

    public boolean canSuccessfullyConnect(){
        try {
            DriverManager.getConnection(connectionString).close();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            plugin.logError("Could not get conection for database.");
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Run a query in the database. Only use this for initialization, as it returns nothing.
     * @param query the query to execute
     */
    public void runQuery(String query){
        Connection connection = getConnection();
        try {
            connection.createStatement().execute(query);
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
