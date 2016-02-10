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

package com.adva.mtosi.gui.beans;

import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.Model;

/**
 * Describes a musical Notification and provides bound bean properties.
 * This class is used throughout the different tutorial examples.<p>
 * 
 * This class has not been marked as final although it is not intended
 * to be subclassed. However some persistency frameworks (like Hibernate)
 * can optimize the data transfer and locking for extensible classes only.
 *  
 * @author  Karsten Lentzsch
 * @version $Revision: 1.5 $
 */
public class Notification extends Model {

    public static final List<Notification> NOTIFICATIONS = new ArrayList();
//    {{
//        add(new Notification("","",false,""));
//    }};


    // Names of the Bound Bean Properties *************************************

    public static final String PROPERTYNAME_NE_NAME = "ne";
    public static final String PROPERTYNAME_MD_NAME = "md";
    public static final String PROPERTYNAME_AID = "aid";
    public static final String PROPERTYNAME_DESCRIPTION = "description";
    public static final String PROPERTYNAME_DATE = "date";
    public static final String PROPERTYNAME_CATEGORY = "category";
    public static final String PROPERTYNAME_SECURITY = "security";
    public static final String PROPERTYNAME_IMPAIRMENT = "impairment";
    public static final String PROPERTYNAME_SEVERITY = "severity";
    
    
    // Instance Fields ********************************************************

    private String ne;
    private String md;
    private String aid;
    private String description;
    private String date;

    /**
     * This Notification's severity as associated with its ISBN,
     * for example "Symphony No. 5".
     */
    private String severity;
    
    /**
     * Holds this Notification's category, for example: "Albert Ayler",
     * or "Berliner Philharmoniker".
     */
    private String category;

    /**
     * Describes if this Notification is security music; in this case
     * it has a impairment.
     */
    private boolean security;
    
    /**
     * Holds the impairment of this Notification's music, for example "Beethoven".
     * Available if and only if this is a security notification.
     */
    private String impairment;
    

    // Instance Creation ******************************************************
    
    /**
     * Constructs an empty Notification: empty severity and category, not security
     * and no impairment set.
     */
    public Notification() {
        this("", "");

    }

    public Notification(String severity, String category, Boolean security, String impairment) {
        setSeverity(severity);
        setCategory(category);
        setSecurity(security);
        setImpairment(impairment);
    }

    public Notification(String md, String ne, String aid, String desc, String date, String severity, String category, Boolean security, String impairment) {
        setMd(md);
        setNe(ne);
        setAid(aid);
        setDescription(desc);
        setDate(date);
        setSeverity(severity);
        setCategory(category);
        setSecurity(security);
        setImpairment(impairment);
    }
    
    
    private Notification(String severity, String category) {
        setSeverity(severity);
        setCategory(category);
        setSecurity(false);
        setImpairment(null);
    }

    // Accessors **************************************************************
    
    
    /**
     * Returns this notification's severity, for example "A Love Supreme",
     * or "Symphony No. 5".
     * 
     * @return this notification's severity.
     */
    public String getSeverity() {
        return severity;
    }
    

    /**
     * Returns this notification's category, for example "Albert Ayler"
     * or "Berliner Philharmoniker". 
     * 
     * @return this notification's category.
     */
    public String getCategory() {
        return category;
    }
    
    
    /**
     * Answers whether this is a security notification or not.
     * 
     * @return true if this notification is security, false if not
     */
    public boolean isSecurity() {
        return security;
    }
    
    
    /**
     * Returns this notification's impairment - if any, for example "Richard Wagner".
     * A impairment is available if and only if this is a security notification.
     * 
     * @return the impairment of this notification's music.
     * 
     * @see #isSecurity
     */
    public String getImpairment() {
        return impairment;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets this notification's severity and notifies observers
     * if the severity changed.
     * 
     * @param severity   The severity to set.
     */
    public void setSeverity(String severity) {
        Object oldValue = getSeverity();
        this.severity = severity;
        firePropertyChange(PROPERTYNAME_SEVERITY, oldValue, severity);
    }
    
    
    /**
     * Sets a new category and notifies observers if the category changed.
     * 
     * @param category  The category to set.
     */
    public void setCategory(String category) {
        String oldValue = getCategory();
        this.category = category;
        firePropertyChange(PROPERTYNAME_CATEGORY, oldValue, category);
    }
    

    /**
     * Sets this notification's security property and notifies observers
     * about changes. If not security the impairment is set to <code>null</code>.
     * 
     * @param security   true to indicate that this notification is security
     */
    public void setSecurity(boolean security) {
        boolean oldValue = isSecurity();
        this.security = security;
        firePropertyChange(PROPERTYNAME_SECURITY, oldValue, security);
        if (!security) {
            setImpairment(null);
        }
    }
    

    /**
     * Sets this notification's impairment and notifies observers if it has changed.
     * A impairment shall be set only if this is a security notification.
     * 
     * @param impairment   The impairment to set.
     * 
     * @see #isSecurity
     */
    public void setImpairment(String impairment) {
        Object oldValue = getImpairment();
        this.impairment = impairment;
        firePropertyChange(PROPERTYNAME_IMPAIRMENT, oldValue, impairment);
    }
    
    
    // Misc *******************************************************************
    
    /**
     * Returns a string representation of this notification
     * that contains the property values in a single text line.
     * 
     * @return a string representation of this notification
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("Notification");
        buffer.append(" [severity=");
        buffer.append(getSeverity());
        buffer.append("; category=");
        buffer.append(getCategory());
        buffer.append("; security=");
        buffer.append(isSecurity());
        buffer.append("; impairment=");
        buffer.append(getImpairment());
        buffer.append("]");
        return buffer.toString();
    }

}
