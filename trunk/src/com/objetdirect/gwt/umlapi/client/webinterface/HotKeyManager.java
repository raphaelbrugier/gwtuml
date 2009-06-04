/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;


/**
 * This class is a bit hacky but allows a GWT application to have global key listeners <br>
 * It also defines the hot keys for the drawer
 *  
 * @author http://markmail.org/message/5ej3lijr4iupnhbz#query:global%20listener%20gwt+page:1+mid:5ej3lijr4iupnhbz+state:results
 */
public final class HotKeyManager {
    private static final class WindowCloseHandlerImpl implements ClosingHandler {
	public native void onWindowClosing(ClosingEvent event) /*-{ 
	       $doc.onkeydown = null; 
	       $doc.onkeypress = null; 
	       $doc.onkeyup = null; 
	       }-*/;

	private native void init() /*-{ 
				$doc.onkeydown = function(evt) { 
					@com.objetdirect.gwt.umlapi.client.webinterface.HotKeyManager::onKeyDown(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
				} 
				$doc.onkeypress = function(evt) { 
					@com.objetdirect.gwt.umlapi.client.webinterface.HotKeyManager::onKeyPress(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
				} 
				$doc.onkeyup = function(evt) { 
					@com.objetdirect.gwt.umlapi.client.webinterface.HotKeyManager::onKeyUp(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
				} 
			}-*/;

    }

    static {
	final WindowCloseHandlerImpl closeListener = new WindowCloseHandlerImpl();
	Window.addWindowClosingHandler(closeListener);
	closeListener.init();
    }

    /**
     * This method must be called to ensure the class is statically initialized
     * It fills up the {@link HelpManager} too.
     */
    public static void forceStaticInit() {
	HelpManager.addHotkeyHelp("H", "Bring this help");
	HelpManager.addHotkeyHelp("C", "Add a new class");
	HelpManager.addHotkeyHelp("O", "Add a new object");
	HelpManager.addHotkeyHelp("N", "Add a new note");
	HelpManager.addHotkeyHelp("F", "Add a new life line");
	HelpManager.addHotkeyHelp("A", "Add a new aggregation relation");
	HelpManager.addHotkeyHelp("L", "Add a new association relation");
	HelpManager.addHotkeyHelp("K", "Add a new composition relation");
	HelpManager.addHotkeyHelp("D", "Add a new dependency relation");
	HelpManager.addHotkeyHelp("G", "Add a new generalization relation");
	HelpManager.addHotkeyHelp("R", "Add a new realization relation");
	HelpManager.addHotkeyHelp("I", "Add a new instantiation relation");
	HelpManager.addHotkeyHelp("T", "Add a new note link");
	HelpManager.addHotkeyHelp("E", "Add a new class relation");
	HelpManager.addHotkeyHelp("M", "Add a new asynchronous message");
	HelpManager.addHotkeyHelp("P", "Add a new synchronous message");
	HelpManager.addHotkeyHelp("B", "Add a new object creation message");
	HelpManager.addHotkeyHelp("J", "Add a new lost message");
	HelpManager.addHotkeyHelp("Y", "Add a new found message");
	HelpManager.addHotkeyHelp("U", "Update URL with current diagram");
	HelpManager.addHotkeyHelp("Del", "Remove selected object(s)");
	HelpManager.addHotkeyHelp("Ctrl]+[Up", "Move up selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Down", "Move down selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Left", "Move left selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Right", "Move right selected object");
		
    }

    private static boolean inputEnabled = true;
    /**
     * Getter to the current state of hotkeys
     * 
     * @return true if it is enabled false otherwise
     */
    public static boolean isInputEnabled() {
	return inputEnabled;
    }

    /**
     * Set the hotkey manager state. This is used to disable hotkeys while editing for instance
     * 
     * @param isEnabled The status : True to activate False to disable 
     */
    public static void setInputEnabled(final boolean isEnabled) {
	inputEnabled = isEnabled;
    }

    @SuppressWarnings("unused")
    private static void onKeyDown(final Event event) {
	if(isInputEnabled()) {
	    Keyboard.push((char) DOM.eventGetKeyCode(event), event.getCtrlKey(), event.getAltKey(), event.getShiftKey(), event.getMetaKey());
	}
    }

    @SuppressWarnings("unused")
    private static void onKeyPress(final Event event) {
	// Unused
    }

    @SuppressWarnings("unused")
    private static void onKeyUp(final Event event) {
	// Unused
    }

    /** 
     * Prevent instantiation. 
     */
    private HotKeyManager() {
	super();
	// This constructor should not be called
    }
}