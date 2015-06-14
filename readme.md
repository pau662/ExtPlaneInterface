# ExtPlane-Plugin Java Interface #
----

A java library to communicate to [ExtPlane-Plugin] so it can be used to write java software that make use of [X-Plane] DataRefs.

## License ##
----

**GNU GPLv3**

Copyright (C) 2015  Pau G.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.


## Build ##
----

To build the jar you will need Maven (https://maven.apache.org/).

Download the project, move to the pom.xml folder and run "mvn package".

## How to use ##
----

### Requisites ###

   - Install [ExtPlane-Plugin] in your [X-Plane].
   - Before start using the interface you have to run your [X-Plane].

### Starting the service ###

The main entrance to the interface is at org.cutre.soft.ExtPlaneInterface.
Instantiate the interface passing the XPlane IP and the port 51000.

```java
ExtPlaneInterface iface = new ExtPlaneInterface("localhost", 51000);
iface.start();
```
Calling start() will connect to [ExtPlane-Plugin] and will get ready to receive DataRefs.

### Subscribing to DataRefs ####

[ExtPlane-Plugin] requires to subscribe dataRefs to receive their data. So, you have to say to [ExtPlane-Plugin] what dataRefs have to send.

```java
// Include dataRefName with standard accuracy
iface.includeDataRef(dataRefName);

// Include dataRefName with custom accuracy
iface.includeDataRef(dataRefName,1000.0F);
```

With accuracy you can decide how much the dataref's value can change before a update is sent. Set it to as large value as possible to maximize frame rate and minimize network traffic.

To stop getting a dataRef just call,

```java
iface.excludeDataRef(dataRefName);
```

### Getting DataRefs ###

Once connected to [ExtPlane-Plugin] and having subscribed to one or more dataRefs, it's time to get their values.

To get dataRefs you may,

   - Get DataRef value by calling,         
        
   ```java
   iface.getDataRefValue(dataRefName)
   ```
  
   This will return a String array with the dataRef value. 
   To get dataRef data type's, call     
  
   ```java
   iface.getDataRefType(dataRefName)
   ```
  
   This will return a DataRefType enum with the right data type,
  
   ```java    
   public static enum DataType {
       ArrayFloat,
       ArrayInt,
       Base64,
       Double,
       Float,
       Int
   }
   ```
   - Get dataRef object by calling,
  
   ```java    
   iface.getDataRef(dataRefName)
   ```

   This will return a DataRef object.
        
   - Get DataRef object implementing an org.cutre.soft.util.Observer<DataRef> object,
   
   ```java    
   Observer<DataRef> s = new Observer<DataRef>() {
      public void update(DataRef object) {
         System.out.println(object);
      }
   };
   
   iface.observeDataRef(dataRefName, s);
   ```
   Every time we get a new dataRefName value from the sim we will call the update method. To stop receiving updates just call,
   
   ```java    
   iface.unObserveDataRef(dataRefName, s);
   ```

### Setting DataRefs value ###

It is possible to set a new value to a DataRef calling,

```java
iface.setDataRefValue(altimeterSettingDataRefName, "30.0");
iface.setDataRefValue(engintThrottleDataRefName, new String[] {"1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0", "1.0"});
```

### Sending messages ###

Setting dataRef values, pressing a button or a key are messages send to [ExtPlane-Plugin].

   - `org.cutre.soft.epi.command.ButtonCommand`: allow pushing/releasing a sim button,
   
   ```java
   // Push a button
   iface.sendMessage(new ButtonCommand(ButtonCommand.ButtonAction.PRESS, buttonId));
   
   // Release a button
   iface.sendMessage(new ButtonCommand(ButtonCommand.ButtonAction.RELEASE, buttonId));
   ```
   - `org.cutre.soft.epi.command.DataRefCommand`: allows sending a DataRef message to the plug-in:
   
   ```java
   // Subscribe to a dataRef
   iface.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.SUBSCRIBE,dataRefName));
   
   // Sets a dataRef value
   iface.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.SET, dataRefName, value)) 

   // Unsubscribe a dataRef
   iface.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.UNSUBSCRIBE,dataRefName));
   ```
   - `org.cutre.soft.epi.command.ExtPlaneCommand`: send an [ExtPlane-Plugin] config command,
   
   ```java
   // Press a key
   iface.sendMessage(new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.UPDATE_INTERVAL, interval));
   ```
   
   - `org.cutre.soft.epi.command.KeyCommand`: send a key press to the a sim,
   
   ```java
   // Press a key
   iface.sendMessage(new KeyCommand(keyId));
   ```
List of key and button id's can be found at: http://www.xsquawkbox.net/xpsdk/mediawiki/XPLMUtilities Note that the key and button id's are numbers, not names. X-Plane does not provide a way to lookup keys or buttons by name.
    
[ExtPlane-Plugin]:https://github.com/vranki/ExtPlane/blob/master/README.md
[X-Plane]:http://www.x-plane.com/      
    
    