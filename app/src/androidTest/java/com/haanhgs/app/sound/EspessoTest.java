package com.haanhgs.app.sound;

import android.content.Context;
import com.haanhgs.app.sound.view.MainActivity;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4ClassRunner.class)
public class EspessoTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.haanhgs.app.sound", appContext.getPackageName());
    }

    @Test
    public void iterateSpinnerItems() {
        String[] myArray = activityTestRule.getActivity().getResources()
                        .getStringArray(R.array.spinner);

        int size = myArray.length;
        for (String s : myArray) {
            onView(withId(R.id.spRepeat)).perform(click());
            onData(is(s)).perform(click());
        }

    }







}
