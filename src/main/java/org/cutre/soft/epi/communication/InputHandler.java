package org.cutre.soft.epi.communication;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.cutre.soft.epi.data.DataRef;
import org.cutre.soft.epi.data.DataRefFactory;
import org.cutre.soft.epi.data.DataRefRepository;
import org.cutre.soft.epi.util.ObservableAware;

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
public class InputHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(InputHandler.class);
    private static final String DATA_REF_PATTERN = "^u(i|f|d|ia|fa|b)\\s.+";
    private static final String VERSION_PATTERN = "^EXTPLANE\\s.*";
    
    private final String data;
    private final DataRefRepository repository;
    
    private DataRef dataRef;
    

    InputHandler(DataRefRepository repository, String data) {
        this.data = data;
        this.repository = repository;
    }

    public void run() {

        if(isDataRef(data)) {
            
            DataRefFactory drf = new DataRefFactory(data);
            
            dataRef = repository.getDataRef(drf.getDataRefName());
            dataRef = drf.getInstance(dataRef);
            repository.setDataRef(dataRef);
            
            ObservableAware.getInstance().update(dataRef);
            
            LOGGER.debug(dataRef);
            
        } else if(isVersion(data)) {
            LOGGER.info("Version " + data.replace("EXTPLANE ", ""));
        }
        
    }
    
    private boolean isDataRef(String data) {
        return Pattern.matches(InputHandler.DATA_REF_PATTERN, data);
    }
    
    private boolean isVersion(String data) {
        return Pattern.matches(InputHandler.VERSION_PATTERN, data);
    }
}
