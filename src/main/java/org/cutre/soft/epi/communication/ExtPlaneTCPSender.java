package org.cutre.soft.epi.communication;

import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.cutre.soft.epi.command.CommandMessage;
import org.cutre.soft.epi.data.MessageRepository;

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
public class ExtPlaneTCPSender extends StoppableThread {

    private static final Logger LOGGER = Logger.getLogger(ExtPlaneTCPSender.class);
    
    private Socket socket;
    private MessageRepository repository;

    public ExtPlaneTCPSender(Socket socket, MessageRepository repository) {
        this.socket = socket;
        this.repository = repository;
        this.keep_running = true;
        Thread.currentThread().setPriority(MIN_PRIORITY);
    }

    @Override
    public void run() {

        try {
            String command = null;
            CommandMessage message = null;
            
            this.setName("ExtPlane-SenderThread");            
            LOGGER.debug("Running Thread " + this.getName());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            
            while(keep_running) {
                message = this.repository.getNextMessage();
                if (message != null) {
                    command = message.getCommand();
                    
                    LOGGER.debug("Sending message: " + command);
                    writer.println(command);
                    writer.flush();
                    
                    command = null;
                } else {
                    sleep(250);
                }
            }
            

        } catch (Exception e) {
            LOGGER.error("ERROR sending data.", e);
        } finally {
            
        }

    }

}
