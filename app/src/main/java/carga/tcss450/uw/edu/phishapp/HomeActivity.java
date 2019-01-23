package carga.tcss450.uw.edu.phishapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import carga.tcss450.uw.edu.phishapp.blog.BlogPost;
import carga.tcss450.uw.edu.phishapp.model.Credentials;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SuccessFragment.OnSuccessFragmentInteractionListener, BlogFragment.OnBlogListFragmentInteractionListener, BlogPostFragment.OnBlogPostFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Credentials credentials = (Credentials) getIntent().getExtras().getSerializable(getString(R.string.key_credentials_object));
            SuccessFragment successFragment = new SuccessFragment();
            Bundle args = new Bundle();
            args.putSerializable(getString(R.string.key_credentials_object), credentials);
            successFragment.setArguments(args);
            loadFragment(successFragment);
        } else if (id == R.id.nav_blog_posts) {
            loadFragment(new BlogFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSuccessFragmentInteraction(Credentials credentials) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Credentials credentials = (Credentials) getIntent().getExtras().getSerializable(getString(R.string.key_credentials_object));

        SuccessFragment successFragment = new SuccessFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.key_credentials_object), credentials);
        successFragment.setArguments(args);

            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, successFragment)
                        .commit();
            }
    }

    private void loadFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    // The blog fucking fragment
    @Override
    public void onBlogListFragmentInteraction(BlogPost item) {

        BlogPostFragment blogPostFragment = new BlogPostFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.key_blog_post_object), item);
        blogPostFragment.setArguments(args);

        if (findViewById(R.id.fragmentContainer) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, blogPostFragment)
                    .addToBackStack(null)
                    .commit();

        }

    }

    @Override
    public void onBlogPostFragmentInteraction(View view) {

    }
}
