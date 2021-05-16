package io.augies.titlesplugin.manager.io.storage;

import io.augies.titlesplugin.manager.io.JsonManager;
import io.augies.titlesplugin.model.PlayerTitles;
import io.augies.titlesplugin.model.PlayerToken;
import io.augies.titlesplugin.model.Title;
import io.augies.titlesplugin.model.container.PlayerTitlesContainer;
import io.augies.titlesplugin.model.container.PlayerTokenContainer;
import io.augies.titlesplugin.model.container.TitleContainer;
import io.augies.titlesplugin.util.IoUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonStorageManager extends StorageTypeManager {
    private static final String PLAYER_TITLES_FILE_PATH = "playerTitles.json";
    private static final String PLAYER_TOKENS_FILE_PATH = "playerTokens.json";
    private static final String TITLES_FILE_PATH = "titles.json";

    private List<Title> titles;

    private File storageFolder;
    private JsonManager jsonManager;
    private File playerTitlesFile;
    private File playerTokensFile;
    private File titlesFile;

    @Override
    public void onEnable() {
        storageFolder = new File(IoUtils.getPathOfFileInFolder(plugin.getDataFolder(), "data"));
        jsonManager = plugin.getJsonManager();
        confirmInitialization();

        playerTitlesFile = new File(IoUtils.getPathOfFileInFolder(storageFolder, PLAYER_TITLES_FILE_PATH));
        playerTokensFile = new File(IoUtils.getPathOfFileInFolder(storageFolder, PLAYER_TOKENS_FILE_PATH));
        titlesFile = new File(IoUtils.getPathOfFileInFolder(storageFolder, TITLES_FILE_PATH));

        //Confirm files can load properly. Create defaults if necessary
        jsonManager.loadJson(playerTitlesFile.getPath(), PlayerTitlesContainer.class);
        jsonManager.loadJson(playerTokensFile.getPath(), PlayerTokenContainer.class);
        jsonManager.loadJson(titlesFile.getPath(), TitleContainer.class);

        loadTitles(); //Grab the titles from local storage and load to memory
    }

    @Override
    public void onDisable() {
        saveTitles();
        playerTitlesFile = null;
        playerTokensFile = null;
        titlesFile = null;
        jsonManager = null;
    }

    private boolean saveTitles() {
        TitleContainer titleContainer = new TitleContainer();
        titleContainer.titlesList = titles;
        return jsonManager.saveJson(titlesFile.getPath(), titleContainer);
    }

    private void loadTitles() {
        TitleContainer titleContainer = jsonManager.loadJson(titlesFile.getPath(), TitleContainer.class);
        if (titleContainer == null) {
            plugin.logError("FAILED TO LOAD TITLES FROM LOCAL JSON STORAGE! Unloading plugin...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        } else {
            titles = titleContainer.titlesList;
        }
    }

    @Override
    public List<Title> getAllTitles() {
        return titles;
    }

    @Override
    public List<Title> getTitlesForPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        List<PlayerTitles> playerTitlesList = jsonManager.loadJson(playerTitlesFile.getPath(), PlayerTitlesContainer.class).playerTitlesList;
        PlayerTitles playerTitles = playerTitlesList.stream().filter(playerTitle -> playerTitle.playerUuid.equals(uuid)).findFirst().orElse(null);
        if(playerTitles==null){
            return null;
        }

        List<Title> titles = new ArrayList<>();
        for(String identifier : playerTitles.titleIdentifiers){
            titles.add(getTitle(identifier));
        }

        return titles;
    }

    @Override
    public List<PlayerToken> getAllPlayerTokens() {
        return jsonManager.loadJson(playerTokensFile, PlayerTokenContainer.class).playerTokenList;
    }

    @Override
    public int getTokensForPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        List<PlayerToken> playerTokens = jsonManager.loadJson(playerTokensFile, PlayerTokenContainer.class).playerTokenList;
        PlayerToken playerToken = playerTokens.stream().filter(token -> token.playerUuid.equals(uuid)).findFirst().orElse(null);

        return playerToken != null ? playerToken.tokens : 0;
    }

    @Override
    public boolean setTokensForPlayer(Player player, int amount) {
        String uuid = player.getUniqueId().toString();
        List<PlayerToken> playerTokens = jsonManager.loadJson(playerTokensFile, PlayerTokenContainer.class).playerTokenList;
        PlayerToken playerToken = playerTokens.stream().filter(pt -> pt.playerUuid.equals(uuid)).findFirst().orElse(null);

        if (playerToken == null) {
            playerToken = new PlayerToken();
            playerToken.playerUuid = uuid;
        } else {
            playerTokens.remove(playerToken);
        }
        playerToken.tokens = amount;
        playerTokens.add(playerToken);
        PlayerTokenContainer playerTokenContainer = new PlayerTokenContainer();
        playerTokenContainer.playerTokenList = playerTokens;

        return jsonManager.saveJson(playerTokensFile.getPath(), playerTokenContainer);
    }

    @Override
    public Title getTitle(String identifier) {
        return titles.stream().filter(title -> title.identifier.equals(identifier)).findFirst().orElse(null);
    }

    @Override
    public boolean createTitle(String identifier, String title) {
        if (getTitle(identifier) != null) {
            plugin.logWarning("Tried creating title with identifier " + identifier + " but that already exists!");
            return false;
        }

        Title titleObject = new Title();
        titleObject.identifier = identifier;
        titleObject.title = title;
        titles.add(titleObject);

        return saveTitles();
    }

    @Override
    public boolean createGroupTitle(String groupName, String title) {//TODO is String best way to identify a group?
        if (getTitle(groupName) != null) {
            plugin.logWarning("Tried creating a title for group " + groupName + " but that already exists!");
            return false;
        }

        Title titleObject = new Title();
        titleObject.identifier = groupName;
        titleObject.isGroupTitle = true;
        titleObject.title = title;
        titles.add(titleObject);

        return saveTitles();
    }

    @Override
    public boolean removeTitle(String identifier) {
        Title title = getTitle(identifier);
        if (title == null) {
            plugin.logWarning("Tried removing title with identifier " + identifier + " but that doesn't exist!");
            return false;
        }
        titles.remove(title);
        return saveTitles();
    }

    @Override
    protected void confirmInitialization() {
        if (!storageFolder.exists()) {
            storageFolder.mkdir();
        }
    }
}
