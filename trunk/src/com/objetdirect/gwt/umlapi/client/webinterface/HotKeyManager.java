package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;


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

    private static UMLCanvas activeCanvas;

    private static boolean isEnabled = true;
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
	HelpManager.addHotkeyHelp("C", "Add a new class");
	HelpManager.addHotkeyHelp("N", "Add a new note");
	HelpManager.addHotkeyHelp("A", "Add a new aggregation");
	HelpManager.addHotkeyHelp("L", "Add a new association");
	HelpManager.addHotkeyHelp("Q", "Add a new composition");
	HelpManager.addHotkeyHelp("D", "Add a new dependency");
	HelpManager.addHotkeyHelp("R", "Add a new generalization");
	HelpManager.addHotkeyHelp("T", "Add a new note link");
	HelpManager.addHotkeyHelp("S", "Add a new class relation");
	HelpManager.addHotkeyHelp("Del", "Add a new realization");
	HelpManager.addHotkeyHelp("Home", "Bring this help");
	HelpManager.addHotkeyHelp("Ctrl]+[Up", "Move up selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Down", "Move down selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Left", "Move left selected object");
	HelpManager.addHotkeyHelp("Ctrl]+[Right", "Move right selected object");

    }

    /**
     * Getter to the current state of hotkeys
     * 
     * @return true if it is enabled false otherwise
     */
    public static boolean isEnabled() {
	return isEnabled;
    }

    /**
     * Setter for the active canvas (the one which will receive the hot keys)
     *  
     * @param canvas The active {@link UMLCanvas}
     */
    public static void setActiveCanvas(final UMLCanvas canvas) {
	activeCanvas = canvas;
    }

    /**
     * Set the hotkey manager state. This is used to disable hotkeys while editing for instance
     * 
     * @param isEnabled The status : True to activate False to disable 
     */
    public static void setEnabled(final boolean isEnabled) {
	HotKeyManager.isEnabled = isEnabled;
    }

    @SuppressWarnings("unused")
    private static void onKeyDown(final Event event) {
	if (!isEnabled) {
	    return;
	}
	final char keyCode = (char) DOM.eventGetKeyCode(event);
	switch (keyCode) {
	case 'C':
	    activeCanvas.addNewClass();
	    break;
	case 'N':
	    activeCanvas.addNewNote();
	    break;
	case 'A':
	    activeCanvas.toLinkMode(RelationKind.AGGREGATION);
	    break;
	case 'L':
	    activeCanvas.toLinkMode(RelationKind.ASSOCIATION);
	    break;
	case 'O':
	    activeCanvas.toLinkMode(RelationKind.COMPOSITION);
	    break;
	case 'D':
	    activeCanvas.toLinkMode(RelationKind.DEPENDENCY);
	    break;
	case 'G':
	    activeCanvas.toLinkMode(RelationKind.GENERALIZATION);
	    break;
	case 'R':
	    activeCanvas.toLinkMode(RelationKind.REALIZATION);
	    break;
	case 'T':
	    activeCanvas.toLinkMode(RelationKind.NOTE);
	    break;
	case 'S':
	    activeCanvas.toLinkMode(RelationKind.CLASSRELATION);
	    break;
	case KeyCodes.KEY_DELETE:
	    activeCanvas.removeSelected();
	    break;
	case KeyCodes.KEY_HOME:
	    HelpManager.bringHelpPopup();
	}
	if (event.getCtrlKey()) {
	    switch (keyCode) {
	    case KeyCodes.KEY_UP:
		activeCanvas.moveSelected(Direction.UP);
		break;
	    case KeyCodes.KEY_DOWN:
		activeCanvas.moveSelected(Direction.DOWN);
		break;
	    case KeyCodes.KEY_LEFT:
		activeCanvas.moveSelected(Direction.LEFT);
		break;
	    case KeyCodes.KEY_RIGHT:
		activeCanvas.moveSelected(Direction.RIGHT);
		break;
	    default:
		break;
	    }
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
	// This constructor should not be called
    }
}