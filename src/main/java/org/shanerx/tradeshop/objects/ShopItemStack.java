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

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.shanerx.tradeshop.enumys.DebugLevels;
import org.shanerx.tradeshop.utils.Utils;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Shop item stack.
 */
public class ShopItemStack implements Serializable {

    private transient ItemStack itemStack;
    private transient Debug debugger;

    private String itemStackB64;

    private int compareDurability = 1;
    private boolean compareEnchantments = true,
            compareName = true,
            compareLore = true,
            compareCustomModelData = true,
            compareItemFlags = true,
            compareUnbreakable = true,
            compareAttributeModifier = true,
            compareBookAuthor = true,
            compareBookPages = true;

    /**
     * Instantiates a new Shop item stack.
     *
     * @param itemStack
     *         the item stack
     */
    public ShopItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        toBase64();
    }

    /**
     * Instantiates a new Shop item stack.
     *
     * @param itemStackB64
     *         the item stack b 64
     */
    public ShopItemStack(String itemStackB64) {
        this.itemStackB64 = itemStackB64;

        fromBase64();
    }

    /**
     * Instantiates a new Shop item stack.
     *
     * @param itemStackB64
     *         the item stack b 64
     * @param compareDurability
     *         the compare durability
     * @param compareEnchantments
     *         the compare enchantments
     * @param compareName
     *         the compare name
     * @param compareLore
     *         the compare lore
     * @param compareCustomModelData
     *         the compare custom model data
     * @param compareItemFlags
     *         the compare item flags
     * @param compareUnbreakable
     *         the compare unbreakable
     * @param compareAttributeModifier
     *         the compare attribute modifier
     * @param compareBookAuthor
     *         the compare book author
     * @param compareBookPages
     *         the compare book pages
     */
    public ShopItemStack(String itemStackB64, int compareDurability, boolean compareEnchantments, boolean compareName, boolean compareLore, boolean compareCustomModelData, boolean compareItemFlags, boolean compareUnbreakable, boolean compareAttributeModifier, boolean compareBookAuthor, boolean compareBookPages) {
        this.itemStackB64 = itemStackB64;
        this.compareAttributeModifier = compareAttributeModifier;
        this.compareUnbreakable = compareUnbreakable;
        this.compareItemFlags = compareItemFlags;
        this.compareCustomModelData = compareCustomModelData;
        this.compareLore = compareLore;
        this.compareName = compareName;
        this.compareEnchantments = compareEnchantments;
        this.compareDurability = compareDurability;
        this.compareBookPages = compareBookPages;
        this.compareBookAuthor = compareBookAuthor;

        fromBase64();

    }

    /**
     * Deserialize shop item stack.
     *
     * @param serialized
     *         the serialized
     *
     * @return the shop item stack
     */
    public static ShopItemStack deserialize(String serialized) {
        ShopItemStack item = new Gson().fromJson(serialized, ShopItemStack.class);
        item.fromBase64();
        return item;
    }

    /**
     * Is compare durability int.
     *
     * @return the int
     */
    public int isCompareDurability() {
        return compareDurability;
    }

    /**
     * Sets compare durability.
     *
     * @param compareDurability
     *         the compare durability
     */
    public void setCompareDurability(int compareDurability) {
        this.compareDurability = compareDurability;
    }

    /**
     * Is compare enchantments boolean.
     *
     * @return the boolean
     */
    public boolean isCompareEnchantments() {
        return compareEnchantments;
    }

    /**
     * Sets compare enchantments.
     *
     * @param compareEnchantments
     *         the compare enchantments
     */
    public void setCompareEnchantments(boolean compareEnchantments) {
        this.compareEnchantments = compareEnchantments;
    }

    /**
     * Is compare name boolean.
     *
     * @return the boolean
     */
    public boolean isCompareName() {
        return compareName;
    }

    /**
     * Sets compare name.
     *
     * @param compareName
     *         the compare name
     */
    public void setCompareName(boolean compareName) {
        this.compareName = compareName;
    }

    /**
     * Is compare lore boolean.
     *
     * @return the boolean
     */
    public boolean isCompareLore() {
        return compareLore;
    }

    /**
     * Sets compare lore.
     *
     * @param compareLore
     *         the compare lore
     */
    public void setCompareLore(boolean compareLore) {
        this.compareLore = compareLore;
    }

    /**
     * Is compare custom model data boolean.
     *
     * @return the boolean
     */
    public boolean isCompareCustomModelData() {
        return compareCustomModelData;
    }

    /**
     * Sets compare custom model data.
     *
     * @param compareCustomModelData
     *         the compare custom model data
     */
    public void setCompareCustomModelData(boolean compareCustomModelData) {
        this.compareCustomModelData = compareCustomModelData;
    }

    /**
     * Is compare item flags boolean.
     *
     * @return the boolean
     */
    public boolean isCompareItemFlags() {
        return compareItemFlags;
    }

    /**
     * Sets compare item flags.
     *
     * @param compareItemFlags
     *         the compare item flags
     */
    public void setCompareItemFlags(boolean compareItemFlags) {
        this.compareItemFlags = compareItemFlags;
    }

    /**
     * Is compare unbreakable boolean.
     *
     * @return the boolean
     */
    public boolean isCompareUnbreakable() {
        return compareUnbreakable;
    }

    /**
     * Sets compare unbreakable.
     *
     * @param compareUnbreakable
     *         the compare unbreakable
     */
    public void setCompareUnbreakable(boolean compareUnbreakable) {
        this.compareUnbreakable = compareUnbreakable;
    }

    /**
     * Is compare attribute modifier boolean.
     *
     * @return the boolean
     */
    public boolean isCompareAttributeModifier() {
        return compareAttributeModifier;
    }

    /**
     * Sets compare attribute modifier.
     *
     * @param compareAttributeModifier
     *         the compare attribute modifier
     */
    public void setCompareAttributeModifier(boolean compareAttributeModifier) {
        this.compareAttributeModifier = compareAttributeModifier;
    }

    /**
     * Gets item stack b 64.
     *
     * @return the item stack b 64
     */
    public String getItemStackB64() {
        return itemStackB64;
    }

    /**
     * Is compare book author boolean.
     *
     * @return the boolean
     */
    public boolean isCompareBookAuthor() {
        return compareBookAuthor;
    }

    /**
     * Sets compare book author.
     *
     * @param compareBookAuthor
     *         the compare book author
     */
    public void setCompareBookAuthor(boolean compareBookAuthor) {
        this.compareBookAuthor = compareBookAuthor;
    }

    /**
     * Is compare book pages boolean.
     *
     * @return the boolean
     */
    public boolean isCompareBookPages() {
        return compareBookPages;
    }

    /**
     * Sets compare book pages.
     *
     * @param compareBookPages
     *         the compare book pages
     */
    public void setCompareBookPages(boolean compareBookPages) {
        this.compareBookPages = compareBookPages;
    }

    /**
     * Is similar boolean.
     *
     * @param toCompare
     *         the to compare
     *
     * @return the boolean
     */
    public boolean isSimilar(ItemStack toCompare) {
        debugger = new Utils().debugger;

        // Return False if either item is null
        if (itemStack == null || toCompare == null) {
            debugger.log("itemstack isNull: " + (itemStack == null ? null : "Not Null"), DebugLevels.ITEM_COMPARE);
            debugger.log("toCompare isNull: " + (toCompare == null ? null : "Not Null"), DebugLevels.ITEM_COMPARE);
            return false;
        }

        // Return False if ItemStacks are different MaterialTypes
        if (itemStack.getType() != toCompare.getType()) {
            debugger.log("itemstack material: " + itemStack.getType(), DebugLevels.ITEM_COMPARE);
            debugger.log("toCompare material: " + toCompare.getType(), DebugLevels.ITEM_COMPARE);
            return false;
        }

        // Return False if hasItemMeta differs (one has one doesn't)
        if (itemStack.hasItemMeta() != toCompare.hasItemMeta()) {
            debugger.log("itemstack hasMeta: " + itemStack.hasItemMeta(), DebugLevels.ITEM_COMPARE);
            debugger.log("toCompare hasMeta: " + toCompare.hasItemMeta(), DebugLevels.ITEM_COMPARE);
            return false;
        }

        // Return True if both items don't have MetaData
        if (itemStack.hasItemMeta()) {

            ItemMeta itemStackMeta = itemStack.getItemMeta(), toCompareMeta = toCompare.getItemMeta();
            BookMeta itemStackBookMeta = itemStack.getItemMeta() instanceof BookMeta ? ((BookMeta) itemStackMeta) : null,
                    toCompareBookMeta = toCompare.getItemMeta() instanceof BookMeta ? ((BookMeta) toCompareMeta) : null;
            boolean useBookMeta = itemStackBookMeta != null && toCompareBookMeta != null;
            debugger.log("itemstack isBookMeta: " + (itemStackBookMeta != null), DebugLevels.ITEM_COMPARE);
            debugger.log("toCompare isBookMeta: " + (toCompareBookMeta != null), DebugLevels.ITEM_COMPARE);

            // Return False if either Meta value is null
            if (itemStackMeta == null || toCompareMeta == null) {
                debugger.log("itemStackMeta isNull: " + (itemStackMeta == null ? null : "Not Null"), DebugLevels.ITEM_COMPARE);
                debugger.log("toCompareMeta isNull: " + (toCompareMeta == null ? null : "Not Null"), DebugLevels.ITEM_COMPARE);
                return false;
            }

            // If compareDurability is on
            if (compareDurability > - 1 && compareDurability < 3) {
                // Return False if Damageable is not equal (one has and one doesn't)
                if (itemStackMeta instanceof Damageable != toCompareMeta instanceof Damageable) {
                    debugger.log("toCompareMeta isDamageable: " + (itemStackMeta instanceof Damageable), DebugLevels.ITEM_COMPARE);
                    debugger.log("toCompareMeta isDamageable: " + (toCompareMeta instanceof Damageable), DebugLevels.ITEM_COMPARE);
                    return false;
                }

                if (itemStackMeta instanceof Damageable) {

                    Damageable itemStackDamageable = (Damageable) itemStackMeta, toCompareDamageable = (Damageable) toCompareMeta;

                    // Return False compareDurability is set to '==' and ItemStack Damage is not equal
                    if (compareDurability == 1 && itemStackDamageable.getDamage() != toCompareDamageable.getDamage()) {
                        debugger.log("itemstack Durabilty (==): " + itemStackDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        debugger.log("toCompare Durabilty (==): " + toCompareDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        return false;
                    }

                    // Return False compareDurability is set to '<=' and ItemStack Damage less than toCompare Damage
                    if (compareDurability == 0 && itemStackDamageable.getDamage() > toCompareDamageable.getDamage()) {
                        debugger.log("itemstack Durabilty (<=): " + itemStackDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        debugger.log("toCompare Durabilty (<=): " + toCompareDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        return false;
                    }

                    // Return False compareDurability is set to '>=' and ItemStack Damage greater than toCompare Damage
                    if (compareDurability == 2 && itemStackDamageable.getDamage() < toCompareDamageable.getDamage()) {
                        debugger.log("itemstack Durabilty (>=): " + itemStackDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        debugger.log("toCompare Durabilty (>=): " + toCompareDamageable.getDamage(), DebugLevels.ITEM_COMPARE);
                        return false;
                    }
                }

            }

            // If compareEnchantments is on
            if (compareEnchantments) {
                // Return False if hasEnchantments differs (one has one doesn't)
                if (itemStackMeta.hasEnchants() != toCompareMeta.hasEnchants()) {
                    debugger.log("itemStackMeta hasEnchants: " + itemStackMeta.hasEnchants(), DebugLevels.ITEM_COMPARE);
                    debugger.log("toCompareMeta hasEnchants: " + toCompareMeta.hasEnchants(), DebugLevels.ITEM_COMPARE);
                    return false;
                }

                // Return False if itemStack hasEnchantments && Enchant maps are not equal
                if (itemStackMeta.hasEnchants() && ! itemStackMeta.getEnchants().equals(toCompareMeta.getEnchants())) { return false; }
            }

            // If compareName is on
            if (compareName) {

                // If ItemStack Meta are BookMeta then compare title, otherwise compare displayname
                if (useBookMeta) {
                    // Return False if hasTitle differs (one has one doesn't)
                    if (itemStackBookMeta.hasTitle() != toCompareBookMeta.hasTitle()) { return false; }

                    // Return False if itemStack hasTitle && Title is not equal
                    if (itemStackBookMeta.hasTitle() && ! itemStackBookMeta.getTitle().equals(toCompareBookMeta.getTitle())) { return false; }
                }
                else {
                    // Return False if hasDisplayName differs (one has one doesn't)
                    if (itemStackMeta.hasDisplayName() != toCompareMeta.hasDisplayName()) { return false; }

                    // Return False if itemStack hasDisplayName && DisplayName is not equal
                    if (itemStackMeta.hasDisplayName() && ! itemStackMeta.getDisplayName().equals(toCompareMeta.getDisplayName())) { return false; }
                }
            }

            // If useBookMeta and compareBookAuthor are true
            if (useBookMeta && compareBookAuthor) {
                // Return False if hasAuthor differs (one has one doesn't)
                debugger.log("itemStackBookMeta hasAuthor: " + itemStackBookMeta.hasAuthor(), DebugLevels.ITEM_COMPARE);
                debugger.log("toCompareBookMeta hasAuthor: " + toCompareBookMeta.hasAuthor(), DebugLevels.ITEM_COMPARE);
                if (itemStackBookMeta.hasAuthor() != toCompareBookMeta.hasAuthor()) {
                    return false;
                }

                // Return False if itemStack hasAuthor && Author is not equal
                debugger.log("itemStackBookMeta getAuthor: " + itemStackBookMeta.getAuthor(), DebugLevels.ITEM_COMPARE);
                debugger.log("toCompareBookMeta getAuthor: " + toCompareBookMeta.getAuthor(), DebugLevels.ITEM_COMPARE);
                if (itemStackBookMeta.hasAuthor() && ! Objects.equals(itemStackBookMeta.getAuthor(), toCompareBookMeta.getAuthor())) {
                    return false;
                }
            }

            // If useBookMeta and compareBookPages are true
            if (useBookMeta && compareBookPages) {
                // Return False if hasPages differs (one has one doesn't)
                debugger.log("itemStackBookMeta hasPages: " + itemStackBookMeta.hasPages(), DebugLevels.ITEM_COMPARE);
                debugger.log("toCompareBookMeta hasPages: " + toCompareBookMeta.hasPages(), DebugLevels.ITEM_COMPARE);
                if (itemStackBookMeta.hasPages() != toCompareBookMeta.hasPages()) {
                    return false;
                }

                // Return False if itemStack hasPages && Pages is not equal
                debugger.log("itemStackBookMeta isNull: " + itemStackBookMeta.getPages(), DebugLevels.ITEM_COMPARE);
                debugger.log("toCompareBookMeta isNull: " + toCompareBookMeta.getPages(), DebugLevels.ITEM_COMPARE);
                if (itemStackBookMeta.hasPages() && ! Objects.equals(itemStackBookMeta.getPages(), toCompareBookMeta.getPages())) {
                    return false;
                }
            }

            // If compareLore is on
            if (compareLore) {
                // Return False if hasLore differs (one has one doesn't)
                if (itemStackMeta.hasLore() != toCompareMeta.hasLore()) { return false; }

                // Return False if itemStack hasLore && Lore is not equal
                if (itemStackMeta.hasLore() && ! Objects.equals(itemStackMeta.getLore(), toCompareMeta.getLore())) { return false; }
            }

            // If compareCustomModelData is on
            if (compareCustomModelData) {
                // Return False if hasCustomModelData differs (one has one doesn't)
                if (itemStackMeta.hasCustomModelData() != toCompareMeta.hasCustomModelData()) { return false; }

                // Return False if itemStack hasCustomModelData && Custom Model Data is not equal
                if (itemStackMeta.hasCustomModelData() && itemStackMeta.getCustomModelData() != toCompareMeta.getCustomModelData()) { return false; }
            }

            // If compareItemFlags is on
            if (compareItemFlags) {
                // Return False if getItemFlags sizes differs
                if (itemStackMeta.getItemFlags().size() != toCompareMeta.getItemFlags().size()) { return false; }

                // Return False if Lore is not equal
                if (! itemStackMeta.getItemFlags().equals(toCompareMeta.getItemFlags())) { return false; }
            }

            // Return False if compareUnbreakable is on and isUnbreakable differs
            if (compareUnbreakable && itemStackMeta.isUnbreakable() != toCompareMeta.isUnbreakable()) { return false; }

            // If compareAttributeModifier is on
            if (compareAttributeModifier) {
                if (itemStackMeta.hasAttributeModifiers() != toCompareMeta.hasAttributeModifiers()) { return false; }

                // Return False if itemStack hasAttributeModifiers && getAttributeModifiers are not equal
                return ! itemStackMeta.hasAttributeModifiers() || Objects.equals(itemStackMeta.getAttributeModifiers(), toCompareMeta.getAttributeModifiers());
            }

        }
        return true;
    }

    /**
     * Gets item stack.
     *
     * @return the item stack
     */
    public ItemStack getItemStack() {
        if (itemStack == null) { fromBase64(); }
        return itemStack;
    }

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() ?
               itemStack.getItemMeta().getDisplayName() :
               itemStack.getType().toString();
    }

    /**
     * Serialize string.
     *
     * @return the string
     */
    public String serialize() {
        return new Gson().toJson(this);
    }

    /**
     *
     * Original code from https://gist.github.com/graywolf336/8153678
     * Tweaked for use with single itemstacks
     *
     */

    /**
     * Sets the objects Base64 from its {@link ItemStack}
     */
    private void toBase64() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Save every element in the list
            dataOutput.writeObject(itemStack);

            // Serialize that array
            dataOutput.close();
            itemStackB64 = Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            itemStackB64 = null;
        }
    }

    /**
     * Sets the objects {@link ItemStack} from its Base64.
     */
    private void fromBase64() {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(itemStackB64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // Read the serialized inventory
            itemStack = (ItemStack) dataInput.readObject();

            dataInput.close();
        }
        catch (ClassNotFoundException | IOException e) {
            itemStack = null;
        }
    }
}