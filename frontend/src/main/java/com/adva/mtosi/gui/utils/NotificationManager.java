/*
 * Copyright (c) 2002-2007 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package com.adva.mtosi.gui.utils;

import java.util.List;

import javax.swing.ListModel;

import com.adva.mtosi.gui.beans.Notification;
import com.jgoodies.common.collect.ArrayListModel;

/**
 * Holds a List of Albums and provides operations to add, delete and
 * change a Notification. Such a manager is often part of the domain layer.<p>
 * 
 * This manager holds the Albums in an ArrayListModel, so we can
 * operate on a List and can expose it as a ListModel.
 * As an alternative, a higher-level presentation model, such as
 * the NotificationManagerModel could turn the List into a ListModel.
 * In the latter case, you would then need to fire the required
 * ListDataEvents.<p>
 * 
 * The NotificationManagerModel turns the List of Albums and the operations
 * into a form that can be used in a user interface to display,
 * select, and edit Albums.<p>
 * 
 * TODO: Demonstrate how to sort the albums.  
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.7 $
 * 
 */

public final class NotificationManager {
    
    /**
     * Holds the List of Albums. Albums are added and removed from this List.
     * The ObservableList implements ListModel, and so, we can directly
     * use this List for the UI and can observe changes.<p>
     * 
     * In a real world application this List may be kept 
     * in synch with a database.
     */
    private final ArrayListModel managedAlbums;
    

    // Instance Creation ******************************************************
    
    /**
     * Constructs a NotificationManager for the given list of Albums.
     * 
     * @param albums   the list of Albums to manage
     */
    public NotificationManager(List albums) {
        this.managedAlbums = new ArrayListModel(albums);
    }
    
    
    // Exposing the ListModel of Albums ****************************************
    
    public ListModel getManagedAlbums() {
        return managedAlbums;
    }
    
    
    // Managing Albums *********************************************************
    
    /**
     * Creates and return a new Notification.
     * 
     * @return the new Notification
     */
    public Notification createItem() {
        return new Notification();
    }

    public Notification createItem(String severity, String category, Boolean security, String impairment ) {
        return new Notification(severity,category,security,impairment);
    }

    public Notification createItem(String md, String ne,String aid, String text,String severity, String category, Boolean security, String impairment ) {
        return new Notification(md, ne, aid, text,severity,category,security,impairment);
    }
    
    
    /**
     * Adds the given Notification to the List of managed Albums
     * and notifies observers of the managed Albums ListModel
     * about the change.
     * 
     * @param albumToAdd   the Notification to add
     */
    public void addItem(Notification albumToAdd) {
        managedAlbums.add(albumToAdd);
    }
    
    
    /**
     * Removes the given Notification from the List of managed Albums
     * and notifies observers of the managed Albums ListModel
     * about the change.
     * 
     * @param albumToRemove    the Notification to remove
     */
    public void removeItem(Notification albumToRemove) {
        managedAlbums.remove(albumToRemove);
    }
    
    
}
