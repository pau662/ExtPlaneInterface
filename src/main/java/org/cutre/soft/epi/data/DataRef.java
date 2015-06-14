package org.cutre.soft.epi.data;

import org.cutre.soft.epi.util.ArrayUtils;
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
public class DataRef {
    
    private String name;
    private DataType dataType;
    private String rawData;
    private String[] value;
    
    public DataRef() {
        
    }
    
    public DataRef(String name, DataType dataType, String[] value) {
        this.setName(name);
        this.setDataType(dataType);
        this.setValue(value);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    private void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    /**
     * 
     * @return
     */
    public String getRawData() {
        return rawData;
    }
    
    /**
     * 
     * @param rawData
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    /**
     * @return the value
     */
    public String[] getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String[] value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ArrayUtils arrayUtils = new ArrayUtils();
        return "DataRef [name=" + name + ", dataType=" + dataType + ", value="
                + arrayUtils.arrayToString(value) + "]";
    }

}
