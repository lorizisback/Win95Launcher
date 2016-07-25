package com.winlauncher.loriz.win95launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private boolean isOpen = false;
    private ImageView startNormal;
    private RelativeLayout startMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNormal = (ImageView) findViewById(R.id.start_button);
        startMenu = (RelativeLayout) findViewById(R.id.start_menu_container);

        View.OnClickListener clickList = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen = startToggler();
            }
        };

        startNormal.setOnClickListener(clickList);



    }

    private boolean startToggler()
    {
        if (isOpen == false) {
            startNormal.setImageResource(R.drawable.start_pressed);
            startMenu.setVisibility(View.VISIBLE);

        } else {
            startNormal.setImageResource(R.drawable.start_unpressed);
            startMenu.setVisibility(View.INVISIBLE);
        }

        return !isOpen;

    }

    @Override
    public void onBackPressed() {
        if (isOpen == true) isOpen = startToggler();
    }
}
