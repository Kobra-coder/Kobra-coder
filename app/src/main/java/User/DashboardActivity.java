package User;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import androidx.appcompat.app.AppCompatActivity;
import com.arglobal.arcloudanchors.Home;
import com.ustglobal.arcloudanchors.R;

import mapdemo.DemoMaps;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    Button guide_b;
    Button nav_b;
    Button map_b;
    Button about_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //**********************************************************//
        setContentView(R.layout.dashboard_activity);
        guide_b = (Button) findViewById(R.id.guide);
        guide_b.setOnClickListener(this);
        nav_b= (Button) findViewById(R.id.nav);
        nav_b.setOnClickListener(this);
        map_b = (Button) findViewById(R.id.map);
        map_b.setOnClickListener(this);
        about_b = (Button) findViewById(R.id.about);
        about_b.setOnClickListener(this);

        //For background animation
        ImageSwitcher imageSwitcher = findViewById(R.id.imageSwitcher3);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageSwitcher.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide:
                Intent intent = new Intent(this, GuideActivity.class);
                startActivity(intent);

                break;

            default:
                break;

        }

        switch (view.getId()) {
            case R.id.nav:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);

                break;

            default:
                break;
        }
        switch (view.getId()) {
            case R.id.map:
                Intent intent = new Intent(this, DemoMaps.class);
                startActivity(intent);

                break;

            default:
                break;
        }

        switch (view.getId()) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }

    }
}