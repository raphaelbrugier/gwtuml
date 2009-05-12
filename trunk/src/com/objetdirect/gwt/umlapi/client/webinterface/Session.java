/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class Session {
    private static UMLCanvas activeCanvas;
    /**
     * Getter for the active canvas
     *
     * @return The active {@link UMLCanvas}
     */
    public static UMLCanvas getActiveCanvas() {
        return activeCanvas;
    }
    /**
     * Setter for the active canvas (the one which will receive the hot keys)
     *  
     * @param canvas The active {@link UMLCanvas}
     */
    public static void setActiveCanvas(final UMLCanvas canvas) {
	activeCanvas = canvas;
    }
}
