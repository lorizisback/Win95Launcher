package com.winlauncher.loriz.win95launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.febaisi.CustomTextView;
import com.winlauncher.loriz.win95launcher.adapters.ProgramsMenuAdapter;
import com.winlauncher.loriz.win95launcher.adapters.StartMenuAdapter;
import com.winlauncher.loriz.win95launcher.items.MenuEntry;
import com.winlauncher.loriz.win95launcher.items.ProgramMenuEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

   private PackageManager manager;
   private ArrayList<ProgramMenuEntry> apps;
   private boolean isOpen = false;
   private ImageView startNormal;
   private RelativeLayout startMenu;
   private RecyclerView startMenuEntries;
   private BroadcastReceiver broadcastReceiver;
   private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
   private CustomTextView clockTextview;
   private LinearLayout taskbarContainer;
   private LinearLayoutManager mLayoutManager;
   private RecyclerView programsMenuEntries;
   private LinearLayoutManager mLayoutManagerPrograms;
   private StartMenuAdapter sma;
   private ImageView battery;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


      startNormal = (ImageView) findViewById(R.id.start_button);
      startMenu = (RelativeLayout) findViewById(R.id.start_menu_container);
      startMenuEntries = (RecyclerView) startMenu.findViewById(R.id.start_menu_entries);
      programsMenuEntries = (RecyclerView) findViewById(R.id.win_95_start_menu_programs_recyclerview);
      battery = (ImageView) findViewById(R.id.taskbar_battery);
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

      // PREPARE DATAS FOR MENU
      ArrayList<MenuEntry> entries = prepareEntries();
      startMenuEntries.setHasFixedSize(true);
      mLayoutManager = new LinearLayoutManager(this);
      startMenuEntries.setLayoutManager(mLayoutManager);

      sma = new StartMenuAdapter(entries, programsMenuEntries);

      startMenuEntries.setAdapter(sma);


      //PROGRAMS
      loadApps();

      programsMenuEntries.setHasFixedSize(true);
      mLayoutManagerPrograms = new LinearLayoutManager(this);
      programsMenuEntries.setLayoutManager(mLayoutManagerPrograms);

      ProgramsMenuAdapter pma = new ProgramsMenuAdapter(apps);

      programsMenuEntries.setAdapter(pma);


   }


   private void loadApps() {
      manager = getPackageManager();
      apps = new ArrayList<ProgramMenuEntry>();

      Intent i = new Intent(Intent.ACTION_MAIN, null);
      i.addCategory(Intent.CATEGORY_LAUNCHER);

      List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
      for (ResolveInfo ri : availableActivities) {
         ProgramMenuEntry app = new ProgramMenuEntry(ri.activityInfo.loadIcon(manager), (String) ri.loadLabel(manager), ri.activityInfo.packageName);
         apps.add(app);
      }

      Collections.sort(apps, new Comparator<ProgramMenuEntry>() {
         @Override
         public int compare(ProgramMenuEntry programMenuEntry, ProgramMenuEntry t1) {
            return programMenuEntry.getName().compareToIgnoreCase(t1.getName());
         }

      });

   }

   private ArrayList<MenuEntry> prepareEntries() {

      ArrayList<MenuEntry> out = new ArrayList<>();
      out.add(new MenuEntry(R.drawable.shell32_20, getString(R.string.menu_programs_entry), "PROGRAMS", true));
      out.add(new MenuEntry(R.drawable.shell32_21, getString(R.string.menu_documents_entry), "DOCUMENTS", false));
      out.add(new MenuEntry(R.drawable.shell32_22, getString(R.string.menu_settings_entry), "SETTINGS", false));
      out.add(new MenuEntry(R.drawable.shell32_23, getString(R.string.menu_find_entry), "FIND", false));
      out.add(new MenuEntry(R.drawable.shell32_24, getString(R.string.menu_help_entry), "HELP", false));
      out.add(new MenuEntry(R.drawable.shell32_25, getString(R.string.menu_run_entry), "RUN", false));
      out.add(new MenuEntry(R.drawable.shell32_28, getString(R.string.menu_shutdown_entry), "SHUTDOWN", false));

      return out;
   }

   private boolean startToggler() {
      if (isOpen == false) {
         startNormal.setImageResource(R.drawable.start_pressed);
         startMenu.setVisibility(View.VISIBLE);

      } else {
         startNormal.setImageResource(R.drawable.start_unpressed);
         startMenu.setVisibility(View.INVISIBLE);
         if  (programsMenuEntries.getVisibility() == View.VISIBLE) {
            sma.togglePrograms(sma.programHolder);
            sma.isProgramsOpen = false;
         }
      }
      return !isOpen;

   }

   @Override
   public void onBackPressed() {
      if (isOpen == true) {

         if (sma.isProgramsOpen == true) {
            sma.togglePrograms(sma.programHolder);
            sma.isProgramsOpen = false;
         } else {
            isOpen = startToggler();
         }
      }
   }


   @Override
   public void onStart() {
      super.onStart();
      clockTextview.setText(sdf.format(new Date()));
      broadcastReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context ctx, Intent intent) {

            if (intent.getAction().compareTo(Intent.ACTION_BATTERY_CHANGED) == 0) {
               int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
               int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
               float percentage = level / (float) scale;

               if (level <= 100 && level > 75) {
                  battery.setImageDrawable(getResources().getDrawable(R.drawable.systray_300));
               } else if (level <= 75 && level > 25) {
                  battery.setImageDrawable(getResources().getDrawable(R.drawable.systray_301));
               } else if (level <= 25 && level > 5) {
                  battery.setImageDrawable(getResources().getDrawable(R.drawable.systray_302));
               } else {
                  battery.setImageDrawable(getResources().getDrawable(R.drawable.systray_303));
               }

            }

            if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
               clockTextview.setText(sdf.format(new Date()));
         }
      };

      IntentFilter timeBattery = new IntentFilter();
      timeBattery.addAction(Intent.ACTION_TIME_TICK);
      timeBattery.addAction(Intent.ACTION_BATTERY_CHANGED);

      registerReceiver(broadcastReceiver, timeBattery);

   }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
   public void onStop() {
      super.onStop();
      if (broadcastReceiver != null)
         unregisterReceiver(broadcastReceiver);
   }


}
