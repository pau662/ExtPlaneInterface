package org.cutre.soft.epi.command;

/**
 * Class that represents a Command of type BUTTON or RELEASE.
 * 
 * Copyright (C) 2015  Pau G.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Pau G.
 * 
 */
public class ButtonCommand implements CommandMessage {
    
    private static final String BUTTON_PREFIX = "button";
    
    private static final String RELEASE_PREFIX = "release";
    
    public static enum ButtonAction {
        PRESS,
        RELEASE
    }
    
    private ButtonAction action;
    private String buttonId;
    
    public ButtonCommand(ButtonAction action, String buttonId) {
        this.buttonId = buttonId;
        this.action = action;
    }
    
    private String buildCommand() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.getActionCommand())
            .append(" ")
            .append(this.buttonId);
        
        return sb.toString();
    }
    
    private String getActionCommand() {
        switch (this.action) {
            case RELEASE:
                return ButtonCommand.RELEASE_PREFIX;
            case PRESS:
            default:
                return ButtonCommand.BUTTON_PREFIX;
        }
    }

    public String getCommand() {
        return this.buildCommand();
    }

}
