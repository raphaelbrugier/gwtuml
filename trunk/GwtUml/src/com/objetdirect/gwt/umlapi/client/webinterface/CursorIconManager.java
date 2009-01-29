package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.ui.RootPanel;

public class CursorIconManager {
	
	private static PointerStyle currentIconStyle = PointerStyle.AUTO;
	
	// To see which type of cursor it is, check : http://www.w3schools.com/CSS/tryit.asp?filename=trycss_cursor
	public enum PointerStyle {
		AUTO("globalAutoCursor"),
		CROSSHAIR("globalCrosshairCursor"),
		DEFAULT("globalDefaultCursor"),
		POINTER("globalPointerCursor"),
		MOVE("globalMoveCursor"),
		E_RESIZE("globalE_ResizeCursor"),
		NE_RESIZE("globalNE_ResizeCursor"),		
		NW_RESIZE("globalNW_ResizeCursor"),
		N_RESIZE("globalN_ResizeCursor"),
		SE_RESIZE("globalSE_ResizeCursor"),
		SW_RESIZE("globalSW_ResizeCursor"),
		S_RESIZE("globalS_ResizeCursor"),
		W_RESIZE("globalW_ResizeCursor"),
		TEXT("globalTextCursor"),
		WAIT("globalWaitCursor"),
		HELP("globalHelpCursor"),
		NOT_ALLOWED("globalNotAllowedCursor");
		
		private final String cssName;		
		
		private PointerStyle( String cssName) {
			this.cssName = cssName;
		}
		public String getCssName() {
			return this.cssName;
		}
	
	};	
	
	public static void setCursorIcon(PointerStyle pStyle) {
		RootPanel.get().removeStyleName(currentIconStyle.getCssName()); 
		currentIconStyle = pStyle;		
		RootPanel.get().addStyleName(currentIconStyle.getCssName());
	}

}
