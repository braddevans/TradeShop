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

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.shanerx.tradeshop.utils.BukkitVersion;
import org.shanerx.tradeshop.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The type Shop storage.
 */
public class ShopStorage extends Utils {

    private final HashMap<String, Storages> storageTypes = new HashMap<>();

    /**
     * Instantiates a new Shop storage.
     */
    public ShopStorage() {
        for (Storages type : Storages.values()) {

            debugger.log(type.toString(), DebugLevels.STARTUP);
            debugger.log(String.format("- MinVer: %s", type.getMinVersionAsString()), DebugLevels.STARTUP);
            debugger.log(String.format("- MaxVer: %s", type.getMaxVersionAsString()), DebugLevels.STARTUP);
            debugger.log(String.format("- Weight: %s", type.getWeight()), DebugLevels.STARTUP);

            boolean added = false;

            BukkitVersion version = new BukkitVersion();
            if ((type.hasMinVersion() && !version.isBelow(type.getMinVer().get(0), type.getMinVer().get(1), type.getMinVer().get(2))) ||
                    (type.hasMaxVersion() && !version.isAbove(type.getMaxVer().get(0), type.getMaxVer().get(1), type.getMaxVer().get(2)))) {
                storageTypes.put(type.getStrippedName(), type);
                added = true;
            }

            debugger.log(String.format("- Added: %s", added), DebugLevels.STARTUP);
        }
    }

    /**
     * Gets storage types.
     *
     * @return the storage types
     */
    public List<Storages> getStorageTypes() {
        return new ArrayList<>(storageTypes.values());
    }

    /**
     * Is valid inventory string.
     *
     * @param inventory
     *         the inventory
     *
     * @return the string
     */
    public String isValidInventory(String inventory) {
        String valid = "";

        for (String key : storageTypes.keySet()) {
            if (key.contains(inventory.toLowerCase().replaceAll("[^A-Za-z]+", ""))) {
                valid = key;
                break;
            }
        }

        return valid;
    }

    /**
     * Gets valid inventory.
     *
     * @param inventory
     *         the inventory
     *
     * @return the valid inventory
     */
    public Storages getValidInventory(String inventory) {
        return storageTypes.get(inventory.toLowerCase().replaceAll("[^A-Za-z]+", ""));
    }

    /**
     * The enum Storages.
     */
    public enum Storages {
        /**
         * Chest storages.
         */
        CHEST("1.0.0", "", 0, Lists.newArrayList(Material.CHEST, Material.TRAPPED_CHEST)),
        /**
         * Blast furnace storages.
         */
        BLAST_FURNACE("1.14.0", "", 10, Lists.newArrayList(Material.BLAST_FURNACE)),
        /**
         * Brewing stand storages.
         */
        BREWING_STAND("1.0.0", "", 10, Lists.newArrayList(Material.BREWING_STAND)),
        /**
         * Barrel storages.
         */
        BARREL("1.14.0", "", 2, Lists.newArrayList(Material.BARREL)),
        /**
         * Dispenser storages.
         */
        DISPENSER("1.2.1", "", 3, Lists.newArrayList(Material.DISPENSER)),
        /**
         * Dropper storages.
         */
        DROPPER("1.5.0", "", 3, Lists.newArrayList(Material.DROPPER)),
        /**
         * Furnace storages.
         */
        FURNACE("1.0.0", "", 10, Lists.newArrayList(Material.FURNACE)),
        /**
         * Hopper storages.
         */
        HOPPER("1.5.0", "", 5, Lists.newArrayList(Material.HOPPER)),
        /**
         * Shulker box storages.
         */
        SHULKER_BOX("1.11.0", "", 1, Lists.newArrayList(Material.SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX,
                Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX)),
        /**
         * Smoker storages.
         */
        SMOKER("1.14.0", "", 10, Lists.newArrayList(Material.SMOKER));

        private final List<Integer> minVer = Arrays.asList(new Integer[3]);
        private final List<Integer> maxVer = Arrays.asList(new Integer[3]);
        private final List<Material> typeMaterials;
        private boolean hasMin = true, hasMax = true;
        private int weight = 10;

        Storages(String minVersion, String maxVersion, int weight, List<Material> typeMaterials) {
            if (minVersion.equalsIgnoreCase("")) {
                hasMin = false;
            }

            if (maxVersion.equalsIgnoreCase("")) {
                hasMax = false;
            }

            if (hasMin) {
                String[] minVerArray = minVersion.split("[.]");
                for (int i = 0; i < minVerArray.length; i++) {
                    minVer.set(i, Integer.parseInt(minVerArray[i]));
                }
            }

            if (hasMax) {
                String[] maxVerArray = maxVersion.split("[.]");
                for (int i = 0; i < maxVerArray.length; i++) {
                    maxVer.set(i, Integer.parseInt(maxVerArray[i]));
                }
            }

            this.weight = weight;
            this.typeMaterials = typeMaterials;

        }

        /**
         * Has min version boolean.
         *
         * @return the boolean
         */
        public boolean hasMinVersion() {
            return hasMin;
        }

        /**
         * Has max version boolean.
         *
         * @return the boolean
         */
        public boolean hasMaxVersion() {
            return hasMax;
        }

        /**
         * Gets min ver.
         *
         * @return the min ver
         */
        public List<Integer> getMinVer() {
            return minVer;
        }

        /**
         * Gets max ver.
         *
         * @return the max ver
         */
        public List<Integer> getMaxVer() {
            return maxVer;
        }

        /**
         * Gets weight.
         *
         * @return the weight
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Gets type materials.
         *
         * @return the type materials
         */
        public List<Material> getTypeMaterials() {
            return typeMaterials;
        }

        /**
         * Gets min version as string.
         *
         * @return the min version as string
         */
        public String getMinVersionAsString() {
            return hasMinVersion() ? getMinVer().get(0) + "." + getMinVer().get(1) + "." + getMinVer().get(2) : "None";
        }

        /**
         * Gets max version as string.
         *
         * @return the max version as string
         */
        public String getMaxVersionAsString() {
            return hasMaxVersion() ? getMaxVer().get(0) + "." + getMaxVer().get(1) + "." + getMaxVer().get(2) : "None";
        }

        /**
         * Gets stripped name.
         *
         * @return the stripped name
         */
        public String getStrippedName() {
            return name().toLowerCase().replaceAll("[^A-Za-z]+", "");
        }

    }
}
