package org.cutre.soft.epi.data;

import org.cutre.soft.epi.util.Constants;
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
public class DataRefFactory {
    
    private String rawData;
    private String dName;
    private Constants.DataType dType;
    private String[] dValue;
    
    public DataRefFactory(String rawData) {
        
        this.rawData = rawData;
        String[] dataSplit = rawData.split("\\s");
        ArrayUtils sto = new ArrayUtils();
        dName = dataSplit[1];
        dType = getDataType(dataSplit[0]);
        dValue = sto.toObject(dataSplit[2], dType);
        
    }
    
    public String getDataRefName() {
        return this.dName;
    }
    
    public DataRef getNewInstance() {
        
        DataRef dr = new DataRef(dName, dType,dValue);
        return dr;
    }
    
    public DataRef getInstance(DataRef dataRef) {
        DataRef dr = this.getNewInstance();
        dr.setRawData(rawData);
        
        if(dataRef==null) {
            dataRef = dr;
        } else {
            if(dr.getName().equals(dr.getName())) {
                dataRef.setValue(dr.getValue());
                dataRef.setRawData(rawData);
            }
        }
        
        return dr;
    }
    
    private Constants.DataType getDataType(String rawDataType) {
        
        rawDataType = rawDataType.replace("u", "");
        
        if("i".equals(rawDataType))
            return DataType.Int;        
        
        if("f".equals(rawDataType))
            return DataType.Float;
        
        if("d".equals(rawDataType))
            return DataType.Double;
        
        if("ai".equals(rawDataType)) 
            return DataType.ArrayInt;
        
        if("fa".equals(rawDataType))
            return DataType.ArrayFloat;
        
        if("b".equals(rawDataType))
            return DataType.Base64;
        
        return null;
        
    }

}
