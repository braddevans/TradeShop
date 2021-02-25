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

package org.shanerx.tradeshop.commands;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.shanerx.tradeshop.TradeShop;
import org.shanerx.tradeshop.enumys.Commands;
import org.shanerx.tradeshop.enumys.Setting;
import org.shanerx.tradeshop.enumys.ShopType;
import org.shanerx.tradeshop.objects.Shop;
import org.shanerx.tradeshop.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Command tab completer.
 */
public class CommandTabCompleter extends Utils {

    private final CommandPass command;
    private Player pSender;

    /**
     * Instantiates a new Command tab completer.
     *
     * @param command
     *         the command
     */
    public CommandTabCompleter(CommandPass command) {
        this.command = command;

        if (command.getSender() instanceof Player) {
            pSender = (Player) command.getSender();
        }
    }

    /**
     * Help list.
     *
     * @return the list
     */
    public List<String> help() {
        if (command.argsSize() == 2) {
            List<String> subCmds = new ArrayList<>();
            for (Commands cmds : Commands.values()) {
                if (cmds.isPartialName(command.getArgAt(1))) { subCmds.add(cmds.getFirstName()); }
            }

            return subCmds;
        }

        return new ArrayList<>();
    }

    /**
     * Add set list.
     *
     * @return the list
     */
    public List<String> addSet() {
        if (command.argsSize() == 2) {
            return Arrays.asList("1", "2", "4", "8", "16", "32", "64", "80", "96", "128");
        }
        else if (command.argsSize() == 3) {
            return partialGameMats(command.getArgAt(2));
        }
        return new ArrayList<>();
    }

    /**
     * Fill server player list.
     *
     * @return the list
     */
    public List<String> fillServerPlayer() {
        if (command.argsSize() == 2) {
            return null;
        }

        return new ArrayList<>();
    }

    /**
     * Fill shop player list.
     *
     * @return the list
     */
    public List<String> fillShopPlayer() {
        if (command.argsSize() == 2) {
            Block b = pSender.getTargetBlock(null, Setting.MAX_EDIT_DISTANCE.getInt());
            Sign s;

            if (TradeShop.getInstance().getListManager().isInventory(b)) {
                s = findShopSign(b);
            }
            else if (ShopType.isShop(b)) {
                s = (Sign) b.getState();
            }
            else {
                return new ArrayList<>();
            }
            Shop shop = Shop.loadShop(s);

            return shop.getUserNames();
        }

        return new ArrayList<>();
    }

    private List<String> partialGameMats(String request) {
        List<String> toReturn = new ArrayList<>();
        for (String str : TradeShop.getInstance().getListManager().getGameMats()) {
            if (str.toLowerCase().contains(request.toLowerCase())) {
                toReturn.add(str);
            }
        }

        return toReturn;
    }
}
