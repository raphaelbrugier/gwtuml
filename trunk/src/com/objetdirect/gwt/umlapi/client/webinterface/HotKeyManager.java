package com.objetdirect.gwt.umlapi.client.webinterface;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
/* Source :
 * http://markmail.org/message/5ej3lijr4iupnhbz#query:global%20listener%20gwt+page:1+mid:5ej3lijr4iupnhbz+state:results
 */
/**
 * @author  florian
 */
public final class HotKeyManager {
    private static boolean isEnabled = true;

    private static final class WindowCloseHandlerImpl implements
    ClosingHandler {
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
        public native void onWindowClosing(ClosingEvent event) /*-{ 
        $doc.onkeydown = null; 
        $doc.onkeypress = null; 
        $doc.onkeyup = null; 
        }-*/;

    }
    private static UMLCanvas activeCanvas;
    static {
        WindowCloseHandlerImpl closeListener = new WindowCloseHandlerImpl();
        Window.addWindowClosingHandler(closeListener);
        closeListener.init();
    };
    /**
     * * Can be called from your code to force installation of * the event
     * handling hooks.
     */
    public static void forceStaticInit() {
        HelpManager.addHotkeyHelp("C", "Add a new class");
        HelpManager.addHotkeyHelp("N", "Add a new note");
        HelpManager.addHotkeyHelp("A", "Add a new aggregation");
        HelpManager.addHotkeyHelp("L", "Add a new association");
        HelpManager.addHotkeyHelp("Q", "Add a new composition");
        HelpManager.addHotkeyHelp("D", "Add a new dependency");
        HelpManager.addHotkeyHelp("R", "Add a new generalization");
        HelpManager.addHotkeyHelp("Del", "Add a new realization");
        HelpManager.addHotkeyHelp("Home", "Bring this help");
        HelpManager.addHotkeyHelp("Ctrl]+[Up", "Move up selected object");
        HelpManager.addHotkeyHelp("Ctrl]+[Down", "Move down selected object");
        HelpManager.addHotkeyHelp("Ctrl]+[Left", "Move left selected object");
        HelpManager.addHotkeyHelp("Ctrl]+[Right", "Move right selected object");
        
    };

    public static void setActiveCanvas(UMLCanvas canvas) {
        activeCanvas = canvas;
    }
    @SuppressWarnings("unused")
    private static void onKeyDown(Event event) {
        if(!isEnabled) return;
        char keyCode = (char) DOM.eventGetKeyCode(event);
        switch (keyCode) {
        case 'C':
            activeCanvas.addNewClass();
            break;
        case 'N':
            activeCanvas.addNewNote();
            break;
        case 'A':
            activeCanvas.addNewLink(RelationKind.AGGREGATION);
            break;
        case 'L':
            activeCanvas.addNewLink(RelationKind.ASSOCIATION);
            break;
        case 'O':
            activeCanvas.addNewLink(RelationKind.COMPOSITION);
            break;
        case 'D':
            activeCanvas.addNewLink(RelationKind.DEPENDENCY);
            break;
        case 'G':
            activeCanvas.addNewLink(RelationKind.GENERALIZATION);
            break;
        case 'R':
            activeCanvas.addNewLink(RelationKind.REALIZATION);
            break;
        case KeyCodes.KEY_DELETE:
            activeCanvas.removeSelected();
            break;
        case KeyCodes.KEY_HOME:
            HelpManager.bringHelpPopup();        
        }
        if(event.getCtrlKey())
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
    @SuppressWarnings("unused")
    private static void onKeyPress(Event event) {
    }
    @SuppressWarnings("unused")
    private static void onKeyUp(Event event) {
    }
    /** * Prevent instantiation. */
    private HotKeyManager() {
    }
    /**
     * @return the isEnabled
     */
    public static boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public static void setEnabled(boolean isEnabled) {
        HotKeyManager.isEnabled = isEnabled;
    }
}