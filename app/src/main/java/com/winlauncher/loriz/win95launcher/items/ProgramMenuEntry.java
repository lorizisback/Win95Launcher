package com.winlauncher.loriz.win95launcher.items;

import android.graphics.drawable.Drawable;

/**
 * Created by loriz on 7/29/16.
 */

public class ProgramMenuEntry {

    private Drawable entryIconId;
    private String name;
    private String identifier;

    public ProgramMenuEntry(Drawable entryIconId, String name, String identifier) {
        this.entryIconId = entryIconId;
        this.name = name;
        this.identifier = identifier;
    }

    public Drawable getImageDrawable() {
        return entryIconId;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }
}
