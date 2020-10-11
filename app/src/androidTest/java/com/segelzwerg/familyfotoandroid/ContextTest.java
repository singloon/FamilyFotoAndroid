package com.segelzwerg.familyfotoandroid;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ContextTest {
    /**
     * Use app context.
     */
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.segelzwerg.familyfotoandroid", appContext.getPackageName());
    }
}