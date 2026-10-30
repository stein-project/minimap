/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (c) 2026 Breno Xavier
 */

package dev.xavier.stein.minimap;

/**
 * Minimap: the terrain from above (like Rei's Minimap) or radar only, with players, mobs and
 * spawners. {@code config/steinminimap.json}; the field names are the JSON keys the block had in
 * the HUD's file, which is where a player's choices come from the first time ({@link
 * MinimapConfigFile}): do not rename them.
 */
public final class MinimapConfig {
    /** 0 detailed map, 1 simple map (less detail, outlines), 2 radar only. */
    public int mode = 0;

    /** 0 square, 1 round. */
    public int shape = 0;

    /** Side in GUI pixels: 64, 80, 96 or 128. */
    public int size = 80;

    /** Blocks per pixel: 0.5, 1, 2 or 4. */
    public float zoom = 1.0F;

    /** Turns with the player (facing up); without it, north is up. */
    public boolean rotate = true;

    /** Players on the map (always as a dot). */
    public boolean players = true;

    /** 0 monsters and animals, 1 monsters only, 2 animals only, 3 none. */
    public int entities = 0;

    /** Spawners nearby. */
    public boolean spawners = true;

    /** Relation colors on players: enemies, allies and my clan. */
    public boolean combat = true;

    public boolean coords = true;

    /** In the Nether and underground, the floor of the player's level. */
    public boolean caves = true;

    // Place and enabled belong to the Loader's grid: these are the factory ones.

    public boolean enabled = true;
    public int anchorX = 2;
    public int anchorY = 0;
    public int x = -2;
    public int y = 2;
    public float scale = 1.0F;
    public boolean showInChat = true;
}
