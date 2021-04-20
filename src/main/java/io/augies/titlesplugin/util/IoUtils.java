package io.augies.titlesplugin.util;

import java.io.File;

public class IoUtils {

    /**
     * Gets the absolute path for a given file within a folder
     * @param folder the File object of a directory
     * @param fileName the name of the file within a directory
     * @return the path string
     */
    public static String getPathOfFileInFolder(File folder, String fileName){
        return folder.getPath() + "/" + fileName;
    }
}
