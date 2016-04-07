package com.moodbanner.dev.any.Backgrounds;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhen0003 on 23/03/16.
 */
public class BackgroundInternalList extends Activity {

    public List<ValueBackground> listDirectoryBackgrounds (Context context, String directory) {

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
//                valueBackground.setImageURL(Environment.getExternalStoragePublicDirectory(directory).toString() + "/" + fileNameWithExtension);

                backgroundList.add(valueBackground);
                // valueFont.setName(aFilePath);

            }
        }

        return backgroundList;
    }
}