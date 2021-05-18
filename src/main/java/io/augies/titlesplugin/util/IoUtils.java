package io.augies.titlesplugin.util;

import io.augies.titlesplugin.TitlesPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class IoUtils {

    /**
     * Gets the absolute path for a given file within a folder
     * @param folder the File object of a directory
     * @param fileName the name of the file within a directory
     * @return the path string
     */
    public static String getPathOfFileInFolder(File folder, String fileName){
        return folder.getPath() + "\\" + fileName;
    }

    public static String getContentsOfResource(String resource){
        InputStream inputStream = TitlesPlugin.getInstance().getResource(resource);

        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
