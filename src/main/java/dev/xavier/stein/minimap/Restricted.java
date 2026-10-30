/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (c) 2026 Breno Xavier
 */

package dev.xavier.stein.minimap;

import dev.xavier.stein.loader.api.Option;
import dev.xavier.stein.loader.api.Server;

/** The options' line saying that the current server turned a feature off. */
final class Restricted {

    private Restricted() {}

    /** Shown only while it is off. A click does nothing: there is nothing to change there. */
    static Option note(String feature) {
        return new Option(T.tr("restricted")) {
            @Override
            public String value() {
                return T.tr("restricted.by", Server.restrictedBy(feature));
            }

            @Override
            public void click(boolean forward) {}
        }.visibleWhen(() -> !Server.allows(feature));
    }
}
