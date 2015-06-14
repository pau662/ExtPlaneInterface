package org.cutre.soft;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.cutre.soft.epi.command.CommandMessage;
import org.cutre.soft.epi.command.DataRefCommand;
import org.cutre.soft.epi.command.ExtPlaneCommand;
import org.cutre.soft.epi.communication.ExtPlaneTCPReceiver;
import org.cutre.soft.epi.communication.ExtPlaneTCPSender;
import org.cutre.soft.epi.data.DataRef;
import org.cutre.soft.epi.data.DataRefRepository;
import org.cutre.soft.epi.data.DataRefRepositoryImpl;
import org.cutre.soft.epi.data.MessageRepository;
import org.cutre.soft.epi.data.MessageRepositoryImpl;
import org.cutre.soft.epi.util.Constants.DataType;
import org.cutre.soft.epi.util.ObservableAware;
import org.cutre.soft.epi.util.Observer;
import org.cutre.soft.exception.ConnectionException;

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
public class ExtPlaneInterface {
    
    private static final Logger LOGGER = Logger.getLogger(ExtPlaneInterface.class);
    
    private Socket socket;
    
    private DataRefRepository dataRefrepository;
    private MessageRepository messageRepository;
    
    private ExtPlaneTCPReceiver receive = null;
    private ExtPlaneTCPSender sender = null;
    
    private String server;
    private int port;
    private int poolSize = 2;
    
    
    public ExtPlaneInterface(String server, int port) {
        
        this.server = server;
        this.port = port;
        
        this.initDataRefRepository();
        this.initMessageRepository();
        
    }
    
    public void excludeDataRef(String dataRefName) {
        this.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.UNSUBSCRIBE,dataRefName));
    }
    
    public DataRef getDataRef(String dataRef) {
        return this.dataRefrepository.getDataRef(dataRef);
    }
    
    public DataType getDataRefType(String dataRefName) {
        DataRef dr = this.dataRefrepository.getDataRef(dataRefName);
        
        if(dr!=null) 
            return dr.getDataType();
        
        return null;
    }
    
    public String[] getDataRefValue(String dataRefName) {
        DataRef dr = this.dataRefrepository.getDataRef(dataRefName);
        
        if(dr!=null) 
            return dr.getValue();
        
        return null;
    }
    
    public void includeDataRef(String dataRefName) {
        this.includeDataRef(dataRefName, null);
    }
    
    public void includeDataRef(String dataRefName, Float accuracy) {
        DataRefCommand drc = new DataRefCommand(DataRefCommand.DATAREF_ACTION.SUBSCRIBE,dataRefName);
        
        if(accuracy!=null)
            drc.setAccuracy(accuracy);
        
        this.sendMessage(drc);
    }
    
    public void setDataRefValue(String dataRefName, String... value) {
        this.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.SET, dataRefName, value));
    }
    
    public void sendMessage(CommandMessage message) {
        this.messageRepository.sendMessage(message);
    }
    
    public void setExtPlaneUpdateInterval(String interval) {
        this.sendMessage(new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.UPDATE_INTERVAL, interval));
    }
    
    public void start() throws Exception {
        try {
            this.connect();
            this.startSending();
            this.startReceiving();
        } catch(Exception e) {
            LOGGER.error("Error starting services.", e);
            this.stopReceiving();
            this.stopSending();
            throw e;
        }
    }
    
    public void stop() {
        this.stopReceiving();
        this.stopSending();
    }
    
    public void observeDataRef(String dataRefName, Observer<DataRef> observer) {
        ObservableAware.getInstance().addObserver(dataRefName, observer);
    }
    
    public void unObserveDataRef(String dataRefName, Observer<DataRef> observer) {
        ObservableAware.getInstance().removeObserver(dataRefName, observer);
    }
    
    private void connect() throws ConnectionException {
        
        try {
            socket = new Socket(server, port);
        } catch (UnknownHostException e) {
            LOGGER.error("[ExtPlaneInterface::connect] Error connecting host " + server, e);
            throw new ConnectionException("Error connecting host -> " + server, e);
        } catch (IOException e) {
            LOGGER.error("[ExtPlaneInterface::connect] Error connecting host " + server, e);
            throw new ConnectionException("Error connecting host -> " + server, e);
        }
    }
    
    private void initDataRefRepository() {
        this.dataRefrepository = new DataRefRepositoryImpl();
    }
    
    private void initMessageRepository() {
        this.messageRepository = new MessageRepositoryImpl();
    }
    
    private void startReceiving() {
        receive = new ExtPlaneTCPReceiver(socket, dataRefrepository, poolSize);
        receive.start();
    }
    
    private void startSending() {
        sender = new ExtPlaneTCPSender(socket, messageRepository);
        sender.start();
    }
    
    private void stopReceiving() {
        if(this.receive!=null) 
            this.receive.setKeep_running(false);
        
        this.receive = null;
    }
    
    private void stopSending() {
        if(this.sender!=null) 
            this.sender.setKeep_running(false);
        
        this.sender = null;
    }

}
