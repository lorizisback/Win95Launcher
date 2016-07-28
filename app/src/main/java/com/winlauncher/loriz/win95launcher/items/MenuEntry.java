package com.winlauncher.loriz.win95launcher.items;

/**
 * Created by loriz on 7/28/16.
 */

public class MenuEntry {


    private int entryIconId;
    private String name;
    private String identifier;
    private boolean isExpandable;

    public MenuEntry(int entryIconId, String name, String identifier,  boolean isExpandable) {
        this.entryIconId = entryIconId;
        this.name = name;
        this.identifier = identifier;
        this.isExpandable = isExpandable;
    }

    public int getEntryIconId() {
        return entryIconId;
    }

    public String getName() {
        return name;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public String getIdentifier() {
        return identifier;
    }
}
