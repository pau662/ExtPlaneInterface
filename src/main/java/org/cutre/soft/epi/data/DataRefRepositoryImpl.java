package org.cutre.soft.epi.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
public class DataRefRepositoryImpl implements DataRefRepository {

    private ConcurrentHashMap<String, DataRef> dataRefsMap = new ConcurrentHashMap<String, DataRef>();
    private List<DataRef> dataRefList = new ArrayList<DataRef>();
    
    
    public DataRef getDataRef(String dataRef) {
        return this.dataRefsMap.get(dataRef);
    }

    public List<DataRef> getDataRefs() {
        return this.dataRefList;
    }
    
    public void removeDataRef(DataRef dataRef) {
        if(dataRef!=null && dataRef.getName()!=null) {
            this.removeDataRef(dataRef.getName());
        }
        
    }

    public void removeDataRef(String dataRef) {
        DataRef dr = this.getDataRef(dataRef);
        if(dr!=null) {
            this.dataRefsMap.remove(dr);
            this.dataRefList.remove(dr);
        }
    }

    public void setDataRef(DataRef dataRef) {
        if(!this.dataRefsMap.containsKey(dataRef.getName())) {
            this.dataRefList.add(dataRef);            
        }
        this.dataRefsMap.put(dataRef.getName(), dataRef);
    }

    
}
