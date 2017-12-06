/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.Filter;
import com.jme3.network.Message;

/**
 *
 * @author mrowlie
 */
public class MessageFilterPair {

        public Filter filter;
        public Message message;
        
        public MessageFilterPair(Message message, Filter filter) {
            this.filter = filter;
            this.message = message;
            
        }
    }
