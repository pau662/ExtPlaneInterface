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
public class KeyCommand implements CommandMessage {
    
    private static final String KEY_PREFIX = "key";
    
    private String keyId;
    
    public KeyCommand(String keyId) {
        this.keyId = keyId;
    }
    
    private String buildCommand() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(KeyCommand.KEY_PREFIX)
            .append(" ")
            .append(this.keyId);
        
        return sb.toString();
    }

    public String getCommand() {
        return this.buildCommand();
    }

}
