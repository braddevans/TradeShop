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
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shanerx.tradeshop.TradeShop;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Set;
import java.util.logging.Level;

/**
 * The enum Message.
 */
@SuppressWarnings("UnstableApiUsage") public enum Message {

    /**
     * The Amount not num.
     */
    AMOUNT_NOT_NUM(MessageSectionKeys.UNUSED, "&cYou should have an amount before each item.", "\\Unused\\"),
    /**
     * The Buy failed sign.
     */
    BUY_FAILED_SIGN(MessageSectionKeys.UNUSED, "&cThis shop sign does not seem to be formatted correctly, please notify the owner.", "\\Unused\\"),
    /**
     * The Change closed.
     */
    CHANGE_CLOSED(MessageSectionKeys.NONE, "&cThe shop is now &l&bCLOSED&r&a."),
    /**
     * The Change open.
     */
    CHANGE_OPEN(MessageSectionKeys.NONE, "&aThe shop is now &l&bOPEN&r&a."),
    /**
     * Confirm trade message.
     */
    CONFIRM_TRADE(MessageSectionKeys.UNUSED, "&eTrade &6{AMOUNT1} {ITEM1} &efor &6{AMOUNT2} {ITEM2} &e?", "\\Unused\\"),
    /**
     * The Empty ts on setup.
     */
    EMPTY_TS_ON_SETUP(MessageSectionKeys.NONE, "&cTradeShop empty, please remember to fill it!", "Text to display when a player places a TradeSign above an empty chest:"),
    /**
     * The Existing shop.
     */
    EXISTING_SHOP(MessageSectionKeys.NONE, "&cYou may only have 1 shop per inventory block."),
    /**
     * The Feature disabled.
     */
    FEATURE_DISABLED(MessageSectionKeys.NONE, "&cThis feature has been disabled on this server!"),
    /**
     * The Full amount.
     */
    FULL_AMOUNT(MessageSectionKeys.UNUSED, "&cYou must have &e{AMOUNT} &cof a single type of &e{ITEM}&c!", "\\Unused\\"),
    /**
     * The Held empty.
     */
    HELD_EMPTY(MessageSectionKeys.NONE, "&eYou are currently holding nothing.", "Text to display when the player is not holding anything"),
    /**
     * The Illegal item.
     */
    ILLEGAL_ITEM(MessageSectionKeys.NONE, "&cYou cannot use one or more of those items in shops.", "Text to display when a shop failed creation due to an illegal item "),
    /**
     * The Insufficient items.
     */
    INSUFFICIENT_ITEMS(MessageSectionKeys.NONE, "&cYou do not have &e{AMOUNT} {ITEM}&c!", "Text to display when the player does not have enough items:"),
    /**
     * The Invalid arguments.
     */
    INVALID_ARGUMENTS(MessageSectionKeys.NONE, "&eTry &6/tradeshop help &eto display help!", "Text to display when invalid arguments are submitted through the \"/tradeshop\" command:"),
    /**
     * The Invalid sign.
     */
    INVALID_SIGN(MessageSectionKeys.UNUSED, "&cInvalid sign format!", "\\Unused\\"),
    /**
     * The Invalid subcommand.
     */
    INVALID_SUBCOMMAND(MessageSectionKeys.UNUSED, "&cInvalid sub-command. Cannot display usage.", "\\Unused\\"),
    /**
     * The Item added.
     */
    ITEM_ADDED(MessageSectionKeys.NONE, "&aItem successfully added to shop."),
    /**
     * The Item not removed.
     */
    ITEM_NOT_REMOVED(MessageSectionKeys.NONE, "&cItem could not be removed from shop."),
    /**
     * The Item removed.
     */
    ITEM_REMOVED(MessageSectionKeys.NONE, "&aItem successfully removed to shop."),
    /**
     * The Missing chest.
     */
    MISSING_CHEST(MessageSectionKeys.NONE, "&cYour shop is missing a chest."),
    /**
     * The Missing item.
     */
    MISSING_ITEM(MessageSectionKeys.NONE, "&cYour sign is missing an item for trade.", "Text to display when a shop sign failed creation due to missing an item"),
    /**
     * The Missing shop.
     */
    MISSING_SHOP(MessageSectionKeys.UNUSED, "&cThere is not currently a shop here, please tell the owner or come back later!", "\\Unused\\"),
    /**
     * The Multi amount.
     */
    MULTI_AMOUNT(MessageSectionKeys.NONE, "&aYour trade multiplier is %amount%."),
    /**
     * The Multi update.
     */
    MULTI_UPDATE(MessageSectionKeys.NONE, "&aTrade multiplier has been updated to %amount%."),
    /**
     * The No chest.
     */
    NO_CHEST(MessageSectionKeys.NONE, "&cYou need to put a chest under the sign!", "Text to display when a player attempts to place a sign without placing the chest first:"),
    /**
     * The No command permission.
     */
    NO_COMMAND_PERMISSION(MessageSectionKeys.NONE, "&aYou do not have permission to execute this command", "Text to display when a player attempts to run administrator commands:"),
    /**
     * The No edit.
     */
    NO_EDIT(MessageSectionKeys.NONE, "&cYou do not have permission to edit this shop."),
    /**
     * The No shop permission.
     */
    NO_SHOP_PERMISSION(MessageSectionKeys.NONE, "&cYou do not have permission to edit that shop."),
    /**
     * The No sighted shop.
     */
    NO_SIGHTED_SHOP(MessageSectionKeys.NONE, "&cNo shop in range!", "Text to display when a player is too far from a shop"),
    /**
     * The No ts create permission.
     */
    NO_TS_CREATE_PERMISSION(MessageSectionKeys.NONE, "&cYou don't have permission to create TradeShops!", "Text to display when a player attempts to setup a shoptype they are not allowed to create:"),
    /**
     * The No ts destroy.
     */
    NO_TS_DESTROY(MessageSectionKeys.NONE, "&cYou may not destroy that TradeShop", "Text to display when a player attempts to destroy a shop they do not own:"),
    /**
     * The No ts open.
     */
    NO_TS_OPEN(MessageSectionKeys.NONE, "&cThat TradeShop does not belong to you", "Text to display when a player attempts to open a shop they do not own nor have been granted access to (1.6):"),
    /**
     * The On trade.
     */
    ON_TRADE(MessageSectionKeys.NONE, "&aYou have traded your &e{AMOUNT2} {ITEM2} &afor &e{AMOUNT1} {ITEM1} &awith {SELLER}", "Text to display upon a successful trade:"),
    /**
     * The Player full.
     */
    PLAYER_FULL(MessageSectionKeys.NONE, "&cYour inventory is full, please make room before trading items!", "Text to display when the players inventory is too full to recieve the trade:"),
    /**
     * The Player not found.
     */
    PLAYER_NOT_FOUND(MessageSectionKeys.NONE, "&cThat player could not be found."),
    /**
     * The Player only command.
     */
    PLAYER_ONLY_COMMAND(MessageSectionKeys.NONE, "&eThis command is only available to players.", "Text to display when console tries to use a player only command"),
    /**
     * The Plugin behind.
     */
    PLUGIN_BEHIND(MessageSectionKeys.NONE, "&cThe server is running an old version of TradeShop, please update the plugin."),
    /**
     * The Self owned.
     */
    SELF_OWNED(MessageSectionKeys.NONE, "&cYou cannot buy from a shop in which you are a user.", "Text to display when a player tries to buy form a shop in which they are a user"),
    /**
     * The Setup help.
     */
    SETUP_HELP(MessageSectionKeys.NONE, "\n&2Setting up a TradeShop is easy! Just make sure to follow these steps:"
                                        + "\n \nStep 1: &ePlace down a chest."
                                        + "\n&2Step 2: &ePlace a sign on top of or around the chest."
                                        + "\n&2Step 3: &eWrite the following on the sign"
                                        + "\n&6     [%header%]"
                                        + "\n&6&o-- Leave Blank --"
                                        + "\n&6&o-- Leave Blank --"
                                        + "\n&6&o-- Leave Blank --"
                                        + "\n&2Step 4: &eUse the addCost and addProduct commands to add items to your shop", "Text to display on \"/tradeshop setup\":"),
    /**
     * The Shop closed.
     */
    SHOP_CLOSED(MessageSectionKeys.NONE, "&cThis shop is currently closed."),
    /**
     * The Shop empty.
     */
    SHOP_EMPTY(MessageSectionKeys.NONE, "&cThis TradeShop is currently &emissing &citems to complete the trade!", "Text to display when the shop does not have enough stock:"),
    /**
     * The Shop full.
     */
    SHOP_FULL(MessageSectionKeys.NONE, "&cThis TradeShop is full, please contact the owner to get it emptied!", "Text to display when the shop storage is full:"),
    /**
     * The Shop full amount.
     */
    SHOP_FULL_AMOUNT(MessageSectionKeys.UNUSED, "&cThe shop does not have &e{AMOUNT} &cof a single type of &e{ITEM}&c!", "\\Unused\\"),
    /**
     * The Shop insufficient items.
     */
    SHOP_INSUFFICIENT_ITEMS(MessageSectionKeys.NONE, "&cThis shop does not have &e{AMOUNT} {ITEM}&c!"),
    /**
     * The Shop item list.
     */
    SHOP_ITEM_LIST(MessageSectionKeys.NONE, "&aThe shops %type%:\n%list%"),
    /**
     * The Shop type switched.
     */
    SHOP_TYPE_SWITCHED(MessageSectionKeys.NONE, "&aShop type has been switched to %newtype%."),
    /**
     * The Successful setup.
     */
    SUCCESSFUL_SETUP(MessageSectionKeys.NONE, "&aYou have successfully setup a TradeShop!", "Text to display when a player successfully creates a TradeShop:"),
    /**
     * The Too many chests.
     */
    TOO_MANY_CHESTS(MessageSectionKeys.NONE, "&cThere are too many shops in this chunk, you can not add another one."),
    /**
     * The Too many items.
     */
    TOO_MANY_ITEMS(MessageSectionKeys.NONE, "&cThis trade can not take any more %side%!"),
    /**
     * The Unsuccessful shop members.
     */
    UNSUCCESSFUL_SHOP_MEMBERS(MessageSectionKeys.NONE, "&aThat player is either already on the shop, or you have reached the maximum number of users!", "Text to display when shop users could not be updated"),
    /**
     * The Updated shop members.
     */
    UPDATED_SHOP_MEMBERS(MessageSectionKeys.NONE, "&aShop owners and members have been updated!", "Text to display when shop users have been updated successfully"),
    /**
     * The Who message.
     */
    WHO_MESSAGE(MessageSectionKeys.NONE, "&6Shop users are:\n&2Owner: &e{OWNER}\n&2Managers: &e{MANAGERS}\n&2Members: &e{MEMBERS}", "Text to display when players use the who command"),
    /**
     * The Various item type.
     */
    VARIOUS_ITEM_TYPE(MessageSectionKeys.NONE, "Various", "Text to display when a message uses an Item Type and the Type varies");

    private static final char COLOUR_CHAR = '&';
    private static final File file = new File(TradeShop.getInstance().getDataFolder(), "messages.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    private static String PREFIX = Setting.MESSAGE_PREFIX.getString() + " ";

    private final String defaultValue;
    private String preComment = "";
    private String postComment = "";
    private final MessageSectionKeys sectionKey;

    Message(MessageSectionKeys sectionKey, String defaultValue) {
        this.sectionKey = sectionKey;
        this.defaultValue = defaultValue;
    }

    Message(MessageSectionKeys sectionKey, String defaultValue, String preComment) {
        this.sectionKey = sectionKey;
        this.defaultValue = defaultValue;
        this.preComment = preComment;
    }

    Message(MessageSectionKeys sectionKey, String defaultValue, String preComment, String postComment) {
        this.sectionKey = sectionKey;
        this.defaultValue = defaultValue;
        this.preComment = preComment;
        this.postComment = postComment;
    }

    /**
     * Sets defaults.
     */
    public static void setDefaults() {
        config = YamlConfiguration.loadConfiguration(file);

        for (Message message : Message.values()) {
            if (config.get(message.getPath()) == null) {
                config.set(message.getPath(), message.defaultValue);
            }
        }

        save();
    }

    private static void save() {
        Validate.notNull(file, "File cannot be null");

        if (config != null) {
            try {
                Files.createParentDirs(file);

                StringBuilder data = new StringBuilder();

                data.append("###########################\n").append("#    TradeShop Messages   #\n").append("###########################\n");
                Set<MessageSectionKeys> messageSectionKeys = Sets.newHashSet(MessageSectionKeys.values());
                messageSectionKeys.remove(MessageSectionKeys.UNUSED);

                for (Message message : values()) {
                    if (! message.sectionKey.equals(MessageSectionKeys.UNUSED)) {
                        if (messageSectionKeys.contains(message.sectionKey)) {
                            data.append(message.sectionKey.getFormattedHeader());
                            messageSectionKeys.remove(message.sectionKey);
                        }

                        if (! message.preComment.isEmpty()) {
                            data.append("# ").append(message.preComment).append("\n");
                        }

                        data.append(message.sectionKey.getValueLead()).append(message.getPath()).append(": ").append(new Yaml().dump(message.getMessage()));

                        if (! message.postComment.isEmpty()) {
                            data.append(message.postComment).append("\n");
                        }
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
            if (! TradeShop.getInstance().getDataFolder().isDirectory()) {
                TradeShop.getInstance().getDataFolder().mkdirs();
            }
            if (! file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException e) {
            TradeShop.getInstance().getLogger().log(Level.SEVERE, "Could not create Message file! Disabling plugin!", e);
            TradeShop.getInstance().getServer().getPluginManager().disablePlugin(TradeShop.getInstance());
        }

        setDefaults();
        config = YamlConfiguration.loadConfiguration(file);

        PREFIX = Setting.MESSAGE_PREFIX.getString();
    }

    /**
     * Colour string.
     *
     * @param x
     *         the x
     *
     * @return the string
     */
    public static String colour(String x) {
        return ChatColor.translateAlternateColorCodes(COLOUR_CHAR, x);
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return sectionKey.getKey() + name().toLowerCase().replace("_", "-");
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return config.getString(getPath());
    }

    @Override
    public String toString() {
        return colour(getMessage().replace("%header%", Setting.TRADESHOP_HEADER.getString()));
    }

    /**
     * Gets prefixed.
     *
     * @return the prefixed
     */
    public String getPrefixed() {
        return colour(PREFIX + toString());
    }
}

/**
 * The enum Message section keys.
 */
enum MessageSectionKeys {

    /**
     * None message section keys.
     */
    NONE("", ""),
    /**
     * Unused message section keys.
     */
    UNUSED("", "");

    private final String key;
    private final String sectionHeader;
    private String value_lead = "";
    private MessageSectionKeys parent;

    MessageSectionKeys(String key, String sectionHeader) {
        this.key = key;
        this.sectionHeader = sectionHeader;
        if (! key.isEmpty()) { this.value_lead = "  "; }
    }

    MessageSectionKeys(MessageSectionKeys parent, String key, String sectionHeader) {
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
