package com.moodbanner.dev.any.Text.Texture;

        import android.app.Activity;
        import android.content.Context;
        import android.content.res.AssetManager;
        import android.graphics.BitmapFactory;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by dhen0003 on 23/03/16.
 */
public class TextureList extends Activity {

    public List<ValueTexture> listDirectoryTextures (Context context, String directory) {

        AssetManager am = context.getAssets();

        String[] files = {};

        try {
            files = am.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ValueTexture> listBackgrounds = filesToValueBackground(files, directory);
        return listBackgrounds;
    }

    /**
     * @param files
     * @return
     */
    public List<ValueTexture> filesToValueBackground(String[] files, String directory) {
        List<ValueTexture> textureList = new ArrayList<>();

        for (String aFilePath : files) { // for each relative path!
            if (aFilePath.toLowerCase().endsWith(".jpg")) {
                ValueTexture valueTexture = new ValueTexture();

                // ----------------
                // get the name
                String[] pathTokens = aFilePath.split("/");

                String fileNameWithExtension = pathTokens[pathTokens.length - 1];
                String[] fileNameAndExtensionTokens = fileNameWithExtension.split("\\.");

                String fileName = fileNameAndExtensionTokens[0];

                valueTexture.setName(fileName);
                // ----------------

                // file Name (with extension)
                valueTexture.setTextureFile("file:///android_asset/" + directory + "/" + fileNameWithExtension);

                valueTexture.setBitmapTexture("textures/" + fileNameWithExtension);

                textureList.add(valueTexture);

            }
        }

        return textureList;
    }
}