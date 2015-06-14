package org.cutre.soft.epi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cutre.soft.epi.data.DataRef;

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
public class ObservableAware {
    
    private static ObservableAware aware = new ObservableAware();
    private Map<String, List<Observer<DataRef>>> observers = new HashMap<String, List<Observer<DataRef>>>();
    
    private ObservableAware() {
        
    }
    
    public static ObservableAware getInstance() {
        return ObservableAware.aware;
    }
    
    public void addObserver(String dataRef, Observer<DataRef> observer) {
        List<Observer<DataRef>> concreteObservers = this.observers.get(dataRef);
        if(concreteObservers==null) {
            this.observers.put(dataRef, new ArrayList<Observer<DataRef>>());
        }
        this.observers.get(dataRef).add(observer);
    }
    
    public void removeObserver(String dataRef, Observer<DataRef> observer) {
        List<Observer<DataRef>> concreteObservers = this.observers.get(dataRef);
        if(concreteObservers!=null) {
            concreteObservers.remove(observer);
        }
    }
    
    public void update(DataRef dataRef) {
        List<Observer<DataRef>> concreteObservers = this.observers.get(dataRef.getName());
        if(concreteObservers!=null && !concreteObservers.isEmpty()) {
            for(Observer<DataRef> obs : concreteObservers) {
                obs.update(dataRef);
            }
        }
    }
}
