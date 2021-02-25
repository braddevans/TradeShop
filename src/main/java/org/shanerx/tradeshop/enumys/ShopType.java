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

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.shanerx.tradeshop.TradeShop;

import java.io.Serializable;

/**
 * The enum Shop type.
 */
public enum ShopType implements Serializable {

    /**
     * Trade shop type.
     */
    TRADE(Setting.TRADESHOP_HEADER.getString(), Permissions.CREATE),

    /**
     * Itrade shop type.
     */
    ITRADE(Setting.ITRADESHOP_HEADER.getString(), Permissions.CREATEI),

    /**
     * Bitrade shop type.
     */
    BITRADE(Setting.BITRADESHOP_HEADER.getString(), Permissions.CREATEBI);

    private transient static TradeShop plugin = (TradeShop) Bukkit.getPluginManager().getPlugin("TradeShop");
    private String key;
    private transient Permissions perm;

    ShopType(String key, Permissions perm) {
        this.key = key;
        this.perm = perm;
    }

    /**
     * Is shop boolean.
     *
     * @param s
     *         the s
     *
     * @return the boolean
     */
    public static boolean isShop(Sign s) {
        return getType(s) != null;
    }

    /**
     * Is shop boolean.
     *
     * @param b
     *         the b
     *
     * @return the boolean
     */
    public static boolean isShop(Block b) {
        if (b != null && plugin.getSigns().getSignTypes().contains(b.getType())) {
            return getType((Sign) b.getState()) != null;
        }

        return false;
    }

    /**
     * Gets type.
     *
     * @param s
     *         the s
     *
     * @return the type
     */
    public static ShopType getType(Sign s) {
        String header = ChatColor.stripColor(s.getLine(0));

        if (header.equalsIgnoreCase(TRADE.toHeader())) {
            return TRADE;

        }
        else if (header.equalsIgnoreCase(ITRADE.toHeader())) {
            return ITRADE;

        }
        else if (header.equalsIgnoreCase(BITRADE.toHeader())) {
            return BITRADE;
        }

        return null;
    }

    /**
     * Deserialize shop role.
     *
     * @param serialized
     *         the serialized
     *
     * @return the shop role
     */
    public static ShopRole deserialize(String serialized) {
        ShopRole shopRole = new Gson().fromJson(serialized, ShopRole.class);
        return shopRole;
    }

    @Override
    public String toString() {
        return key;
    }

    /**
     * To header string.
     *
     * @return the string
     */
    public String toHeader() {
        return "[" + key + "]";
    }

    /**
     * Check perm boolean.
     *
     * @param pl
     *         the pl
     *
     * @return the boolean
     */
    public boolean checkPerm(Player pl) {
        return pl.hasPermission(perm.getPerm());
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
     * Is trade boolean.
     *
     * @return the boolean
     */
    public boolean isTrade() {
        return this.equals(TRADE);
    }

    /**
     * Is i trade boolean.
     *
     * @return the boolean
     */
    public boolean isITrade() {
        return this.equals(ITRADE);
    }

    /**
     * Is bi trade boolean.
     *
     * @return the boolean
     */
    public boolean isBiTrade() {
        return this.equals(BITRADE);
    }
}