package com.glleung.parkinggarageautomation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 3/26/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BackendTest extends ActivityInstrumentationTestCase2<MainLogin>{
    public BackendTest() {
        super(MainLogin.class);
    }

    Activity mMainActivity;

   @Before
   public void setUp() throws Exception {
       super.setUp();

       // Injecting the Instrumentation instance is required
       // for your test to run with AndroidJUnitRunner.
       injectInstrumentation(InstrumentationRegistry.getInstrumentation());
       mMainActivity = getActivity();
   }

    static boolean handled;

    @Test
    public void TestLoginWithCorrectInfo() {
        Firebase.setAndroidContext(mMainActivity);
        final Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/");
        handled = false;
        ref.authWithPassword("test0@rutgers.edu", "defaultpass",
                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        handled = true;
                        assertTrue(true);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        handled = true;
                        Assert.assertTrue(false);
                    }
                });
        while(!handled) {
            //Log.d("UNIT TEST", "PASSED THE TEST");
            assertTrue(true);
        }
    }

    @Test
    public void TestLoginWithIncorrectInfo() {
        Firebase.setAndroidContext(mMainActivity);
        final Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/");
        handled = false;
        ref.authWithPassword("test0@rutgers.edu", "defaultpass2",
                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        handled = true;
                        assertTrue(false);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        handled = true;
                        Assert.assertTrue(true);
                    }
                });
        while(!handled) {
            //Log.d("UNIT TEST", "PASSED THE TEST");
            assertTrue(true);
        }
    }

    @Test
    public void TestVehicleRegistration() {
        Firebase.setAndroidContext(mMainActivity);
        handled = false;
        Firebase firebaseCount = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
        firebaseCount.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                count++;
                Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/"+count);

                Map<String,String> map = new HashMap<String, String>();
                map.put("color","n/a");
                map.put("licenseplate","012345");
                map.put("make","testmake");
                map.put("model","testmodel");
                map.put("year","testyear");
                map.put("user","28fb0ac5-37f5-46a0-bc11-16f8f05b3247");
                ref.setValue(map);

                Firebase countref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
                countref.child("count").setValue(count);

                Firebase userref = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/" + MainLogin.currentUID);
                userref.child("vehicles").push().setValue(count);

                countref.child("" + count).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String testmake = (String) dataSnapshot.child("make").getValue();
                        assertEquals("testmake",testmake);
                        Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/28fb0ac5-37f5-46a0-bc11-16f8f05b3247/vehicles");
                        ref.removeValue();
                        handled = true;
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        assertTrue(false);
                    }
                });

                Firebase vehicleref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/"+count);
                vehicleref.removeValue();

                Firebase countref2 = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
                countref2.child("count").setValue(--count);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                assertTrue(false);
            }
        });
        while(!handled) {
            //Log.d("UNIT TEST", "PASSED THE TEST");
            assertTrue(true);
        }
    }

    @Test
    public void TestVehicleRemoval() {
        Firebase.setAndroidContext(mMainActivity);
        handled = false;
        Firebase firebaseCount = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
        firebaseCount.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                count++;
                Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/"+count);
                ref.removeValue();

                Firebase vehicleref = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/28fb0ac5-37f5-46a0-bc11-16f8f05b3247/vehicles");
                vehicleref.removeValue();

                Firebase countref2 = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
                countref2.child("count").setValue(--count);
                handled = true;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                assertTrue(false);
            }
        });
        while(!handled) {
            //Log.d("UNIT TEST", "PASSED THE TEST");
            assertTrue(true);
        }
    }
}
