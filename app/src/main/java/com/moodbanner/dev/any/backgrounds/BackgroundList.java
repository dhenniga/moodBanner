package com.moodbanner.dev.any.backgrounds;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhen0003 on 23/03/16.
 */
public class BackgroundList extends Activity {

    public List<ValueBackground> listInternalDirectoryBackgrounds (Context context, String directory) {

        AssetManager am = context.getAssets();

        String[] files = {};

        try {
            files = am.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ValueBackground> listBackgrounds = filesToValueBackground(files, directory);
        return listBackgrounds;
    }

    /**
     * @param files
     * @return
     */
    public List<ValueBackground> filesToValueBackground(String[] files, String directory) {
        List<ValueBackground> backgroundList = new ArrayList<>();

        for (String aFilePath : files) { // for each relative path!
            if (aFilePath.toLowerCase().endsWith(".jpg")) {
                ValueBackground valueBackground = new ValueBackground();

                // ----------------
                // get the name
                String[] pathTokens = aFilePath.split("/");

                String fileNameWithExtension = pathTokens[pathTokens.length - 1];
                String[] fileNameAndExtensionTokens = fileNameWithExtension.split("\\.");

                String fileName = fileNameAndExtensionTokens[0];

                valueBackground.setName(fileName);
                // ----------------

                // file Name (with extension)
                valueBackground.setImageURL("file:///android_asset/" + directory + "/" + fileNameWithExtension);


                backgroundList.add(valueBackground);

            }
        }

        return backgroundList;
    }





    /**
     *
     * Returns a list from the internal Camera DCIM folder.  Need to create a couple of different
     * variations to be more compatible with different mobile devices.
     *
     * @return
     */
    public List<ValueBackground> listCameraDirectoryImages() {

        File dcimDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        File dir = new File(dcimDirectory, "/Camera/");
        File[] images = dir.listFiles();

        List<ValueBackground> listBackgrounds = filesCameraToValueBackground(images, dir.toString());

        return listBackgrounds;

    }


    /**
     *
     * @param files
     * @param directory
     * @return
     */
    public List<ValueBackground> filesCameraToValueBackground(File[] files, String directory) {
        List<ValueBackground> backgroundList = new ArrayList<>();

        for (File aFilePath : files) { // for each relative path!

            if (aFilePath.toString().toLowerCase().endsWith(".jpg")) {

                ValueBackground valueBackground = new ValueBackground();

                String[] pathTokens = aFilePath.toString().split("/");
                String fileNameWithExtension = pathTokens[pathTokens.length - 1];
                String[] fileNameAndExtensionTokens = fileNameWithExtension.split("\\.");
                String fileName = fileNameAndExtensionTokens[0];

                valueBackground.setName(fileName);
                valueBackground.setImageURL("file:///" + directory + "/" + fileNameWithExtension);

                backgroundList.add(valueBackground);
            }
        }

        return backgroundList;
    }

}