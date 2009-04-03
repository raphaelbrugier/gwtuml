package com.objetdirect.gwt.umlapi.client.webinterface;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
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
	};
 
	public static void setActiveCanvas(UMLCanvas canvas) {
		activeCanvas = canvas;
	}
	@SuppressWarnings("unused")
	private static void onKeyDown(Event event) {
		char keyCode = (char) DOM.eventGetKeyCode(event);
		///if (DOM.eventGetCtrlKey(event)) {
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
		//}
	}
	@SuppressWarnings("unused")
	private static void onKeyPress(Event event) {
		// char keyCode = (char) DOM.eventGetKeyCode(event);
	}
	@SuppressWarnings("unused")
	private static void onKeyUp(Event event) {
		// char keyCode = (char) DOM.eventGetKeyCode(event);
	}
	/** * Prevent instantiation. */
	private HotKeyManager() {
	}
}