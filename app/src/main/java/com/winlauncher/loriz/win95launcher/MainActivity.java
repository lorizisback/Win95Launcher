package com.winlauncher.loriz.win95launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private boolean isStartOpen = false;
    private ImageView startNormal;
    private ImageView startPressed;
    private RelativeLayout startMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNormal = (ImageView) findViewById(R.id.start_button_normal);
        startPressed = (ImageView) findViewById(R.id.start_button_pressed);
        startMenu = (RelativeLayout) findViewById(R.id.start_menu_container);

        View.OnClickListener clickList = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartOpen = startToggler();
            }
        };

        startNormal.setOnClickListener(clickList);
        startPressed.setOnClickListener(clickList);



    }

    private boolean startToggler()
    {
        if (isStartOpen) {
            startNormal.setVisibility(View.INVISIBLE);
            startPressed.setVisibility(View.VISIBLE);
            startMenu.setVisibility(View.INVISIBLE);

        } else {
            startNormal.setVisibility(View.VISIBLE);
            startPressed.setVisibility(View.INVISIBLE);
            startMenu.setVisibility(View.VISIBLE);
        }

        return !isStartOpen;

    }

}
