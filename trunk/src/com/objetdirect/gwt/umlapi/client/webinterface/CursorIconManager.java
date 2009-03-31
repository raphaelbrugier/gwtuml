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
		/**
		 * @uml.property  name="aUTO"
		 * @uml.associationEnd  
		 */
		AUTO("globalAutoCursor"), /**
		 * @uml.property  name="cROSSHAIR"
		 * @uml.associationEnd  
		 */
		CROSSHAIR("globalCrosshairCursor"), /**
		 * @uml.property  name="dEFAULT"
		 * @uml.associationEnd  
		 */
		DEFAULT(
				"globalDefaultCursor"), /**
				 * @uml.property  name="e_RESIZE"
				 * @uml.associationEnd  
				 */
				E_RESIZE("globalE_ResizeCursor"), /**
				 * @uml.property  name="hELP"
				 * @uml.associationEnd  
				 */
				HELP("globalHelpCursor"), /**
				 * @uml.property  name="mOVE"
				 * @uml.associationEnd  
				 */
				MOVE(
				"globalMoveCursor"), /**
				 * @uml.property  name="n_RESIZE"
				 * @uml.associationEnd  
				 */
				N_RESIZE(
				"globalN_ResizeCursor"), /**
				 * @uml.property  name="nE_RESIZE"
				 * @uml.associationEnd  
				 */
				NE_RESIZE(
				"globalNE_ResizeCursor"), /**
				 * @uml.property  name="nOT_ALLOWED"
				 * @uml.associationEnd  
				 */
				NOT_ALLOWED(
				"globalNotAllowedCursor"), /**
				 * @uml.property  name="nW_RESIZE"
				 * @uml.associationEnd  
				 */
				NW_RESIZE("globalNW_ResizeCursor"), /**
				 * @uml.property  name="pOINTER"
				 * @uml.associationEnd  
				 */
				POINTER("globalPointerCursor"), /**
				 * @uml.property  name="s_RESIZE"
				 * @uml.associationEnd  
				 */
				S_RESIZE("globalS_ResizeCursor"), /**
				 * @uml.property  name="sE_RESIZE"
				 * @uml.associationEnd  
				 */
				SE_RESIZE("globalSE_ResizeCursor"), /**
				 * @uml.property  name="sW_RESIZE"
				 * @uml.associationEnd  
				 */
				SW_RESIZE(
				"globalSW_ResizeCursor"), /**
				 * @uml.property  name="tEXT"
				 * @uml.associationEnd  
				 */
				TEXT("globalTextCursor"), /**
				 * @uml.property  name="w_RESIZE"
				 * @uml.associationEnd  
				 */
				W_RESIZE(
				"globalW_ResizeCursor"), /**
				 * @uml.property  name="wAIT"
				 * @uml.associationEnd  
				 */
				WAIT(
				"globalWaitCursor");
		/**
		 * @uml.property  name="cssName"
		 */
		private final String cssName;
		private PointerStyle(String cssName) {
			this.cssName = cssName;
		}
		/**
		 * @return
		 * @uml.property  name="cssName"
		 */
		public String getCssName() {
			return this.cssName;
		}
	}
	/**
	 * @uml.property  name="currentIconStyle"
	 * @uml.associationEnd  
	 */
	private static PointerStyle currentIconStyle = PointerStyle.AUTO;;
	public static void setCursorIcon(PointerStyle pStyle) {
		RootPanel.get().removeStyleName(currentIconStyle.getCssName());
		currentIconStyle = pStyle;
		RootPanel.get().addStyleName(currentIconStyle.getCssName());
	}
}
