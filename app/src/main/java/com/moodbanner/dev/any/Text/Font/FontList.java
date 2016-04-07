package com.moodbanner.dev.any.Text.Font;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhen0003 on 23/03/16.
 */
public class FontList extends Activity {

    public List<ValueFont> listDirectoryFonts (Context context, String directory) {

        AssetManager am = context.getAssets();

        String[] files = {};

        try {
            files = am.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ValueFont> listFonts = filesToValueFont(files, directory);
        return listFonts;
    }

    /**
     * @param files
     * @return
     */
    public List<ValueFont> filesToValueFont(String[] files, String directory) {
        List<ValueFont> fontList = new ArrayList<>();

        for (String aFilePath : files) { // for each relative path!
            if (aFilePath.toLowerCase().endsWith(".otf")) {
                ValueFont valueFont = new ValueFont();

                // ----------------
                // get the name
                String[] pathTokens = aFilePath.split("/");

                String fileNameWithExtension = pathTokens[pathTokens.length - 1];
                String[] fileNameAndExtensionTokens = fileNameWithExtension.split("\\.");

                String fileName = fileNameAndExtensionTokens[0];

                valueFont.setName(fileName);
                // ----------------

                // file Name (with extension)
                valueFont.setFontFile(directory + "/" + fileNameWithExtension);

                // file extension
                valueFont.setFontThumb("file:///android_asset/" + directory + "/" + fileName + ".png");


                fontList.add(valueFont);
                // valueFont.setName(aFilePath);

            }
        }

        return fontList;
    }
}
