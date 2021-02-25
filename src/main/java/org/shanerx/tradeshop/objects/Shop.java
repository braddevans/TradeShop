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
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shanerx.tradeshop.enumys.Setting;
import org.shanerx.tradeshop.enumys.ShopRole;
import org.shanerx.tradeshop.enumys.ShopStatus;
import org.shanerx.tradeshop.enumys.ShopType;
import org.shanerx.tradeshop.utils.JsonConfiguration;
import org.shanerx.tradeshop.utils.Tuple;
import org.shanerx.tradeshop.utils.Utils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Shop.
 */
@SuppressWarnings("unused")
public class Shop implements Serializable {

    private final ShopLocation shopLoc;
    private final List<ShopItemStack> product;
    private final List<ShopItemStack> cost;
    private ShopUser owner;
    private List<UUID> managers, members;
    private ShopType shopType;
    private ShopLocation chestLoc;
    private transient SignChangeEvent signChangeEvent;
    private transient Inventory storageInv;
    private transient Utils utils;
    private ShopStatus status = ShopStatus.INCOMPLETE;

    /**
     * Creates a Shop object
     *
     * @param locations
     *         Location of shop sign and chest as Tuple, left = Sign location, right = inventory location
     * @param shopType
     *         Type of the shop as ShopType
     * @param owner
     *         Owner of the shop as a ShopUser
     * @param players
     *         Users to be added to the shop as Tuple, left = Managers, right = Members
     * @param items
     *         Items to go into the shop as Tuple, left = Product, right = Cost
     */
    public Shop(Tuple<Location, Location> locations, ShopType shopType, ShopUser owner, Tuple<List<UUID>, List<UUID>> players, Tuple<ItemStack, ItemStack> items) {
        shopLoc = new ShopLocation(locations.getLeft());
        this.owner = owner;
        chestLoc = new ShopLocation(locations.getRight());
        this.shopType = shopType;
        managers = players.getLeft();
        members = players.getRight();

        product = new ArrayList<>();
        cost = new ArrayList<>();

        product.add(new ShopItemStack(items.getLeft()));
        cost.add(new ShopItemStack(items.getRight()));

        fixAfterLoad();
    }

    /**
     * Creates a Shop object
     *
     * @param locations
     *         Location of shop sign and chest as Tuple, left = Sign location, right = inventory location
     * @param shopType
     *         Type of the shop as ShopType
     * @param owner
     *         Owner of the shop as a ShopUser
     */
    public Shop(Tuple<Location, Location> locations, ShopType shopType, ShopUser owner) {
        shopLoc = new ShopLocation(locations.getLeft());
        this.owner = owner;
        chestLoc = new ShopLocation(locations.getRight());
        this.shopType = shopType;
        managers = Collections.emptyList();
        members = Collections.emptyList();

        product = new ArrayList<>();
        cost = new ArrayList<>();

        fixAfterLoad();
    }

    /**
     * Creates a Shop object
     *
     * @param location
     *         Location of shop sign
     * @param shopType
     *         Type of the shop as ShopType
     * @param owner
     *         Owner of the shop as a ShopUser
     */
    public Shop(Location location, ShopType shopType, ShopUser owner) {
        shopLoc = new ShopLocation(location);
        this.owner = owner;
        this.shopType = shopType;
        managers = Collections.emptyList();
        members = Collections.emptyList();

        product = new ArrayList<>();
        cost = new ArrayList<>();

        fixAfterLoad();
    }

    /**
     * Deserializes the object to Json using Gson
     *
     * @param serialized
     *         Shop GSON to be deserialized
     *
     * @return Shop object from file
     */
    public static Shop deserialize(String serialized) {
        Shop shop = new Gson().fromJson(serialized, Shop.class);
        shop.fixAfterLoad();

        return shop;
    }

    /**
     * Loads a shop from file and returns the Shop object
     *
     * @param loc
     *         Location of the shop sign
     *
     * @return The shop from file
     */
    public static Shop loadShop(ShopLocation loc) {
        return new JsonConfiguration(loc.getLocation().getChunk()).loadShop(loc);
    }

    /**
     * Retrieves the Shop object based on a serialized ShopLocation of the sign
     *
     * @param serializedShopLocation
     *         ShopLocation in serialized string
     *
     * @return Shop object from file
     */
    public static Shop loadShop(String serializedShopLocation) {
        return loadShop(Objects.requireNonNull(ShopLocation.deserialize(serializedShopLocation)));
    }

    /**
     * Loads the shop from file
     *
     * @param s
     *         Shop sign to load from
     *
     * @return Shop Object
     */
    public static Shop loadShop(Sign s) {
        return loadShop(new ShopLocation(s.getLocation()));
    }

    /**
     * Returns the shops owner
     *
     * @return ShopUser object of owner
     */
    public ShopUser getOwner() {
        return owner;
    }

    /**
     * Sets the owner (don't know if this will ever be used)
     *
     * @param owner
     *         The new owner of the shop
     */
    public void setOwner(ShopUser owner) {
        this.owner = owner;
    }

    /**
     * Sets the storageInventory
     */
    public void setStorageInventory() {
        if (getStorage() != null && getStorage() instanceof Container) { storageInv = ((Container) getStorage()).getInventory(); }
        else { storageInv = null; }
    }

    /**
     * Adds the sign change event to the shop to be used during sign update
     *
     * @param event
     *         The SignChangeEvent to hold
     */
    public void setEvent(SignChangeEvent event) {
        this.signChangeEvent = event;
    }

    /**
     * Removes the SignChangeEvent from the shop(Should be done before leaving the event)
     */
    public void removeEvent() {
        this.signChangeEvent = null;
    }

    /**
     * Returns the managers
     *
     * @return List of managers as ShopUser
     */
    public List<ShopUser> getManagers() {
        List<ShopUser> tempManagers = new ArrayList<>();

        for (UUID user : managers) {
            tempManagers.add(new ShopUser(user, ShopRole.MANAGER));
        }

        return tempManagers;
    }

    /**
     * Sets the managers
     *
     * @param managers
     *         the managers to be set to the shop
     */
    public void setManagers(List<UUID> managers) {
        this.managers = managers;
    }

    /**
     * Returns a list of all users for the shop, including; owners, managers, and members.
     *
     * @return the sign.
     */
    public List<ShopUser> getUsers() {
        List<ShopUser> users = new ArrayList<>();
        users.add(owner);
        users.addAll(getManagers());
        users.addAll(getMembers());
        return users;
    }

    /**
     * Returns the storage block as a ShopChest
     *
     * @return Shop storage as ShopChest
     */
    public ShopChest getChestAsSC() {
        try {
            return new ShopChest(chestLoc.getLocation());
        }
        catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Gets the members of the shop as ShopUser
     *
     * @return List of members as ShopUser
     */
    public List<ShopUser> getMembers() {
        List<ShopUser> tempMembers = new ArrayList<>();

        for (UUID user : members) {
            tempMembers.add(new ShopUser(user, ShopRole.MEMBER));
        }

        return tempMembers;
    }

    /**
     * Sets the members
     *
     * @param members
     *         the members to be set to the shop
     */
    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    /**
     * Adds a manager to the shop
     *
     * @param newManager
     *         the player to be added as a shopUser object
     *
     * @return true if player has been added
     */
    public boolean addManager(UUID newManager) {
        if (! getUsersUUID().contains(newManager)) {
            managers.add(newManager);
            saveShop();
            return true;
        }
        return false;
    }

    /**
     * Removes a user from the shop
     *
     * @param oldUser
     *         the UUID of the player to be removed
     *
     * @return true if user was removed
     */
    public boolean removeUser(UUID oldUser) {
        if (getManagersUUID().contains(oldUser)) {
            managers.remove(oldUser);
            saveShop();
            updateSign();
            return true;
        }

        if (getMembersUUID().contains(oldUser)) {
            members.remove(oldUser);
            saveShop();
            updateSign();
            return true;
        }

        return false;
    }

    /**
     * Adds a member to the shop
     *
     * @param newMember
     *         the player to be added as a shopUser object
     *
     * @return true if player has been added
     */
    public boolean addMember(UUID newMember) {
        if (! getUsersUUID().contains(newMember)) {
            members.add(newMember);
            saveShop();
            return true;
        }
        return false;
    }

    /**
     * Returns a list of managers uuid
     *
     * @return List of all managers uuid
     */
    public List<UUID> getManagersUUID() {
        return managers;
    }

    /**
     * Returns a list of all members uuid
     *
     * @return list of member uuid
     */
    public List<UUID> getMembersUUID() {
        return members;
    }

    /**
     * Returns location of shops inventory
     *
     * @return inventory location as Location
     */
    public Location getInventoryLocation() {
        return chestLoc.getLocation();
    }

    /**
     * Sets the inventory location
     *
     * @param newLoc
     *         new location to set
     */
    public void setInventoryLocation(Location newLoc) {
        chestLoc = new ShopLocation(newLoc);
    }

    /**
     * Returns the location of the shop sign
     *
     * @return Location of the shops sign
     */
    public Location getShopLocation() {
        return getShopLocationAsSL().getLocation();
    }

    /**
     * Returns the type of the shop
     *
     * @return list of managers as ShopUser
     */
    public ShopType getShopType() {
        return shopType;
    }

    /**
     * Sets the shops type
     *
     * @param newType
     *         new type as ShopType
     */
    public void setShopType(ShopType newType) {
        shopType = newType;
    }

    /**
     * returns the cost item
     *
     * @return Cost ItemStack List
     */
    public List<ShopItemStack> getCost() {
        return cost;
    }

    /**
     * Sets the cost item
     *
     * @param newItem
     *         ItemStack to be set
     */
    public void setCost(ItemStack newItem) {
        cost.clear();

        addCost(newItem);
    }

    /**
     * Adds more cost items
     *
     * @param newItem
     *         ItemStack to be added
     */
    public void addCost(ItemStack newItem) {
        int amount = newItem.getAmount();
        List<ItemStack> items = new ArrayList<>();
        while (amount > 0) {
            if (newItem.getMaxStackSize() < amount) {
                ItemStack itm = newItem.clone();
                itm.setAmount(newItem.getMaxStackSize());
                items.add(itm);
                amount -= newItem.getMaxStackSize();
            }
            else {
                ItemStack itm = newItem.clone();
                itm.setAmount(amount);
                items.add(itm);
                amount -= amount;
            }
        }

        items.forEach((ItemStack iS) -> cost.add(new ShopItemStack(iS)));

        saveShop();
        updateSign();
    }

    /**
     * Removes the cost item at the index
     *
     * @param index
     *         index of item to remove
     *
     * @return true if Cost is removed
     */
    public boolean removeCost(int index) {
        try {
            cost.remove(index);

            saveShop();
            updateSign();
            return true;
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    /**
     * Checks if shop has product
     *
     * @return True if product != null
     */
    public boolean hasProduct() {
        return product.size() > 0;
    }

    /**
     * Checks if shop has cost
     *
     * @return True if cost != null
     */
    public boolean hasCost() {
        return cost.size() > 0;
    }

    /**
     * Adds more product items
     *
     * @param newItem
     *         ItemStack to be added
     */
    public void addProduct(ItemStack newItem) {
        int amount = newItem.getAmount();
        List<ItemStack> items = new ArrayList<>();
        while (amount > 0) {
            if (newItem.getMaxStackSize() < amount) {
                ItemStack itm = newItem.clone();
                itm.setAmount(newItem.getMaxStackSize());
                items.add(itm);
                amount -= newItem.getMaxStackSize();
            }
            else {
                ItemStack itm = newItem.clone();
                itm.setAmount(amount);
                items.add(itm);
                amount -= amount;
            }
        }

        items.forEach((ItemStack iS) -> product.add(new ShopItemStack(iS)));

        saveShop();
        updateSign();
    }

    /**
     * Returns the product item
     *
     * @return Product ItemStack List
     */
    public List<ShopItemStack> getProduct() {
        return product;
    }

    /**
     * Sets the product item
     *
     * @param newItem
     *         item to be set to product
     */
    public void setProduct(ItemStack newItem) {
        product.clear();

        addProduct(newItem);
    }

    /**
     * Removes the product item at the index
     *
     * @param index
     *         index of item to remove
     *
     * @return true if Product is removed
     */
    public boolean removeProduct(int index) {
        if (product.size() > index) {
            product.remove(index);

            saveShop();
            updateSign();
            return true;
        }

        return false;
    }

    /**
     * Serializes the object to Json using Gson
     *
     * @return serialized string
     */
    public String serialize() {
        return new Gson().toJson(this);
    }

    /**
     * Returns the shop signs location as a ShopLocation
     *
     * @return Shop sign's Location as ShopLocation
     */
    public ShopLocation getShopLocationAsSL() {
        return shopLoc;
    }

    /**
     * Returns the shop inventories location as a ShopLocation
     *
     * @return Shop inventory's Location as ShopLocation
     */
    public ShopLocation getInventoryLocationAsSL() {
        return chestLoc;
    }

    /**
     * Fixes values that cannot be serialized after loading
     */
    public void fixAfterLoad() {
        utils = new Utils();
        shopLoc.stringToWorld();
        if (! shopType.isITrade() && chestLoc != null) { chestLoc.stringToWorld(); }
        if (getShopSign() != null) { updateSign(); }
    }

    /**
     * Gets all users of shop as UUID List
     *
     * @return List of all users as UUIDs
     */
    public List<UUID> getUsersUUID() {
        return getUsers().stream().map(ShopUser::getUUID).collect(Collectors.toList());
    }

    /**
     * Saves the shop too file
     */
    public void saveShop() {
        new JsonConfiguration(shopLoc.getLocation().getChunk()).saveShop(this);
    }

    /**
     * Returns the shops sign as a Sign
     *
     * @return Shop sign as Sign
     */
    public Sign getShopSign() {
        Block b = getShopLocation().getBlock();
        Sign s = null;

        if (ShopType.isShop(b)) {
            s = (Sign) b.getState();
        }
        return s;
    }

    /**
     * Updates the text on the shops sign
     */
    public void updateSign() {
        if (signChangeEvent != null) { updateSign(signChangeEvent); }
        else {
            Sign s = getShopSign();

            String[] signLines = updateSignLines();

            for (int i = 0; i < 4; i++) {
                if (signLines[i] != null && s != null) { s.setLine(i, signLines[i]); }
            }

            s.update();
        }
    }

    /**
     * Updates the text on the shops sign during SignChangeEvent
     *
     * @param signEvent
     *         SignEvent to update the sign for
     */
    public void updateSign(SignChangeEvent signEvent) {
        String[] signLines = updateSignLines();

        for (int i = 0; i < 4; i++) {
            signEvent.setLine(i, signLines[i]);
        }
    }

    /**
     * Updates the text for the shop signs
     *
     * @return String array containing updated sign lines to be set
     */
    private String[] updateSignLines() {
        String[] signLines = new String[4];

        if (isMissingItems()) {
            signLines[0] = utils.colorize(Setting.SHOP_INCOMPLETE_COLOUR.getString() + shopType.toHeader());
        }
        else {
            signLines[0] = utils.colorize(Setting.SHOP_GOOD_COLOUR.getString() + shopType.toHeader());
        }

        if (product.isEmpty()) {
            signLines[1] = "";
        }
        else if (product.size() == 1) {
            StringBuilder sb = new StringBuilder();

            ShopItemStack item = product.get(0);

            sb.append(item.getItemStack().getAmount());
            sb.append(" ");

            sb.append(item.getItemName());

            signLines[1] = sb.substring(0, Math.min(sb.length(), 15));

        }
        else {
            signLines[1] = Setting.MULTIPLE_ITEMS_ON_SIGN.getString();
        }

        if (cost.isEmpty()) {
            signLines[2] = "";
        }
        else if (cost.size() == 1) {
            StringBuilder sb = new StringBuilder();

            ShopItemStack item = cost.get(0);

            sb.append(item.getItemStack().getAmount());
            sb.append(" ");

            sb.append(item.getItemName());

            signLines[2] = sb.substring(0, Math.min(sb.length(), 15));
        }
        else {
            signLines[2] = Setting.MULTIPLE_ITEMS_ON_SIGN.getString();
        }

        updateStatus();

        signLines[3] = status.getLine();

        return signLines;
    }

    /**
     * Returns the shops inventory as a BlockState
     *
     * @return shops inventory as BlockState
     */
    public BlockState getStorage() {
        try {
            return getInventoryLocation().getBlock().getState();
        }
        catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * Removes the shops inventory from the shop
     */
    public void removeStorage() {
        if (hasStorage()) {
            chestLoc = null;
        }
    }

    /**
     * Returns if the shops inventory exists
     *
     * @return shops inventory as BlockState
     */
    public boolean hasStorage() {
        return getStorage() != null;
    }

    /**
     * Returns the shops status as ShopStatus
     *
     * @return Shops status as ShopStatus
     */
    public ShopStatus getStatus() {
        return status;
    }

    /**
     * Sets the shops status to closed
     *
     * @param newStatus
     *         the new status
     */
    public void setStatus(ShopStatus newStatus) {
        status = newStatus;
    }

    /**
     * Sets the shop to open if the shop has all necessary information to make a trade
     *
     * @return true if shop opened
     */
    public ShopStatus setOpen() {
        setStatus(ShopStatus.OPEN);
        updateStatus();

        saveShop();
        updateSign();
        return status;
    }

    /**
     * Automatically updates a shops status if it is not CLOSED
     */
    public void updateStatus() {
        if (! status.equals(ShopStatus.CLOSED)) {
            if (! isMissingItems() && (chestLoc != null || shopType.equals(ShopType.ITRADE))) {
                if (shopType.equals(ShopType.ITRADE) || (getChestAsSC() != null && getChestAsSC().hasStock(product))) { setStatus(ShopStatus.OPEN); }
                else { setStatus(ShopStatus.OUT_OF_STOCK); }
            }
            else {
                setStatus(ShopStatus.INCOMPLETE);
            }
        }
    }

    /**
     * Checks if shop has necessary items to make a trade
     *
     * @return true if items are missing
     */
    public boolean isMissingItems() {
        return shopType.equals(ShopType.ITRADE) ? product.isEmpty() : product.isEmpty() || cost.isEmpty();
    }

    /**
     * Removes this shop from file
     */
    public void remove() {
        JsonConfiguration json = new JsonConfiguration(shopLoc.getLocation().getChunk());

        json.removeShop(shopLoc);
    }

    /**
     * Checks if shop is open
     *
     * @return true if open
     */
    public boolean isTradeable() {
        return status.isTradingAllowed();
    }

    /**
     * Switches the type of the shop between 'Trade' and 'BiTrade'
     */
    public void switchType() {
        if (shopType == ShopType.TRADE) { setShopType(ShopType.BITRADE); }
        else if (shopType == ShopType.BITRADE) { setShopType(ShopType.TRADE); }

        saveShop();
        updateSign();
    }

    /**
     * Returns a list of Managers' names
     *
     * @return List of Managers' names
     */
    public List<String> getManagersNames() {
        List<String> names = Arrays.asList(new String[getManagers().size()]);

        for (int i = 0; i < getManagers().size(); i++) {
            names.set(i, getManagers().get(i).getName());
        }

        return names;
    }

    /**
     * Returns a list of Members' names
     *
     * @return List of Members' names
     */
    public List<String> getMembersNames() {
        List<String> names = Arrays.asList(new String[getMembers().size()]);

        for (int i = 0; i < getMembers().size(); i++) {
            names.set(i, getMembers().get(i).getName());
        }

        return names;
    }

    /**
     * Returns a list of Members' and Managers' names
     *
     * @return List of Members' and Managers' names
     */
    public List<String> getUserNames() {
        List<String> users = new ArrayList<>();
        users.addAll(getManagersNames());
        users.addAll(getMembersNames());

        return users;
    }

    /**
     * Checks if all Costs in the list are valid for trade
     *
     * @return true if all costs are valid
     */
    public boolean areCostsValid() {
        for (ShopItemStack iS : cost) {
            if (! utils.isValidType(iS.getItemStack().getType())) { return false; }
        }

        return true;
    }

    /**
     * Checks if all Products in the list are valid for trade
     *
     * @return true if all products are valid
     */
    public boolean areProductsValid() {
        for (ShopItemStack iS : product) {
            if (! utils.isValidType(iS.getItemStack().getType())) { return false; }
        }

        return true;
    }

    /**
     * Returns true if shop has enough product to make trade
     *
     * @param multiplier
     *         multiplier to use for check
     *
     * @return true if shop has enough product to make trade
     */
    public Boolean checkProduct(int multiplier) {
        setStorageInventory();
        return utils.checkInventory(storageInv, product, multiplier);
    }

    /**
     * Returns true if shop has enough cost to make trade
     *
     * @param multiplier
     *         multiplier to use for check
     *
     * @return true if shop has enough cost to make trade
     */
    public Boolean checkCost(int multiplier) {
        setStorageInventory();
        return utils.checkInventory(storageInv, cost, multiplier);
    }
}