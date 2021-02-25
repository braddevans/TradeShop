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

package org.shanerx.tradeshop;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.tradeshop.commands.CommandCaller;
import org.shanerx.tradeshop.commands.CommandTabCaller;
import org.shanerx.tradeshop.enumys.Message;
import org.shanerx.tradeshop.enumys.Setting;
import org.shanerx.tradeshop.enumys.ShopSign;
import org.shanerx.tradeshop.enumys.ShopStorage;
import org.shanerx.tradeshop.listeners.*;
import org.shanerx.tradeshop.objects.Debug;
import org.shanerx.tradeshop.objects.ListManager;
import org.shanerx.tradeshop.utils.BukkitVersion;
import org.shanerx.tradeshop.utils.Updater;

/**
 * The type Trade shop.
 */
public class TradeShop extends JavaPlugin {

    private final NamespacedKey storageKey = new NamespacedKey(this, "tradeshop-storage-data");
    private final NamespacedKey signKey = new NamespacedKey(this, "tradeshop-sign-data");

    private ListManager lists;
    private BukkitVersion version;
    private ShopSign signs;
    private static TradeShop instance;

    private ShopStorage storages;

    private Debug debugger;

    public static TradeShop getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        version = new BukkitVersion();

        Setting.reload();
        Message.reload();

        debugger = new Debug();
        signs = new ShopSign();
        storages = new ShopStorage();
        lists = new ListManager();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinEventListener(), this);
        pm.registerEvents(new ShopProtectionListener(), this);
        pm.registerEvents(new ShopCreateListener(), this);
        pm.registerEvents(new ShopTradeListener(), this);
        pm.registerEvents(new ShopRestockListener(), this);

        getCommand("tradeshop").setExecutor(new CommandCaller());
        getCommand("tradeshop").setTabCompleter(new CommandTabCaller());

        /*
        if (Setting.CHECK_UPDATES.getBoolean()) {
            new Thread(() -> new Updater(getDescription()).checkCurrentVersion()).start();
        }
        */

    }

    /**
     * Gets storage key.
     *
     * @return the storage key
     */
    public NamespacedKey getStorageKey() {
        return storageKey;
    }

    /**
     * Gets sign key.
     *
     * @return the sign key
     */
    public NamespacedKey getSignKey() {
        return signKey;
    }

    /**
     * Gets list manager.
     *
     * @return the list manager
     */
    public ListManager getListManager() {
        return lists;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public BukkitVersion getVersion() {
        return version;
    }

    /**
     * Gets signs.
     *
     * @return the signs
     */
    public ShopSign getSigns() {
        return signs;
    }

    /**
     * Gets storages.
     *
     * @return the storages
     */
    public ShopStorage getStorages() {
        return storages;
    }

    /**
     * Gets updater.
     *
     * @return the updater
     */
    public Updater getUpdater() {
        return new Updater(getDescription());
    }

    /**
     * Gets debugger.
     *
     * @return the debugger
     */
    public Debug getDebugger() {
        return debugger;
    }
}
