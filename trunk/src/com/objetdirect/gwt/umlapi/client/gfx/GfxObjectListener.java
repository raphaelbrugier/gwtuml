package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.Event;
import com.objetdirect.gwt.umlapi.client.engine.Point;

/**
 * This interface normalize the events for a graphic canvas for any gfx implementation
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public interface GfxObjectListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a gfxObject.
     * 
     * @param event The event object from DOM
     */
    public void mouseClicked(Event event);

    /**
     * Invoked when a mouse button has been double clicked on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param location The {@link Point} location of the mouse during the click
     * @param event The event object from DOM
     */
    public void mouseDblClicked(final GfxObject graphicObject, final Point location, final Event event);
    
    /**
     * Invoked when the left mouse button has been pressed on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param location The {@link Point} location of the mouse during the click
     * @param event The event object from DOM
     */
    public void mouseLeftClickPressed(final GfxObject graphicObject, final Point location, final Event event);
    
    /**
     * Invoked when a the mouse has been moved
     * 
     * @param location The {@link Point} location of the mouse during the move
     * @param event The event object from DOM
     */
    public void mouseMoved(final Point location, final Event event);
    
    /**
     * Invoked when a mouse button has been release on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param location The {@link Point} location of the mouse during the click
     * @param event The event object from DOM
     */
    public void mouseReleased(final GfxObject graphicObject, final Point location, final Event event);
    
    /**
     * Invoked when the right mouse button has been pressed on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param location The {@link Point} location of the mouse during the click
     * @param event The event object from DOM
     */
    public void mouseRightClickPressed(final GfxObject graphicObject, final Point location, final Event event);

}
