package org.cutre.soft.epi.util;

import org.cutre.soft.epi.util.Constants.DataType;

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
public class ArrayUtils {

    public String arrayToString(String[] array) {
        
        StringBuilder sb = new StringBuilder("[");
        boolean isFirst = true;
        
        for(String element : array) {
            
            if(isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            sb.append(element);
        }
        
        sb.append("]");
        
        return sb.toString();
        
    }
    
    public String[] toObject(String data, DataType dataType) {

        String[] obj = null;

        obj = toStringArray(data.trim());
        
        return obj;

    }
    
    

    @SuppressWarnings("unused")
    private Integer[] toIntArray(String data) {

        String[] stringArray = this.toStringArray(data);
        Integer[] intArray = new Integer[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i].trim());
        }

        return intArray;
    }

    @SuppressWarnings("unused")
    private float[] toFloatArray(String data) {

        String[] stringArray = this.toStringArray(data);
        float[] floatArray = new float[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            floatArray[i] = Float.parseFloat(stringArray[i].trim());
        }

        return floatArray;
    }

    private String[] toStringArray(String data) {
        return data.trim().replaceAll("\\[", "").replaceAll("\\]", "").split(",");
    }

}
