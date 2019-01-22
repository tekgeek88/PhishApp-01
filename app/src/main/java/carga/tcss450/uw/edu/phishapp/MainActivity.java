package carga.tcss450.uw.edu.phishapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import carga.tcss450.uw.edu.phishapp.model.Credentials;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener, SuccessFragment.OnSuccessFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (findViewById(R.id.frame_main_container) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_main_container, new LoginFragment())
                        .commit();
            }
        }
    }


    @Override
    public void onLoginSuccess(Credentials credentials, String jwt) {

        SuccessFragment successFragment = new SuccessFragment();
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.key_login_success), credentials);
        successFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_container, successFragment);

        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onRegisterClicked() {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_container, new RegisterFragment())
                .addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onRegisterSuccess(Credentials credentials) {

        LoginFragment loginFragment = new LoginFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.key_register_success), credentials);
        loginFragment.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_container, loginFragment);

        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onSuccessFragmentInteraction(Credentials credentials) {
        // No code here because there is no interaction after a successful login
    }

}
