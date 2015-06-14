package org.cutre.soft.epi.command;

import org.cutre.soft.epi.util.ArrayUtils;

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
public class DataRefCommand implements CommandMessage {

    public static enum DATAREF_ACTION {
        SET,
        SUBSCRIBE,
        UNSUBSCRIBE
    }
    
    private DATAREF_ACTION action;
    private Float accuracy;
    private String dataRefName;
    private String[] value;
    
    public DataRefCommand(DATAREF_ACTION action, String dataRefName, String... value) {
        this.action = action;
        this.dataRefName = dataRefName;
        this.value = value;
    }
    
    private String buildCommand() {
        StringBuilder sb = new StringBuilder();
        ArrayUtils arrayUtils = null;
        
        sb.append(this.getActionCommand());
        sb.append(this.dataRefName);
        
        if(DATAREF_ACTION.SUBSCRIBE.equals(this.action)
                && this.accuracy!=null) {
            
            sb.append(" ")
                .append(accuracy);
            
        } else if(DATAREF_ACTION.SET.equals(this.action)) {
            
            sb.append(" ");
            
            if(this.value.length == 1) {
                sb.append(this.value[0]);
            } else {
                arrayUtils = new ArrayUtils();
                sb.append(arrayUtils.arrayToString(this.value));
            }
            
        }
        return sb.toString();
    }
    
    private String getActionCommand() {
        switch (this.action) {
            case SET:
                return "set ";
                
            case UNSUBSCRIBE:
                return "unsub ";
                
            case SUBSCRIBE:
            default:
                return "sub ";
        }
        
    }
    
    public String getCommand() {
        System.out.println(this.buildCommand());
        return this.buildCommand();
    }
    
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

}
