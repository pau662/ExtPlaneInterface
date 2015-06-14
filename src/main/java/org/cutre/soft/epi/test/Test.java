package org.cutre.soft.epi.test;

import org.cutre.soft.ExtPlaneInterface;
import org.cutre.soft.epi.command.ExtPlaneCommand;
import org.cutre.soft.epi.command.KeyCommand;
import org.cutre.soft.epi.data.DataRef;
import org.cutre.soft.epi.util.Observer;

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
public class Test {

//  sub sim/cockpit2/engine/indicators/engine_speed_rpm
//  sub sim/flightmodel/position/indicated_airspeed
//  sub sim/flightmodel/position/vh_ind_fpm
//  sub sim/cockpit2/gauges/indicators/altitude_ft_pilot
//  sub sim/cockpit2/gauges/indicators/altitude_ft_copilot
//  sub sim/aircraft/view/acf_tailnum
//  sub sim/aircraft/view/acf_author
//  sub sim/aircraft/view/acf_descrip
//  sub sim/aircraft/view/acf_notes
    
    public static void main(String[] args) throws Exception {

        String dataRefName = "sim/cockpit2/engine/indicators/engine_speed_rpm";
        
        String altimeterSettingDataRefName = "sim/cockpit/misc/barometer_setting";
        
        String engintThrottleDataRefName = "sim/flightmodel/engine/ENGN_thro_use";
        
        String com1DataRefName = "sim/cockpit/radios/com1_freq_hz";
        
        ExtPlaneInterface iface = new ExtPlaneInterface("localhost", 51000);
        
        System.out.println("Starting!");
        iface.start();
        
        System.out.println("Setting ExtPlane update interval to 0.001.");
        ExtPlaneCommand epc = new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.UPDATE_INTERVAL, "0.001");
        iface.sendMessage(epc);
        
        System.out.println("Including dataRef " + dataRefName + " with an accuracy of 100.0.");
        iface.includeDataRef(dataRefName,100.0F);
        
        Observer<DataRef> s = new Observer<DataRef>() {

            public void update(DataRef object) {
                System.out.println(object);
            }
        };
        
        System.out.println("Observing " + dataRefName);
        iface.observeDataRef(dataRefName, s);
        
        Thread.sleep(5000);
        
        System.out.println("Including data ref " + dataRefName + " with an accuracy of 1000.0.");
        iface.includeDataRef(dataRefName,1000.0F);
        
        Thread.sleep(5000);
        
        System.out.println("Getting DataRef " + dataRefName);
        System.out.println(iface.getDataRef(dataRefName));
        
        System.out.println("Getting data ref " + dataRefName + " VALUE.");
        System.out.println(iface.getDataRefValue(dataRefName));
        
        System.out.println("Getting data ref " + dataRefName + " TYPE.");
        System.out.println(iface.getDataRefType(dataRefName));
        
        System.out.println("Excluding data ref " + dataRefName + ".");
        iface.excludeDataRef(dataRefName);
        
        Thread.sleep(5000);
        
        System.out.println("Including data ref " + dataRefName + " default accuracy.");
        iface.includeDataRef(dataRefName);
        
        System.out.println("Updating ExtPlane update interval to 0.5.");
        epc = new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.UPDATE_INTERVAL, "0.5");
        iface.sendMessage(epc);
        
        System.out.println("Including data ref " + altimeterSettingDataRefName + " default accuracy.");
        iface.includeDataRef(altimeterSettingDataRefName);
        
        System.out.println("Including data ref " + engintThrottleDataRefName + " with an accuracy of 0.01.");
        iface.includeDataRef(engintThrottleDataRefName, 0.01F);
        
        System.out.println("Observing " + engintThrottleDataRefName);
        iface.observeDataRef(engintThrottleDataRefName, s);
        
        Thread.sleep(1000);

        System.out.println("Excluding data ref " + dataRefName + ".");
        iface.excludeDataRef(dataRefName);
        
        System.out.println("Setting " + altimeterSettingDataRefName + " value to 30.0");
        iface.setDataRefValue(altimeterSettingDataRefName, "30.0");
        
        System.out.println("Setting ON oil pressure low warning, " + engintThrottleDataRefName);
        iface.setDataRefValue(engintThrottleDataRefName, new String[] {"1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0"});
        
        Thread.sleep(2000);
        
        System.out.println("Setting " + altimeterSettingDataRefName + " value to 29.92");
        iface.setDataRefValue(altimeterSettingDataRefName, "29.92");
        
        System.out.println("Setting OFF oil pressure low warning, " + engintThrottleDataRefName);
        iface.setDataRefValue(engintThrottleDataRefName, new String[] {"0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0"});
        
        iface.excludeDataRef(altimeterSettingDataRefName);
        iface.excludeDataRef(engintThrottleDataRefName);
        
        iface.includeDataRef(com1DataRefName);
        Thread.sleep(500);
        
        System.out.println("Setting " + com1DataRefName + " value to 121.5");
        iface.setDataRefValue(com1DataRefName, "12150");
        
        iface.excludeDataRef(com1DataRefName);
        
        System.out.println("Pausing the sim");
        KeyCommand kc = new KeyCommand("0");
        iface.sendMessage(kc);
        
        Thread.sleep(5000);
        
        System.out.println("Unpausing the sim");
        iface.sendMessage(kc);
        
        System.out.println("Stopping!");
        Thread.sleep(500);

        iface.stop();
        
    }
}
