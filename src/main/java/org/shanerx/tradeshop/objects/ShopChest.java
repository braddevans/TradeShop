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
import org.bukkit.Nameable;
import org.bukkit.block.*;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.shanerx.tradeshop.TradeShop;
import org.shanerx.tradeshop.enumys.DebugLevels;
import org.shanerx.tradeshop.utils.Utils;

import java.util.*;

/**
 * The type Shop chest.
 */
public class ShopChest extends Utils {

    private final transient static TradeShop plugin = (TradeShop) Bukkit.getPluginManager().getPlugin("TradeShop");
    private final Location loc;
    private final String titleSeparator = ";;";
    private ShopLocation shopSign;
    private Block chest;
    private UUID owner;

    /**
     * Instantiates a new Shop chest.
     *
     * @param chestLoc
     *         the chest loc
     */
    public ShopChest(Location chestLoc) {
        this.loc = chestLoc;

        getBlock();
        loadFromName();
    }

    /**
     * Instantiates a new Shop chest.
     *
     * @param chest
     *         the chest
     * @param owner
     *         the owner
     * @param sign
     *         the sign
     */
    public ShopChest(Block chest, UUID owner, Location sign) {
        this.loc = chest.getLocation();
        this.owner = owner;
        this.shopSign = new ShopLocation(sign);
        this.chest = chest;
    }

    /**
     * Is shop chest boolean.
     *
     * @param checking
     *         the checking
     *
     * @return the boolean
     */
    public static boolean isShopChest(Block checking) {
        plugin.getDebugger().log("isShopChest checking Block at " + new ShopLocation(checking.getLocation()).serialize() + "", DebugLevels.PROTECTION);
        try {
            if (isDoubleChest(checking)) {
                DoubleChest dbl = getDoubleChest(checking);
                boolean leftHas = ((Container) dbl.getLeftSide()).getPersistentDataContainer().has(plugin.getSignKey(), PersistentDataType.STRING),
                        rightHas = ((Container) dbl.getRightSide()).getPersistentDataContainer().has(plugin.getSignKey(), PersistentDataType.STRING);

                plugin.getDebugger().log("Block is DoubleChest", DebugLevels.PROTECTION);
                plugin.getDebugger().log("Left side PerData: " + (leftHas ? ((Container) dbl.getLeftSide()).getPersistentDataContainer().get(plugin.getSignKey(), PersistentDataType.STRING) : "null"), DebugLevels.PROTECTION);
                plugin.getDebugger().log("Right side PerData: " + (rightHas ? ((Container) dbl.getRightSide()).getPersistentDataContainer().get(plugin.getSignKey(), PersistentDataType.STRING) : "null"), DebugLevels.PROTECTION);

                return leftHas || rightHas;
            }
            boolean conHas = ((Container) checking.getState()).getPersistentDataContainer().has(plugin.getSignKey(), PersistentDataType.STRING);
            plugin.getDebugger().log("Block is SINGLE inventory", DebugLevels.PROTECTION);
            plugin.getDebugger().log("Storage Block PerData: " + (conHas ? ((Container) checking.getState()).getPersistentDataContainer().get(plugin.getSignKey(), PersistentDataType.STRING) : "null"), DebugLevels.PROTECTION);
            return conHas;
        }
        catch (NullPointerException | ClassCastException ex) {
            plugin.getDebugger().log("NPE thrown during isShopChest by: \n" + ex.getCause(), DebugLevels.PROTECTION);
        }
        return false;
    }

    /**
     * Is shop chest boolean.
     *
     * @param checking
     *         the checking
     *
     * @return the boolean
     */
    public static boolean isShopChest(Inventory checking) {
        try {
            return isShopChest(Objects.requireNonNull(checking.getLocation()).getBlock());
        }
        catch (NullPointerException ignored) {
        }
        return false;
    }

    /**
     * Reset old name.
     *
     * @param checking
     *         the checking
     */
    public static void resetOldName(Block checking) {
        if (checking != null) {
            BlockState bs = checking.getState();
            if (bs instanceof Nameable && ((Nameable) bs).getCustomName() != null) {

                if (isDoubleChest(checking)) {
                    DoubleChest dbl = getDoubleChest(checking);
                    BlockState stateLeft = dbl.getLeftSide().getInventory().getLocation().getBlock().getState();
                    BlockState stateRight = dbl.getRightSide().getInventory().getLocation().getBlock().getState();

                    if (Objects.requireNonNull(((Nameable) stateRight).getCustomName()).contains("$ ^Sign:l_")) {
                        ((Nameable) stateRight).setCustomName(Objects.requireNonNull(((Nameable) stateRight).getCustomName()).split("\\$ \\^")[0]);
                        stateRight.update();
                    }
                    if (Objects.requireNonNull(((Nameable) stateLeft).getCustomName()).contains("$ ^Sign:l_")) {
                        ((Nameable) stateLeft).setCustomName(Objects.requireNonNull(((Nameable) stateLeft).getCustomName()).split("\\$ \\^")[0]);
                        stateLeft.update();
                    }

                }
                else if (Objects.requireNonNull(((Nameable) bs).getCustomName()).contains("$ ^Sign:l_")) {
                    ((Nameable) bs).setCustomName(Objects.requireNonNull(((Nameable) bs).getCustomName()).split("\\$ \\^")[0]);
                    bs.update();
                }
            }
        }
    }

    /**
     * Gets double chest.
     *
     * @param chest
     *         the chest
     *
     * @return the double chest
     */
    public static DoubleChest getDoubleChest(Block chest) {
        try {
            return (DoubleChest) ((Chest) chest.getState()).getInventory().getHolder();
        }
        catch (ClassCastException | NullPointerException ex) {
            return null;
        }
    }

    /**
     * Is double chest boolean.
     *
     * @param chest
     *         the chest
     *
     * @return the boolean
     */
    public static boolean isDoubleChest(Block chest) {
        return getDoubleChest(chest) != null;
    }

    private void getBlock() {
        if (plugin.getListManager().isInventory(loc.getBlock())) {
            Block block = loc.getBlock();

            try {
                if (isDoubleChest(block)) {
                    DoubleChest dbl = getDoubleChest(block);
                    Container left = ((Container) dbl.getLeftSide()),
                            right = ((Container) dbl.getRightSide());
                    chest = left.getPersistentDataContainer().has(plugin.getSignKey(), PersistentDataType.STRING) ? left.getBlock() : right.getBlock();
                }
                else { chest = block; }
            }
            catch (NullPointerException npe) {
                chest = block;
            }
        }
    }

    /**
     * Gets block state.
     *
     * @return the block state
     */
    public BlockState getBlockState() {
        return chest.getState();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        try {
            return ((InventoryHolder) chest.getState()).getInventory();
        }
        catch (ClassCastException | NullPointerException ex) {
        }

        return null;
    }

    /**
     * Has stock boolean.
     *
     * @param product
     *         the product
     *
     * @return the boolean
     */
    public boolean hasStock(List<ShopItemStack> product) {
        return product.size() > 0 && getItems(getInventory(), product, 1).get(0) != null;
    }

    /**
     * Load from name.
     */
    public void loadFromName() {
        if (isShopChest(chest)) {
            String sectionSeparator = "\\$ \\^";
            String[] name = ((Container) chest.getState()).getPersistentDataContainer().get(plugin.getSignKey(), PersistentDataType.STRING)
                                                          .replaceAll("Sign:", "Sign" + titleSeparator).replaceAll("Owner:", "Owner" + titleSeparator)
                                                          .split(sectionSeparator);
            Map<String, String> chestData = new HashMap<>();
            for (String s : name) {
                chestData.put(s.split(titleSeparator)[0], s.replace(s.split(titleSeparator)[0] + titleSeparator, ""));
            }
            chestData.forEach((k, v) -> plugin.getDebugger().log(k + " = " + v, DebugLevels.PROTECTION));
            shopSign = ShopLocation.deserialize(chestData.get("Sign"));
            owner = UUID.fromString(chestData.get("Owner"));
        }
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        Inventory inv = getInventory();
        if (inv == null) {
            return true;
        }

        for (ItemStack i : inv.getStorageContents()) {
            if (i != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return "$ ^Sign" +
               titleSeparator +
               shopSign.serialize() +
               "$ ^Owner" +
               titleSeparator +
               owner.toString();
    }

    /**
     * Sets name.
     *
     * @param toSet
     *         the to set
     */
    public void setName(Block toSet) {
        Container container = (Container) chest.getState();
        container.getPersistentDataContainer().set(plugin.getSignKey(), PersistentDataType.STRING, getName());
        container.update();
    }

    /**
     * Sets name.
     */
    public void setName() {
        setName(chest);
    }

    /**
     * Reset name.
     */
    public void resetName() {
        if (isShopChest(chest)) {
            Container container = (Container) chest.getState();
            container.getPersistentDataContainer().remove(plugin.getStorageKey());
            container.getPersistentDataContainer().remove(plugin.getSignKey());
            container.update();
        }
    }

    /**
     * Sets event name.
     *
     * @param event
     *         the event
     */
    public void setEventName(BlockPlaceEvent event) {
        setName(event.getBlockPlaced());
    }

    /**
     * Sets sign.
     *
     * @param newSign
     *         the new sign
     */
    public void setSign(ShopLocation newSign) {
        shopSign = newSign;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public UUID getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param uuid
     *         the uuid
     */
    public void setOwner(UUID uuid) {
        owner = uuid;
    }

    /**
     * Gets chest.
     *
     * @return the chest
     */
    public Block getChest() {
        return chest;
    }

    /**
     * Gets shop sign.
     *
     * @return the shop sign
     */
    public ShopLocation getShopSign() {
        return shopSign;
    }

    /**
     * Has owner boolean.
     *
     * @return the boolean
     */
    public boolean hasOwner() {
        return owner != null;
    }

    /**
     * Has shop sign boolean.
     *
     * @return the boolean
     */
    public boolean hasShopSign() {
        return shopSign != null;
    }

    /**
     * Gets shop.
     *
     * @return the shop
     */
    public Shop getShop() {
        if (hasShopSign()) {
            return Shop.loadShop(getShopSign());
        }

        return null;
    }
}
