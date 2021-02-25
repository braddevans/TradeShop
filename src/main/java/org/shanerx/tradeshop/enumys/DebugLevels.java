/*
 *
 *                         Copyright (c) 2016-2019
 *                SparklingComet @ http://shanerx.org
 *               KillerOfPie @ http://killerofpie.github.io
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *                http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  NOTICE: All modifications made by others to the source code belong
 *  to the respective contributor. No contributor should be held liable for
 *  any damages of any kind, whether be material or moral, which were
 *  caused by their contribution(s) to the project. See the full License for more information.
 *
 */

package org.shanerx.tradeshop.enumys;

import java.util.logging.Level;

/**
 * The enum Debug levels.
 */
public enum DebugLevels {

    /**
     * Disabled debug levels.
     */
    DISABLED(0, Level.INFO),
    /**
     * List manager debug levels.
     */
    LIST_MANAGER(1, Level.WARNING),
    /**
     * Startup debug levels.
     */
    STARTUP(2, Level.INFO),
    /**
     * Protection debug levels.
     */
    PROTECTION(3, Level.WARNING),
    /**
     * Trade debug levels.
     */
    TRADE(4, Level.WARNING),
    /**
     * Inventory close npe debug levels.
     */
    INVENTORY_CLOSE_NPE(5, Level.WARNING),
    /**
     * Item compare debug levels.
     */
    ITEM_COMPARE(6, Level.WARNING);

    private static int max = 0;
    /**
     * The Position.
     */
//position is what value to check for this level in the binary string -1.
    //
    int position;
    /**
     * The Log level.
     */
    Level logLevel;

    DebugLevels(int position, Level logLevel) {
        this.position = position;
        this.logLevel = logLevel;
    }

    /**
     * Levels int.
     *
     * @return the int
     */
    public static int levels() {
        return Math.min(values().length - 1, 32);
    }

    /**
     * Max value int.
     *
     * @return the int
     */
    public static int maxValue() {
        if (max <= 1) {
            for (DebugLevels lvl : values()) {
                max += Math.pow(2, lvl.position - 1);
            }
        }

        return max;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets log level.
     *
     * @return the log level
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return " - " + name();
    }

}