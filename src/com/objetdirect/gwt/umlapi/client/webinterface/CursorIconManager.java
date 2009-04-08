package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class is an helper to change the current mouse cursor
 * To see which type of cursor it is, check :
 * http://www.w3schools.com/CSS/tryit.asp?filename=trycss_cursor
 * @author florian
 */
public class CursorIconManager {

    /**
     * @author florian
     */
    public enum PointerStyle {

	AUTO("globalAutoCursor"), CROSSHAIR("globalCrosshairCursor"), DEFAULT(
		"globalDefaultCursor"), E_RESIZE("globalE_ResizeCursor"), HELP(
		"globalHelpCursor"), MOVE("globalMoveCursor"), N_RESIZE(
		"globalN_ResizeCursor"), NE_RESIZE("globalNE_ResizeCursor"), NOT_ALLOWED(
		"globalNotAllowedCursor"), NW_RESIZE("globalNW_ResizeCursor"), POINTER(
		"globalPointerCursor"), S_RESIZE("globalS_ResizeCursor"), SE_RESIZE(
		"globalSE_ResizeCursor"), SW_RESIZE("globalSW_ResizeCursor"), TEXT(
		"globalTextCursor"), W_RESIZE("globalW_ResizeCursor"), WAIT(
		"globalWaitCursor");
	/**
		 * 
		 */
	private final String cssName;

	private PointerStyle(final String cssName) {
	    this.cssName = cssName;
	}

	/**
	 * @return the css identifier for a pointer style
	 * 
	 */
	public String getCssName() {
	    return this.cssName;
	}
    }

    private static PointerStyle currentIconStyle = PointerStyle.AUTO;

    public static void setCursorIcon(final PointerStyle pStyle) {
	RootPanel.get().removeStyleName(currentIconStyle.getCssName());
	currentIconStyle = pStyle;
	RootPanel.get().addStyleName(currentIconStyle.getCssName());
    }
}
