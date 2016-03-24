package com.glleung.parkinggarageautomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class MainLogin extends ActionBarActivity {

    Button loginButton;
    public static String currentUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        loginButton = (Button)findViewById(R.id.login);

        Button new_user = (Button)findViewById(R.id.new_user);
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, new_user.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/");

                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                ref.authWithPassword(username.getText().toString(), password.getText().toString(),
                        new Firebase.AuthResultHandler() {

                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authentication just completed successfully :)
                              /*  Map<String, String> map = new HashMap<String, String>();
                                map.put("provider", authData.getProvider());
                                if(authData.getProviderData().containsKey("displayName")) {
                                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                                }

                                //Not sure what this code does?
                                ref.child("users").child(authData.getUid()).setValue(map);*/

                                //Save the current user id for later use
                                MainLogin.currentUID = authData.getUid();

                                //Change activity
                                startActivity(new Intent(MainLogin.this, main.class));
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError error) {
                                // Something went wrong :(
                                Toast.makeText(MainLogin.this,"Could not login",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
