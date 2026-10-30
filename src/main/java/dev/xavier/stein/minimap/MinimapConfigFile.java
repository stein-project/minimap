/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (c) 2026 Breno Xavier
 */

package dev.xavier.stein.minimap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.xavier.stein.loader.api.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Reads and writes {@link MinimapConfig}. The minimap used to be part of the HUD: without a file of
 * its own it starts from the block the HUD left in {@code config/steinhud-moved.json}, so that a
 * player who adds this mod long after updating the HUD finds their choices.
 */
final class MinimapConfigFile {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private MinimapConfigFile() {}

    static MinimapConfig load(File file, Log log) {
        try {
            if (file.isFile()) {
                return read(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
            }
            File moved = new File(file.getParentFile(), "steinhud-moved.json");
            if (moved.isFile()) {
                JsonElement root =
                        new JsonParser()
                                .parse(
                                        new String(
                                                Files.readAllBytes(moved.toPath()),
                                                StandardCharsets.UTF_8));
                if (root.isJsonObject()) {
                    JsonObject o = root.getAsJsonObject();
                    if (o.has("minimap")) {
                        return read(o.get("minimap").toString());
                    }
                }
            }
        } catch (IOException | RuntimeException e) {
            log.warn("unreadable configuration, using the defaults: " + e);
        }
        return new MinimapConfig();
    }

    private static MinimapConfig read(String json) {
        MinimapConfig c = GSON.fromJson(json, MinimapConfig.class);
        return c == null ? new MinimapConfig() : c;
    }

    static void save(MinimapConfig config, File file, Log log) {
        try {
            file.getParentFile().mkdirs();
            Files.write(file.toPath(), GSON.toJson(config).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("could not save the configuration", e);
        }
    }
}
