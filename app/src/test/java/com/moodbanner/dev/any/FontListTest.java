package com.moodbanner.dev.any;

import com.moodbanner.dev.any.Text.Font.FontList;
import com.moodbanner.dev.any.Text.Font.ValueFont;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FontListTest {


    private FontList mFontList;

    @Before
    public void setUp() {
        mFontList = new FontList();
    }

    @Test
    public void testFontList() {

        String[] filesPaths = { "fonts/arial.ttf", "fonts/times_new_roman.ttf" };
        List<ValueFont> result = mFontList.filesToValueFont(filesPaths, "fonts");

        Assert.assertEquals(result.size(), filesPaths.length);

        for (ValueFont aFile : result) {
            Assert.assertNotNull(aFile.getFontFile());
            Assert.assertNotNull(aFile.getFontThumb());
            Assert.assertNotNull(aFile.getName());
        }

    }
}