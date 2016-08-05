package com.winlauncher.loriz.win95launcher.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.febaisi.CustomTextView;
import com.winlauncher.loriz.win95launcher.MainActivity;
import com.winlauncher.loriz.win95launcher.R;
import com.winlauncher.loriz.win95launcher.items.MenuEntry;

import java.util.ArrayList;

/**
 * Created by loriz on 7/28/16.
 */

public class StartMenuAdapter extends RecyclerView.Adapter<StartMenuAdapter.StartMenuViewHolder> {

    private final ArrayList<MenuEntry> entryList;
    private RecyclerView pmentries;
    public boolean isProgramsOpen = false;
    public StartMenuViewHolder programHolder;
    private StartMenuViewHolder findHolder;
    private StartMenuViewHolder settingsHolder;


    public StartMenuAdapter(ArrayList<MenuEntry> entryList, RecyclerView pmentries) {
        this.entryList = entryList;
        this.pmentries = pmentries;
    }

    @Override
    public StartMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayoutView = inflater.inflate(R.layout.start_menu_entry, parent, false);

        StartMenuViewHolder vh = new StartMenuViewHolder(itemLayoutView);

        return vh;
    }

    @Override
    public void onBindViewHolder(final StartMenuViewHolder holder, int position) {

        //////////////////////////////////// CUSTOMIZE THE HOLDER //////////////////////////////////////////////
        holder.entryHiddenIdentifier.setText(entryList.get(position).getIdentifier());
        holder.entryIcon.setImageResource(entryList.get(position).getEntryIconId());
        holder.entryText.setText(entryList.get(position).getName());
        if (entryList.get(position).isExpandable()) holder.entryTriangle.setVisibility(View.VISIBLE);




        //////////////////////////////////// CLICK ON SETTINGS //////////////////////////////////////////////
        if (holder.entryHiddenIdentifier.getText().equals("SETTINGS")) {
            if (settingsHolder == null) settingsHolder = holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.getContext().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });

        }


        //////////////////////////////////// CLICK ON FIND //////////////////////////////////////////////
        if (holder.entryHiddenIdentifier.getText().equals("FIND")) {
            if (findHolder == null) findHolder = holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.VoiceSearchActivity");
                    try {
                        holder.itemView.getContext().startActivity(intent);
                    } catch (Exception anfe) {
                    }
                }
            });

        }


        //////////////////////////////////// CLICK ON PROGRAMS //////////////////////////////////////////////
        if (holder.entryHiddenIdentifier.getText().equals("PROGRAMS")) {
            if (programHolder == null) programHolder = holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    togglePrograms(holder);
                    }
            });

        }

    }



    /////////////////////////////// SUPPORT METHOD FOR PROGRAM SUBMENU MANAGEMENT /////////////////////////////////////////////////

    public void togglePrograms(StartMenuViewHolder holder) {

        if (isProgramsOpen) {
            //chiudi
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.transparent));
            holder.entryText.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));
            pmentries.setVisibility(View.INVISIBLE);
            isProgramsOpen = false;

        } else {
            //apri
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.selected_blue));
            holder.entryText.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            pmentries.setVisibility(View.VISIBLE);
            isProgramsOpen = true;
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class StartMenuViewHolder extends RecyclerView.ViewHolder {

        private TextView entryHiddenIdentifier;
        private ImageView entryIcon;
        private CustomTextView entryText;
        private ImageView entryTriangle;


        public StartMenuViewHolder(View itemView) {
            super(itemView);

            entryIcon = (ImageView) itemView.findViewById(R.id.start_menu_main_entry_icon);
            entryText = (CustomTextView) itemView.findViewById(R.id.start_menu_main_entry_label);
            entryText.getPaint().setAntiAlias(false);
            entryTriangle = (ImageView) itemView.findViewById(R.id.start_menu_main_entry_triangle);
            entryHiddenIdentifier = (TextView) itemView.findViewById(R.id.start_menu_main_entry_hidden_identifier);

        }
    }
}