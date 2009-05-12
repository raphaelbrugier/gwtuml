package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.Event;

/**
 * This interface normalize the events for a graphic canvas for any gfx implementation
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public interface GfxObjectListener {

    /**
     * Invoked when a mouse button has been double clicked on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param event The event object from DOM
     */
    public void mouseDoubleClicked(final GfxObject graphicObject, final Event event);
    
    /**
     * Invoked when the left mouse button has been pressed on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param event The event object from DOM
     */
    public void mousePressed(final GfxObject graphicObject, final Event event);
    
    /**
     * Invoked when a the mouse has been moved
     * 
     * @param event The event object from DOM
     */
    public void mouseMoved(final Event event);
    
    /**
     * Invoked when a mouse button has been release on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param event The event object from DOM
     */
    public void mouseReleased(final GfxObject graphicObject,  final Event event);
    
}
