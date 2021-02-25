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

package org.shanerx.tradeshop.utils;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Bukkit version.
 */
public class BukkitVersion {
    private final String VERSION = Bukkit.getBukkitVersion();
    private Map<String, Integer> verMap;

    /**
     * Instantiates a new Bukkit version.
     */
    public BukkitVersion() {
        verMap = getVerMap();
    }

    public String toString() {
        return getMajor() + "." + getMinor() + "." + getPatch();
    }

    /**
     * Gets full version.
     *
     * @return the full version
     */
    public String getFullVersion() {
        return VERSION;
    }

    /**
     * Gets major.
     *
     * @return the major
     */
    public int getMajor() {
        return verMap.get("major");
    }

    /**
     * Gets minor.
     *
     * @return the minor
     */
    public int getMinor() {
        return verMap.get("minor");
    }

    /**
     * Gets patch.
     *
     * @return the patch
     */
    public int getPatch() {
        return verMap.get("patch");
    }

    /**
     * Is below boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     *
     * @return the boolean
     */
    public boolean isBelow(int major, int minor) {
        if (getMajor() < major) {
            return true;
        }
        else if (getMajor() == major) {
            return getMinor() < minor;
        }

        return false;
    }

    /**
     * Is below boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     * @param patch
     *         the patch
     *
     * @return the boolean
     */
    public boolean isBelow(int major, int minor, int patch) {
        if (getMajor() < major) {
            return true;
        }
        else if (getMajor() == major) {
            if (getMinor() < minor) {
                return true;
            }
            else if (getMinor() == minor) {
                return getPatch() < patch;
            }
        }

        return false;
    }

    /**
     * Is above boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     *
     * @return the boolean
     */
    public boolean isAbove(int major, int minor) {
        if (getMajor() > major) {
            return true;
        }
        else if (getMajor() == major) {
            return getMinor() > minor;
        }

        return false;
    }

    /**
     * Is above boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     * @param patch
     *         the patch
     *
     * @return the boolean
     */
    public boolean isAbove(int major, int minor, int patch) {
        if (getMajor() > major) {
            return true;
        }
        else if (getMajor() == major) {
            if (getMinor() > minor) {
                return true;
            }
            else if (getMinor() == minor) {
                return getPatch() > patch;
            }
        }

        return false;
    }

    /**
     * Is equal boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     *
     * @return the boolean
     */
    public boolean isEqual(int major, int minor) {
        return getMajor() == major && getMinor() == minor;
    }

    /**
     * Is equal boolean.
     *
     * @param major
     *         the major
     * @param minor
     *         the minor
     * @param patch
     *         the patch
     *
     * @return the boolean
     */
    public boolean isEqual(int major, int minor, int patch) {
        return getMajor() == major && getMinor() == minor && getPatch() == getPatch();
    }

    /**
     * Is int boolean.
     *
     * @param str
     *         the str
     *
     * @return the boolean
     */
    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets ver map.
     *
     * @return the ver map
     */
    public Map<String, Integer> getVerMap() {
        Pattern pat = Pattern.compile("(?!\\.)(\\d+(\\.\\d+)+)(?![\\d\\.])");
        Matcher matcher = pat.matcher(VERSION);

        String ver;
        if (matcher.find()) {
            ver = matcher.group();
        }
        else {
            return null;
        }

        String[] verSplit = ver.split("\\.");

        int[] verInts = new int[3];

        for (int i = 0; i < verSplit.length; i++) {
            if (isInt(verSplit[i])) {
                verInts[i] = Integer.parseInt(verSplit[i]);
            }
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("major", verInts[0]);
        map.put("minor", verInts[1]);
        map.put("patch", verInts[2]);

        return map;
    }
}