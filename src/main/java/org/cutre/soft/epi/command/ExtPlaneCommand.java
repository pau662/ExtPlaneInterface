package org.cutre.soft.epi.command;

/**
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
 */
public class ExtPlaneCommand implements CommandMessage {
    
    private static final String COMMAND_PREFIX = "extplane-set";
    
    private static final String UPDATE_INTERVAL_SETTING = "update_interval";
    
    public static enum EXTPLANE_SETTING {
        UPDATE_INTERVAL
    }
    
    private EXTPLANE_SETTING setting;
    private String value;
    
    public ExtPlaneCommand(EXTPLANE_SETTING extPlaneSetting, String value) {
        this.value = value;
        this.setting = extPlaneSetting;
    }
    
    private String buildCommand() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(ExtPlaneCommand.COMMAND_PREFIX)
            .append(" ")
            .append(this.getActionCommand())
            .append(" ")
            .append(this.value);
        
        return sb.toString();
    }
    
    private String getActionCommand() {
        switch (this.setting) {
            case UPDATE_INTERVAL:
            default:
                return ExtPlaneCommand.UPDATE_INTERVAL_SETTING;
        }
    }

    public String getCommand() {
        return this.buildCommand();
    }

}
