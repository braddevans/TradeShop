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

package org.shanerx.tradeshop.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;

/**
 * The type Shop location.
 */
public class ShopLocation implements Serializable {

    final private String div = "::";
    private transient World world;
    private String worldName;
    private double x, y, z;

    /**
     * Instantiates a new Shop location.
     *
     * @param w
     *         the w
     * @param x
     *         the x
     * @param y
     *         the y
     * @param z
     *         the z
     */
    public ShopLocation(World w, double x, double y, double z) {
        this.world = w;
        this.worldName = w.getName();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiates a new Shop location.
     *
     * @param loc
     *         the loc
     */
    public ShopLocation(Location loc) {
        this.world = loc.getWorld();
        this.worldName = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
    }

    /**
     * Deserialize shop location.
     *
     * @param loc
     *         the loc
     *
     * @return the shop location
     */
    public static ShopLocation deserialize(String loc) {
        if (loc.startsWith("l")) {
            String[] locA = loc.contains("::") ? loc.split("::") : loc.split("_"); //Keep same as div
            World world = Bukkit.getWorld(locA[1]);
            if (world == null) { world = Bukkit.getWorld(locA[1].replace("-", "_")); }
            double x = Double.parseDouble(locA[2]), y = Double.parseDouble(locA[3]), z = Double.parseDouble(locA[4]);

            return new ShopLocation(world, x, y, z);
        }

        return null;
    }

    /**
     * Serialize string.
     *
     * @return the string
     */
    public String serialize() {
        return "l" + div + world.getName() + div + x + div + y + div + z;
    }

    /**
     * Gets world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets world name.
     *
     * @return the world name
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * String to world.
     */
    public void stringToWorld() {
        if (worldName != null && world == null) {
            world = Bukkit.getWorld(worldName);
        }
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return new Location(world, x, y, z);
    }
}