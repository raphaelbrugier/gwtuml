package com.objetdirect.gwt.umlapi.client.webinterface;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * @author  florian
 */
public class CursorIconManager {
	// To see which type of cursor it is, check :
	// http://www.w3schools.com/CSS/tryit.asp?filename=trycss_cursor
	/**
	 * @author   florian
	 */
	public enum PointerStyle {
 
		AUTO("globalAutoCursor"),  
		CROSSHAIR("globalCrosshairCursor"),  
		DEFAULT(
				"globalDefaultCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				E_RESIZE("globalE_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				HELP("globalHelpCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				MOVE(
				"globalMoveCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				N_RESIZE(
				"globalN_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				NE_RESIZE(
				"globalNE_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				NOT_ALLOWED(
				"globalNotAllowedCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				NW_RESIZE("globalNW_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				POINTER("globalPointerCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				S_RESIZE("globalS_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				SE_RESIZE("globalSE_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				SW_RESIZE(
				"globalSW_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				TEXT("globalTextCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				W_RESIZE(
				"globalW_ResizeCursor"), /**
				 * 
				 * @uml.associationEnd  
				 */
				WAIT(
				"globalWaitCursor");
		/**
		 * 
		 */
		private final String cssName;
		private PointerStyle(String cssName) {
			this.cssName = cssName;
		}
		/**
		 * @return
		 * 
		 */
		public String getCssName() {
			return this.cssName;
		}
	}
private static PointerStyle currentIconStyle = PointerStyle.AUTO;;
	public static void setCursorIcon(PointerStyle pStyle) {
		RootPanel.get().removeStyleName(currentIconStyle.getCssName());
		currentIconStyle = pStyle;
		RootPanel.get().addStyleName(currentIconStyle.getCssName());
	}
}
