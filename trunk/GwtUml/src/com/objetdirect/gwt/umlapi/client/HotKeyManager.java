package com.objetdirect.gwt.umlapi.client;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLCanvas;

/* Source :
 * http://markmail.org/message/5ej3lijr4iupnhbz#query:global%20listener%20gwt+page:1+mid:5ej3lijr4iupnhbz+state:results
 */
public final class HotKeyManager { 
	private static final class WindowCloseListenerImpl implements WindowCloseListener { 
		public native void onWindowClosed() /*-{ 
		$doc.onkeydown = null; 
		$doc.onkeypress = null; 
		$doc.onkeyup = null; 
		}-*/;


		public String onWindowClosing() { 
			return null;
		}

		private native void init() /*-{ 
			$doc.onkeydown = function(evt) { 
				@com.objetdirect.gwt.umlapi.client.HotKeyManager::onKeyDown(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
			} 
			$doc.onkeypress = function(evt) { 
				@com.objetdirect.gwt.umlapi.client.HotKeyManager::onKeyPress(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
			} 
			$doc.onkeyup = function(evt) { 
				@com.objetdirect.gwt.umlapi.client.HotKeyManager::onKeyUp(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event); 
			} 
		}-*/;
	}

	static { 
		WindowCloseListenerImpl closeListener = new WindowCloseListenerImpl(); 
		Window.addWindowCloseListener(closeListener); 
		closeListener.init(); 
	}

	/** * Can be called from your code to force installation of * the event handling hooks. */ 
	public static void forceStaticInit() { };

	public static void setActiveCanvas(UMLCanvas canvas) { 
		activeCanvas = canvas;
	};
	
	private static void onKeyDown(Event event) { 
		char keyCode = (char) DOM.eventGetKeyCode(event); 
		switch (keyCode) {
		case 'C':
			activeCanvas.addNewClass();
			break;
		case 'N':
			activeCanvas.addNewNote();
			break;
		case 'L':
			activeCanvas.addNewLink();
			break;
		default:
			break;
		}

	}

	private static void onKeyPress(Event event) { 
		char keyCode = (char) DOM.eventGetKeyCode(event); 
	}


	private static void onKeyUp(Event event) { 
		char keyCode = (char) DOM.eventGetKeyCode(event);
	}	
	
	private static UMLCanvas activeCanvas;
	

	/** * Prevent instantiation. */
	private HotKeyManager() { } 
} 