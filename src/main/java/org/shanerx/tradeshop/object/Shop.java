/*
 *     Copyright (c) 2016-2017 SparklingComet @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *              http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * NOTICE: All modifications made by others to the source code belong
 * to the respective contributor. No contributor should be held liable for
 * any damages of any kind, whether be material or moral, which were
 * caused by their contribution(s) to the project. See the full License for more information
 */

package org.shanerx.tradeshop.object;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.shanerx.tradeshop.enums.ShopRole;
import org.shanerx.tradeshop.util.Tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Shop implements Serializable {

    private ShopUser owner;
    private List<ShopUser> managers, members;
    private Material shopType;
    private Location shopLoc, chestLoc;
	private ItemStack sellItem, buyItem;

    public Shop(Tuple<Location, Location> locations, Material shopType, ShopUser owner, Tuple<List<ShopUser>, List<ShopUser>> players, Tuple<ItemStack, ItemStack> items) {
        shopLoc = locations.getLeft();
        this.owner = owner;
		chestLoc = locations.getRight();
		this.shopType = shopType;
		managers = players.getLeft();
		members = players.getRight();
		sellItem = items.getLeft();
		buyItem = items.getRight();
	}

	@Deprecated
	public Shop() {
	}

	public Location getInventoryLocation() {
		return chestLoc;
	}

    public void setOwner(ShopUser owner) {
        this.owner = owner;
    }

    public ShopUser getOwner() {
        return owner;
    }

    public void setManagers(List<ShopUser> managers) {
        this.managers = managers;
    }

    public List<ShopUser> getManagers() {
        return managers;
    }

    public void setMembers(List<ShopUser> members) {
        this.members = members;
    }

    public List<ShopUser> getMembers() {
        return members;
    }

    public void addManager(ShopUser newManager) {
        managers.add(newManager);
    }

    public void removeManager(ShopUser oldManager) {
        managers.remove(oldManager);
    }

    public void addMember(ShopUser newMember) {
        members.add(newMember);
    }

    public void removeMember(ShopUser oldMember) {
        members.remove(oldMember);
    }

	public List<UUID> getManagersUUID() {
        return managers.stream().map(ShopUser::getUUID).collect(Collectors.toList());
    }

	public List<UUID> getMembersUUID() {
        return members.stream().map(ShopUser::getUUID).collect(Collectors.toList());
    }

	private List<ShopUser> managersFromUUIDs(String... uuids) {
		List<ShopUser> managers = new ArrayList<>();
        for (String str : uuids) {
            managers.add(new ShopUser(Bukkit.getPlayer(UUID.fromString(str)), ShopRole.MANAGER));
        }

		return managers;
	}

	private List<ShopUser> membersFromUUIDs(String... uuids) {
		List<ShopUser> members = new ArrayList<>();
        for (String str : uuids) {
            members.add(new ShopUser(Bukkit.getPlayer(UUID.fromString(str)), ShopRole.MEMBER));
        }

		return members;
	}

	public void setChestLocation(Location newLoc) {
		chestLoc = newLoc;
	}

	public Location getShopLocation() {
		return shopLoc;
	}

	public void setShopType(Material newMat) {
		shopType = newMat;
	}

	public Material getShopType() {
		return shopType;
	}

	public void setBuyItem(ItemStack newItem) {
		buyItem = newItem;
	}

	public ItemStack getBuyItem() {
		return buyItem;
	}

	public void setSellItem(ItemStack newItem) {
		sellItem = newItem;
	}

	public ItemStack getSellItem() {
		return sellItem;
	}

	private String serializeLocation(Location loc) {
		return new Gson().toJson(loc);
	}

	private static Location deserializeLocation(String loc) {
		return new Gson().fromJson(loc, Location.class);
	}

	public String serialize() {
		return new Gson().toJson(this);
	}

	public static Shop deserialize(String serialized) {
		return new Gson().fromJson(serialized, Shop.class);
	}

	public ShopLocation getShopLocationAsSL() {
		return new ShopLocation(shopLoc);
	}

	public ShopLocation getInventoryLocationAsSL() {
		return new ShopLocation(chestLoc);
	}
}
