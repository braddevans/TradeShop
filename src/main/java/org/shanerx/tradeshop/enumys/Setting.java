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

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shanerx.tradeshop.TradeShop;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * The enum Setting.
 */
@SuppressWarnings({"UnstableApiUsage", "ResultOfMethodCallIgnored"}) public enum Setting {

    /**
     * Config version setting.
     */
    CONFIG_VERSION(SettingSectionKeys.NONE, "config-version", 1.1, "", "\n"),

    /**
     * The Enable debug.
     */
// System Options
    ENABLE_DEBUG(SettingSectionKeys.SYSTEM_OPTIONS, "enable-debug", 0, "What debug code should be run. this will add significant amounts of spam to the console/log, generally not used unless requested by Devs (must be a whole number)"),
    /**
     * The Check updates.
     */
    CHECK_UPDATES(SettingSectionKeys.SYSTEM_OPTIONS, "check-updates", true, "Should we check for updates when the server starts"),
    /**
     * The Allow metrics.
     */
    ALLOW_METRICS(SettingSectionKeys.SYSTEM_OPTIONS, "allow-metrics", true, "Allow us to connect anonymous metrics so we can see how our plugin is being used to better develop it", "\n"),

    /**
     * The Message prefix.
     */
// Language Options
    MESSAGE_PREFIX(SettingSectionKeys.LANGUAGE_OPTIONS, "message-prefix", "&a[&eTradeShop&a] ", "The prefix the displays before all plugin messages", "\n"),

    /**
     * The Shop good colour.
     */
    SHOP_GOOD_COLOUR(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-good-colour", "&2", "Header Colours, if the codes are showing in the header, set to \"\"\n  # Color for successfully created and stocked signs"),
    /**
     * The Shop incomplete colour.
     */
    SHOP_INCOMPLETE_COLOUR(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-incomplete-colour", "&7", "Color for shops that are missing data to make trades"),
    /**
     * The Shop bad colour.
     */
    SHOP_BAD_COLOUR(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-bad-colour", "&4", "Color for shops that were not successfully created", "\n"),

    /**
     * The Shop open status.
     */
    SHOP_OPEN_STATUS(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-open-status", "&a<Open>", "Status Text, What will be shown in the bottom line of shop sign for each status\n  # Open"),
    /**
     * Shop closed status setting.
     */
    SHOP_CLOSED_STATUS(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-closed-status", "&c<Closed>", "Closed"),
    /**
     * Shop incomplete status setting.
     */
    SHOP_INCOMPLETE_STATUS(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-incomplete-status", "&c<Incomplete>", "Incomplete"),
    /**
     * The Shop outofstock status.
     */
    SHOP_OUTOFSTOCK_STATUS(SettingSectionKeys.LANGUAGE_OPTIONS, "shop-outofstock-status", "&c<Out Of Stock>", "Out of Stock", "\n"),

    /**
     * The Allowed directions.
     */
// Global Options
    ALLOWED_DIRECTIONS(SettingSectionKeys.GLOBAL_OPTIONS, "allowed-directions", new String[]{"DOWN", "WEST", "SOUTH", "EAST", "NORTH", "UP"}, "Directions an allowed shop can be from a sign. Allowed directions are:\n  # Up, Down, North, East, South, West"),
    /**
     * The Allowed shops.
     */
    ALLOWED_SHOPS(SettingSectionKeys.GLOBAL_OPTIONS, "allowed-shops", new String[]{"CHEST", "TRAPPED_CHEST", "SHULKER"}, "Inventories to allow for shops. Allowed blocks are:\n  # Chest, Trapped_Chest, Dropper, Hopper, Dispenser, Shulker, ..."),
    /**
     * The Max edit distance.
     */
    MAX_EDIT_DISTANCE(SettingSectionKeys.GLOBAL_OPTIONS, "max-edit-distance", 4, "Max distance a player can be from a shop to edit it (must be a whole number)"),
    /**
     * The Illegal items.
     */
    ILLEGAL_ITEMS(SettingSectionKeys.GLOBAL_OPTIONS, "illegal-items", new String[]{"Air", "Void_Air", "Cave_Air", "Bedrock", "Command_Block"}, "Material types that cannot be used in trades", "\n"),

    /**
     * The Allow multi trade.
     */
// ^ Multi Trade
    ALLOW_MULTI_TRADE(SettingSectionKeys.GLOBAL_MULTI_TRADE, "enable", true, "Should we allow multi trades with shift + click (true/false)"),
    /**
     * The Multi trade default.
     */
    MULTI_TRADE_DEFAULT(SettingSectionKeys.GLOBAL_MULTI_TRADE, "default-multi", 2, "Default multiplier for trades using shift + click (must be a whole number)"),
    /**
     * The Multi trade max.
     */
    MULTI_TRADE_MAX(SettingSectionKeys.GLOBAL_MULTI_TRADE, "max-multi", 6, "Maximum amount a player can set their multiplier to, reset upon leaving (must be a whole number)", "\n"),

    /**
     * The Max shop users.
     */
// Shop Options
    MAX_SHOP_USERS(SettingSectionKeys.SHOP_OPTIONS, "max-shop-users", 5, "Maximum users a shop can have (must be a whole number)"),
    /**
     * The Max shops per chunk.
     */
    MAX_SHOPS_PER_CHUNK(SettingSectionKeys.SHOP_OPTIONS, "max-shops-per-chunk", 128, "Maximum shops that can exist in a single chunk (must be a whole number)"),
    /**
     * The Max items per trade side.
     */
    MAX_ITEMS_PER_TRADE_SIDE(SettingSectionKeys.SHOP_OPTIONS, "max-items-per-trade-side", 6, "Maximum amount of item stacks per side of trade (must be a whole number)"),
    /**
     * The Allow user purchasing.
     */
    ALLOW_USER_PURCHASING(SettingSectionKeys.SHOP_OPTIONS, "allow-user-purchasing", false, "Can players purchase from a shop in which they are a user of (true/false)"),
    /**
     * The Multiple items on sign.
     */
    MULTIPLE_ITEMS_ON_SIGN(SettingSectionKeys.SHOP_OPTIONS, "multiple-items-on-sign", "Use '/ts what'", "Text that shows on trade signs that contain more than 1 item", "\n"),

    /**
     * The Tradeshop header.
     */
// Trade Shop Options
    TRADESHOP_HEADER(SettingSectionKeys.TRADE_SHOP_OPTIONS, "header", "Trade", "The header that appears at the top of the shop signs, this is also what the player types to create the sign"),
    /**
     * The Tradeshop explode.
     */
    TRADESHOP_EXPLODE(SettingSectionKeys.TRADE_SHOP_OPTIONS, "allow-explode", false, "Can explosions damage the shop sign/storage (true/false)"),
    /**
     * The Tradeshop hopper export.
     */
    TRADESHOP_HOPPER_EXPORT(SettingSectionKeys.TRADE_SHOP_OPTIONS, "allow-hopper-export", false, "Can hoppers pull items from the shop storage (true/false)", "\n"),

    /**
     * The Itradeshop owner.
     */
// ITrade Shop Options
    ITRADESHOP_OWNER(SettingSectionKeys.ITRADE_SHOP_OPTIONS, "owner", "Server Shop", "Name to put on the bottom of iTrade signs"),
    /**
     * The Itradeshop header.
     */
    ITRADESHOP_HEADER(SettingSectionKeys.ITRADE_SHOP_OPTIONS, "header", "iTrade", "The header that appears at the top of the shop signs, this is also what the player types to create the sign"),
    /**
     * The Itradeshop explode.
     */
    ITRADESHOP_EXPLODE(SettingSectionKeys.ITRADE_SHOP_OPTIONS, "allow-explode", false, "Can explosions damage the shop sign (true/false)", "\n"),

    /**
     * The Bitradeshop header.
     */
// BiTrade Shop Options
    BITRADESHOP_HEADER(SettingSectionKeys.BITRADE_SHOP_OPTIONS, "header", "BiTrade", "The header that appears at the top of the shop signs, this is also what the player types to create the sign"),
    /**
     * The Bitradeshop explode.
     */
    BITRADESHOP_EXPLODE(SettingSectionKeys.BITRADE_SHOP_OPTIONS, "allow-explode", false, "Can explosions damage the shop sign/storage (true/false)"),
    /**
     * The Bitradeshop hopper export.
     */
    BITRADESHOP_HOPPER_EXPORT(SettingSectionKeys.BITRADE_SHOP_OPTIONS, "allow-hopper-export", false, "Can hoppers pull items from the shop storage (true/false)");

    private static final File file = new File(TradeShop.getInstance().getDataFolder(), "config.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    private final String key;
    private final String path;
    private String preComment = "";
    private String postComment = "";
    private final Object defaultValue;
    private final SettingSectionKeys sectionKey;

    Setting(SettingSectionKeys sectionKey, String path, Object defaultValue) {
        this.sectionKey = sectionKey;
        this.key = path;
        this.path = sectionKey.getKey() + path;
        this.defaultValue = defaultValue;
    }

    Setting(SettingSectionKeys sectionKey, String path, Object defaultValue, String preComment) {
        this.sectionKey = sectionKey;
        this.key = path;
        this.path = sectionKey.getKey() + path;
        this.defaultValue = defaultValue;
        this.preComment = preComment;
    }

    Setting(SettingSectionKeys sectionKey, String path, Object defaultValue, String preComment, String postComment) {
        this.sectionKey = sectionKey;
        this.key = path;
        this.path = sectionKey.getKey() + path;
        this.defaultValue = defaultValue;
        this.preComment = preComment;
        this.postComment = postComment;
    }

    /**
     * Gets item black list.
     *
     * @return the item black list
     */
    public static ArrayList<String> getItemBlackList() {
        ArrayList<String> blacklist = new ArrayList<>();
        blacklist.add("air");
        blacklist.addAll(Setting.ILLEGAL_ITEMS.getStringList());

        return blacklist;
    }

    /**
     * Find setting setting.
     *
     * @param search
     *         the search
     *
     * @return the setting
     */
    public static Setting findSetting(String search) {
        return valueOf(search.toUpperCase().replace("-", "_"));
    }

    private static void setDefaults() {
        config = YamlConfiguration.loadConfiguration(file);

        for (Setting set : Setting.values()) {
            addSetting(set.path, set.defaultValue);
        }

        save();
    }

    private static void addSetting(String node, Object value) {
        if (config.get(node) == null) {
            config.set(node, value);
        }
    }

    private static void save() {
        Validate.notNull(file, "File cannot be null");

        if (config != null) {
            try {
                Files.createParentDirs(file);

                StringBuilder data = new StringBuilder();

                data.append("##########################\n").append("#    TradeShop Config    #\n").append("##########################\n");
                Set<SettingSectionKeys> settingSectionKeys = Sets.newHashSet(SettingSectionKeys.values());

                for (Setting setting : values()) {
                    if (settingSectionKeys.contains(setting.sectionKey)) {
                        data.append(setting.sectionKey.getFormattedHeader());
                        settingSectionKeys.remove(setting.sectionKey);
                    }

                    if (! setting.preComment.isEmpty()) {
                        data.append(setting.sectionKey.getValueLead()).append("# ").append(setting.preComment).append("\n");
                    }

                    data.append(setting.sectionKey.getValueLead()).append(setting.key).append(": ").append(new Yaml().dump(setting.getSetting()));

                    if (! setting.postComment.isEmpty()) {
                        data.append(setting.postComment).append("\n");
                    }
                }

                try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8)) {
                    writer.write(data.toString());
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reload.
     */
    public static void reload() {
        try {
            if (!TradeShop.getInstance().getDataFolder().isDirectory()) {
                TradeShop.getInstance().getDataFolder().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException e) {
            TradeShop.getInstance().getLogger().log(Level.SEVERE, "Could not create Config file! Disabling plugin!", e);
            TradeShop.getInstance().getServer().getPluginManager().disablePlugin(TradeShop.getInstance());
        }

        fixUp();

        setDefaults();
        config = YamlConfiguration.loadConfiguration(file);
    }

    // Method to fix any values that have changed with updates
    private static void fixUp() {
        boolean changes = false;

        //Changes if CONFIG_VERSION is below 1, then sets config version to 1.0
        if (CONFIG_VERSION.getDouble() < 1.0) {
            CONFIG_VERSION.setSetting(1.0);
            changes = true;
        }

        // 2.2.2 Changed enable debug from true/false to integer
        if (! config.isInt(ENABLE_DEBUG.path)) {
            ENABLE_DEBUG.clearSetting();
            changes = true;
        }

        // 2.2.2 Better Sorted/potentially commented config
        if (CONFIG_VERSION.getDouble() < 1.1) {
            if (config.contains("itradeshop.owner")) {
                config.set(ITRADESHOP_OWNER.path, config.get("itradeshop.owner"));
                config.set("itradeshop.owner", null);
                changes = true;
            }

            if (config.contains("itradeshop.header")) {
                config.set(ITRADESHOP_HEADER.path, config.get("itradeshop.header"));
                config.set("itradeshop.header", null);
                changes = true;
            }

            if (config.contains("itradeshop.allow-explode")) {
                config.set(ITRADESHOP_EXPLODE.path, config.get("itradeshop.allow-explode"));
                config.set("itradeshop.allow-explode", null);
                changes = true;
            }

            if (config.contains("tradeshop.header")) {
                config.set(TRADESHOP_HEADER.path, config.get("tradeshop.header"));
                config.set("tradeshop.header", null);
                changes = true;
            }

            if (config.contains("tradeshop.allow-explode")) {
                config.set(TRADESHOP_EXPLODE.path, config.get("tradeshop.allow-explode"));
                config.set("tradeshop.allow-explode", null);
                changes = true;
            }

            if (config.contains("tradeshop.allow-hopper-export")) {
                config.set(TRADESHOP_HOPPER_EXPORT.path, config.get("tradeshop.allow-hopper-export"));
                config.set("tradeshop.allow-hopper-export", null);
                changes = true;
            }

            if (config.contains("bitradeshop.header")) {
                config.set(BITRADESHOP_HEADER.path, config.get("bitradeshop.header"));
                config.set("bitradeshop.header", null);
                changes = true;
            }

            if (config.contains("bitradeshop.allow-explode")) {
                config.set(BITRADESHOP_EXPLODE.path, config.get("bitradeshop.allow-explode"));
                config.set("bitradeshop.allow-explode", null);
                changes = true;
            }

            if (config.contains("bitradeshop.allow-hopper-export")) {
                config.set(BITRADESHOP_HOPPER_EXPORT.path, config.get("bitradeshop.allow-hopper-export"));
                config.set("bitradeshop.allow-hopper-export", null);
                changes = true;
            }

            CONFIG_VERSION.setSetting(1.1);
        }

        if (changes) { save(); }
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public static FileConfiguration getConfig() {
        return config;
    }

    /**
     * To path string.
     *
     * @return the string
     */
    public String toPath() {
        return path;
    }

    /**
     * Clear setting.
     */
    public void clearSetting() {
        config.set(toPath(), null);
    }

    /**
     * Gets setting.
     *
     * @return the setting
     */
    public Object getSetting() {
        return config.get(toPath());
    }

    /**
     * Sets setting.
     *
     * @param obj
     *         the obj
     */
    public void setSetting(Object obj) {
        config.set(toPath(), obj);
    }

    /**
     * Gets string.
     *
     * @return the string
     */
    public String getString() {
        return config.getString(toPath());
    }

    /**
     * Gets string list.
     *
     * @return the string list
     */
    public List<String> getStringList() {
        return config.getStringList(toPath());
    }

    /**
     * Gets int.
     *
     * @return the int
     */
    public int getInt() {
        return config.getInt(toPath());
    }

    /**
     * Gets double.
     *
     * @return the double
     */
    public double getDouble() {
        return config.getDouble(toPath());
    }

    /**
     * Gets boolean.
     *
     * @return the boolean
     */
    public boolean getBoolean() {
        return config.getBoolean(toPath());
    }
}

/**
 * The enum Setting section keys.
 */
enum SettingSectionKeys {

    /**
     * None setting section keys.
     */
    NONE("", ""),
    /**
     * The System options.
     */
    SYSTEM_OPTIONS("system-options", "System Options"),
    /**
     * The Language options.
     */
    LANGUAGE_OPTIONS("language-options", "Language Options"),
    /**
     * The Global options.
     */
    GLOBAL_OPTIONS("global-options", "Global Options"),
    /**
     * Global multi trade setting section keys.
     */
    GLOBAL_MULTI_TRADE(GLOBAL_OPTIONS, "multi-trade", ""),
    /**
     * The Shop options.
     */
    SHOP_OPTIONS("shop-options", "Shop Options"),
    /**
     * The Trade shop options.
     */
    TRADE_SHOP_OPTIONS("trade-shop-options", "Trade Shop Options"),
    /**
     * The Itrade shop options.
     */
    ITRADE_SHOP_OPTIONS("itrade-shop-options", "ITrade Shop Options"),
    /**
     * The Bitrade shop options.
     */
    BITRADE_SHOP_OPTIONS("bitrade-shop-options", "BiTrade Shop Options");

    private final String key;
    private final String sectionHeader;
    private String value_lead = "";
    private SettingSectionKeys parent;

    SettingSectionKeys(String key, String sectionHeader) {
        this.key = key;
        this.sectionHeader = sectionHeader;
        if (! key.isEmpty()) { this.value_lead = "  "; }
    }

    SettingSectionKeys(SettingSectionKeys parent, String key, String sectionHeader) {
        this.key = key;
        this.sectionHeader = sectionHeader;
        this.parent = parent;
        if (! key.isEmpty()) { this.value_lead = parent.value_lead + "  "; }
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return ! key.isEmpty() ? (parent != null ? parent.getKey() + "." + key + "." : key + ".") : "";
    }

    /**
     * Gets value lead.
     *
     * @return the value lead
     */
    public String getValueLead() {
        return value_lead;
    }

    /**
     * Gets formatted header.
     *
     * @return the formatted header
     */
    public String getFormattedHeader() {
        if (! sectionHeader.isEmpty() && ! key.isEmpty()) {
            StringBuilder header = new StringBuilder();
            header.append("|    ").append(sectionHeader).append("    |");

            int line1Length = header.length();

            header.insert(0, "# ").append("\n").append("# ");

            while (line1Length > 0) {
                header.append("^");
                line1Length--;
            }

            header.append("\n").append(getFileText()).append(":\n");

            return header.toString();
        }
        else if (sectionHeader.isEmpty() && ! key.isEmpty()) {

            return getFileText() + ":\n";
        }

        return "";
    }

    /**
     * Gets file text.
     *
     * @return the file text
     */
    public String getFileText() {
        return parent != null ? parent.value_lead + key : key;
    }
}