package com.makemoney2023.online;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.makemoney2023.online.chat.ChatMainActivity;
import com.makemoney2023.online.image.ImageMainActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    CardView chat,image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout constraintLayout = findViewById(R.id.back);

        AnimationDrawable animationDrawable  = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        chat = findViewById(R.id.lets_chat);
        image = findViewById(R.id.generate_image);


        chat.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, ChatMainActivity.class));
        });

        image.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, ImageMainActivity.class));
        });



        setNav();


    }

    private void setNav() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);


        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


//    ================ Sidebar =============


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_about) {
//            Intent openAbout = new Intent(getApplicationContext(), About.class);
//            startActivity(openAbout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_about) {
            Uri rate = Uri.parse("https://swaradeveloperspvt.blogspot.com/2021/10/privacy-policy.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, rate);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

            Intent shareIt = new Intent(Intent.ACTION_SEND);
            shareIt.setType("text/plain");
            String body = "Hey there, try this new app to make easy money. Watch and earn is a free app which pays users just to watch ads. Not just that, it also gives you bonuses everyday. Click this link to download it - https://bit.ly/2R2risM";
            shareIt.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(shareIt, "Share using"));


        } else if (id == R.id.nav_rate) {
            Uri rate = Uri.parse("https://t.me/joinchat/j8TcmeEa45EwYmRl");
            Intent intent = new Intent(Intent.ACTION_VIEW, rate);
            startActivity(intent);


//        } else if (id == R.id.nav_contact) {
//            Intent contact = new Intent(Intent.ACTION_SENDTO);
//            contact.setData(Uri.parse("mailto:swaradeveloperspvt@gmail.com"));
//            startActivity(contact);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}