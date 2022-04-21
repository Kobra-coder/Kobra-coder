package com.arglobal.arcloudanchors;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.ustglobal.arcloudanchors.R;

public class Home extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    public static final String LEK205 = "lek205";
    public static final String LEK211 = "lek211";
    public static final String DECANAT = "decanat";
    public static final String KAFEDRA = "kafedra";

    public static final String FROM = "from";
    public static final String MODE = "mode";
    public String userMode = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher_activiy);

        ImageButton settingsBtn = findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getApplicationContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_main ,popup.getMenu());
            popup.setOnMenuItemClickListener(Home.this::onMenuItemClick);
            popup.show();
        });

        ImageButton electBtn = findViewById(R.id.lek205);
        ImageButton toysBtn = findViewById(R.id.lek211);
        ImageButton tvBtn = findViewById(R.id.Decanat);
        ImageButton clothingBtn = findViewById(R.id.Kafedra);
        electBtn.setOnClickListener(this);
        toysBtn.setOnClickListener(this);
        tvBtn.setOnClickListener(this);
        clothingBtn.setOnClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                userMode = "user";
                return true;
            case R.id.item2:
                userMode = "admin";
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lek205:
                goToCameraActivity(LEK205);
                break;
            case R.id.lek211:
                goToCameraActivity(LEK211);
                break;
            case R.id.Decanat:
                goToCameraActivity(DECANAT);
                break;
            case R.id.Kafedra:
                goToCameraActivity(KAFEDRA);
                break;

        }
    }

    private void goToCameraActivity(String Section) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra(FROM, Section);
        i.putExtra(MODE,userMode);
        startActivity(i);
    }
}
