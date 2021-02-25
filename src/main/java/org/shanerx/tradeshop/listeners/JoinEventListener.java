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

package org.shanerx.tradeshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.shanerx.tradeshop.TradeShop;
import org.shanerx.tradeshop.enumys.Message;
import org.shanerx.tradeshop.enumys.Permissions;
import org.shanerx.tradeshop.enumys.Setting;
import org.shanerx.tradeshop.utils.BukkitVersion;
import org.shanerx.tradeshop.utils.JsonConfiguration;
import org.shanerx.tradeshop.utils.Updater;
import org.shanerx.tradeshop.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Join event listener.
 */
public class JoinEventListener extends Utils implements Listener {

    /**
     * On join.
     *
     * @param event
     *         the event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        JsonConfiguration json = new JsonConfiguration(player.getUniqueId());
        Map<String, Integer> data = json.loadPlayer();

        if (data == null) { data = new HashMap<>(); }

        if (! data.containsKey("type")) { data.put("type", 0); }

        data.put("multi", Setting.MULTI_TRADE_DEFAULT.getInt());

        json.savePlayer(data);

        if (player.hasPermission(Permissions.ADMIN.getPerm())) {
            BukkitVersion ver = new BukkitVersion();
            if (TradeShop.getInstance().getUpdater().compareVersions((short) ver.getMajor(), (short) ver.getMinor(), (short) ver.getPatch()).equals(Updater.RelationalStatus.BEHIND)) { player.sendMessage(Message.PLUGIN_BEHIND.getPrefixed()); }
        }
    }
}

