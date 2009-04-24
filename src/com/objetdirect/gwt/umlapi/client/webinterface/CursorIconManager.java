package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class is an helper to change the current mouse cursor To see which type
 * of cursor it is, check :
 * http://www.w3schools.com/CSS/tryit.asp?filename=trycss_cursor
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class CursorIconManager {

    /**
     * This enumeration lists all the cursor available in CSS in a browser
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    public enum PointerStyle {

	/**
	 * Auto cursor
	 */
	AUTO("globalAutoCursor"), 
	/**
	 * Crosshair cursor
	 */
	CROSSHAIR("globalCrosshairCursor"), 
	/**
	 * Default cursor
	 */
	DEFAULT("globalDefaultCursor"), 
	/**
	 * Resize east cursor
	 */
	E_RESIZE("globalE_ResizeCursor"), 
	/**
	 * Help cursor
	 */
	HELP("globalHelpCursor"), 
	/**
	 * Move cursor
	 */
	MOVE("globalMoveCursor"), 
	/**
	 * Resize north cursor
	 */
	N_RESIZE("globalN_ResizeCursor"),
	/**
	 * Resize north east cursor
	 */
	NE_RESIZE("globalNE_ResizeCursor"),
	/**
	 * Not allowed cursor
	 */
	NOT_ALLOWED("globalNotAllowedCursor"), 
	/**
	 * Resize north west cursor
	 */
	NW_RESIZE("globalNW_ResizeCursor"),
	/**
	 * Pointer cursor
	 */
	POINTER("globalPointerCursor"), 
	/**
	 * Resize south cursor
	 */
	S_RESIZE("globalS_ResizeCursor"), 
	/**
	 * Resize south east cursor
	 */
	SE_RESIZE("globalSE_ResizeCursor"), 
	/**
	 * Resize south west cursor
	 */
	SW_RESIZE("globalSW_ResizeCursor"),
	/**
	 * Text edition cursor
	 */
	TEXT("globalTextCursor"), 
	/**
	 * Resize west cursor
	 */
	W_RESIZE("globalW_ResizeCursor"),
	/**
	 * Wait cursor
	 */
	WAIT("globalWaitCursor");
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

    /**
     * Setter for the current cursor icon
     * 
     * @param pStyle The style of the cursor in {@link PointerStyle}
     */
    public static void setCursorIcon(final PointerStyle pStyle) {
	RootPanel.get().removeStyleName(currentIconStyle.getCssName());
	currentIconStyle = pStyle;
	RootPanel.get().addStyleName(currentIconStyle.getCssName());
    }
}