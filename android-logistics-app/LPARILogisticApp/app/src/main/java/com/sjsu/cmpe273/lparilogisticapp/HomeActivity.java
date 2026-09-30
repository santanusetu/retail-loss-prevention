package com.sjsu.cmpe273.lparilogisticapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sjsu.cmpe273.lparilogisticapp.fragments.FragmentAnalytics;
import com.sjsu.cmpe273.lparilogisticapp.fragments.FragmentShipmentCompleted;
import com.sjsu.cmpe273.lparilogisticapp.fragments.FragmentShipmentLead;


public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    NavigationView nvDrawer;
    ActionBarDrawerToggle drawerToggle;

    // TODO: changing for testing -- santanu
    public static boolean isLoggedIn = false;

    /** Set by the signature screen: after a delivery, clear the delivery flow and show Completed. */
    public static final String EXTRA_SHOW_COMPLETED = "showCompleted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //For Testing purpose -- made true

        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Shipments");


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);


        // Find our navigation view and Setup drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        // Inflate the header view at runtime
        //View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);

        // Setting up the first Screen
       // FragmentShipment shipmentFragment = new FragmentShipment();
       // setHomeFragment(shipmentFragment);

        // Only on a fresh start; after rotation the fragment manager restores the screen itself
        if (savedInstanceState == null) {
            setHomeFragment(new FragmentShipmentLead());
        }


        //floating action button
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(EXTRA_SHOW_COMPLETED, false)) {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            selectDrawerItem(nvDrawer.getMenu().findItem(R.id.nav_second_fragment));
        }
    }

    /*
       Function that sets the initial Fragment in Screen
    */
    public void setHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.flContent, fragment)
                .commit();
    }


    /*
   Function to set Hamburger Toggle in top in action bar
    */
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    /*
     Function to show fragment based upon position
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        // Resource IDs are not compile-time constants under current Android tooling, so no switch
        Class fragmentClass;
        int id = menuItem.getItemId();
        if (id == R.id.nav_first_fragment) {
            fragmentClass = FragmentShipmentLead.class;
        } else if (id == R.id.nav_second_fragment) {
            fragmentClass = FragmentShipmentCompleted.class;
        } else {
            fragmentClass = FragmentAnalytics.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        // this will not keep track of the fragment stack
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        // added the transaction to the back stack so the user can navigate back
        // transaction.addToBackStack(null);
        transaction.commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        //menuItem.setEnabled(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }



   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Toast.makeText(this, "Toast Scanned HOME ACTIVITY", Toast.LENGTH_SHORT).show();

       // if (requestCode == 12345) {
            Toast.makeText(this, " Scanned ABCD ", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "Toast Scanned ", Toast.LENGTH_SHORT).show();
            // Handle successful scan

        //}
    }*/

}
