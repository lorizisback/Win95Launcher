package com.winlauncher.loriz.win95launcher.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.febaisi.CustomTextView;
import com.winlauncher.loriz.win95launcher.MainActivity;
import com.winlauncher.loriz.win95launcher.R;
import com.winlauncher.loriz.win95launcher.items.MenuEntry;
import com.winlauncher.loriz.win95launcher.items.ProgramMenuEntry;

import java.util.ArrayList;

/**
 * Created by loriz on 7/28/16.
 */

public class ProgramsMenuAdapter extends RecyclerView.Adapter<ProgramsMenuAdapter.ProgramsMenuViewHolder> {

    private final ArrayList<ProgramMenuEntry> entryList;

    public ProgramsMenuAdapter(ArrayList<ProgramMenuEntry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public ProgramsMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemLayoutView = inflater.inflate(R.layout.programs_menu_entry, parent, false);

        ProgramsMenuViewHolder vh = new ProgramsMenuViewHolder(itemLayoutView);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ProgramsMenuViewHolder holder, final int position) {


        holder.entryIcon.setImageDrawable(entryList.get(position).getImageDrawable());

        holder.entryText.setText(entryList.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.itemView.getContext().startActivity(holder.itemView.getContext().getPackageManager().getLaunchIntentForPackage(entryList.get(position).getIdentifier()));

            }
        });



    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class ProgramsMenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView entryIcon;
        private CustomTextView entryText;


        public ProgramsMenuViewHolder(View itemView) {
            super(itemView);

            entryIcon = (ImageView) itemView.findViewById(R.id.program_list_entry_image);
            entryText = (CustomTextView) itemView.findViewById(R.id.program_list_entry_name);
            entryText.getPaint().setAntiAlias(false);

        }
    }
}