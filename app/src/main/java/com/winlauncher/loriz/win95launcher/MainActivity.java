package com.winlauncher.loriz.win95launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.febaisi.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private boolean isOpen = false;
    private ImageView startNormal;
    private RelativeLayout startMenu;
    BroadcastReceiver broadcastReceiver;
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private CustomTextView clockTextview;
    private LinearLayout taskbarContainer;
    private int menuHeight;
    private int menuWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNormal = (ImageView) findViewById(R.id.start_button);
        startMenu = (RelativeLayout) findViewById(R.id.start_menu_container);
        taskbarContainer = (LinearLayout) findViewById(R.id.taskbar_container);
        clockTextview = (CustomTextView) findViewById(R.id.clock_textview);
        clockTextview.getPaint().setAntiAlias(false);

        View.OnClickListener clickList = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen = startToggler();
            }
        };

        startNormal.setOnClickListener(clickList);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        menuHeight = (size.y*2)/3;
        menuWidth = (size.x*2)/3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(menuWidth,menuHeight);
        params.addRule(RelativeLayout.ABOVE,R.id.bottom_bar_container);
        params.setMargins(7,0,0,-8);

        startMenu.setLayoutParams(params);



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


    @Override
    public void onStart() {
        super.onStart();
        clockTextview.setText(sdf.format(new Date()));
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    clockTextview.setText(sdf.format(new Date()));
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

}
