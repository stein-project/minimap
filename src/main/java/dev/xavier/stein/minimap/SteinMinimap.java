/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (c) 2026 Breno Xavier
 */

package dev.xavier.stein.minimap;

import dev.xavier.stein.loader.api.Hud;
import dev.xavier.stein.loader.api.Log;
import dev.xavier.stein.loader.api.ModContext;
import dev.xavier.stein.loader.api.SteinMod;

import java.io.File;

/** The minimap: an element of the HUD grid, moved and configured in the grid's editor. */
public final class SteinMinimap implements SteinMod {

    private final ModContext ctx = ModContext.of(this);
    private final Log log = ctx.log();
    private MinimapConfig config = new MinimapConfig();
    private File configFile;
    private Minimap minimap;

    /** Changes when cached texts may be stale: a resource reload, and once a second. */
    int textEpoch;

    private int ticks;

    Log log() {
        return log;
    }

    /** A new resource pack: the minimap takes its colors again. */
    @Override
    public void onModelsReloaded() {
        textEpoch++;
        if (minimap != null) {
            minimap.resourcesReloaded();
        }
    }

    @Override
    public void onTickEnd() {
        if (++ticks % 20 == 0) {
            textEpoch++;
        }
    }

    @Override
    public void afterStartGame() {
        configFile = ctx.configFile();
        config = MinimapConfigFile.load(configFile, log);
        // Written at once: from here on this file is the one that counts.
        save();
        minimap = new Minimap(config, this);
        Hud.register(minimap);
        log.info("started (" + configFile + ")");
    }

    void save() {
        if (configFile != null) {
            MinimapConfigFile.save(config, configFile, log);
        }
    }

    /** Configure, on the Stein Loader menu: the grid editor, returning to the menu. */
    @Override
    public Object configScreen(Object parent) {
        return Hud.editorScreen(parent);
    }
}
