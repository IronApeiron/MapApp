package com.mapapp.mpi.core.exec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import android.widget.TextView;
import com.mapapp.R;
import com.mapapp.mpi.core.exec.MainActivity;
import com.mapapp.activities.SignUpActivity;
import com.mapapp.mpi.ext.utils.Screen;


/**
 * @author Ganesh Ravendranathan
 *
 * TODO: MUST HIDE LOGIN ACTIVITY DETAILS ON PUBLIC API RELEASE!!!!!
 *
 * Last Edited 07/19/2013
 */
public class LoginActivity extends Activity {

    /**
     * The RatingBar that shows the connection quality between the device and the server.
     */
    private RatingBar rbConStat;

    protected static Context context;
    protected static LoginActivity instance;

    private static Activity sysServ;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login);

        Screen.showMessageDialog("Welcome!", "Sign-up if you haven't already, or else log in to connect with others!", this);

        LoginActivity.instance = this;
        LoginActivity.context = getApplicationContext();

        //Setup the environment variables
        //this.rbConStat = (RatingBar) findViewById(R.id.rbConStat);
    }

    /**
     * Called whenever the login button is clicked.
     *
     * @param view The view called.
     */
    public void onLoginClicked(View view){
        //Access the controls within the activity
        TextView cnet = (TextView) findViewById(R.id.txtConnect);
        ProgressBar pb = (ProgressBar) findViewById(R.id.pbConnect);

        //Set the progress bar and the textual connection status to be visible to the user while the app attempts to contact the server
        cnet.setVisibility(TextView.VISIBLE);
        pb.setVisibility(ProgressBar.VISIBLE);

        //This is where

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Called whenever the signup button is clicked.
     *
     * @param view The view called.
     */
    public void onSignupClicked(View view){
        Intent myIntent = new Intent(this, SignUpActivity.class);
        startActivity(myIntent);
    }


}
