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

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * The type Command pass.
 */
public class CommandPass {

    private CommandSender sender;
    private Command cmd;
    private String label;
    private ArrayList<String> args;

    /**
     * Instantiates a new Command pass.
     *
     * @param sender
     *         the sender
     * @param cmd
     *         the cmd
     * @param label
     *         the label
     * @param args
     *         the args
     */
    public CommandPass(CommandSender sender, Command cmd, String label, String[] args) {
        this.sender = sender;
        this.cmd = cmd;
        this.label = label;
        this.args = Lists.newArrayList(args);
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets cmd.
     *
     * @return the cmd
     */
    public Command getCmd() {
        return cmd;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Args size int.
     *
     * @return the int
     */
    public int argsSize() {
        return args.size();
    }

    /**
     * Has arg at boolean.
     *
     * @param index
     *         the index
     *
     * @return the boolean
     */
    public boolean hasArgAt(int index) {
        return index < argsSize();
    }

    /**
     * Gets arg at.
     *
     * @param index
     *         the index
     *
     * @return the arg at
     */
    public String getArgAt(int index) {
        if (hasArgAt(index)) {
            return args.get(index);
        }
        else {
            return null;
        }
    }

    /**
     * Gets args.
     *
     * @return the args
     */
    public ArrayList<String> getArgs() {
        return args;
    }

    /**
     * Has args boolean.
     *
     * @return the boolean
     */
    public boolean hasArgs() {
        return argsSize() > 0;
    }
}
