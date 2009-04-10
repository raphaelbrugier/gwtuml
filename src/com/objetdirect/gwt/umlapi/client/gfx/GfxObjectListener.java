package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * This interface normalize the events for a graphic canvas for any gfx implementation
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public interface GfxObjectListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a gfxObject.
     */
    public void mouseClicked();

    /**
     * Invoked when a mouse button has been double clicked on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param x The abscissa of the mouse position during the click
     * @param y The coordinate of the mouse position during the click
     */
    public void mouseDblClicked(GfxObject graphicObject, int x, int y);
    
    /**
     * Invoked when the left mouse button has been pressed on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param x The abscissa of the mouse position during the click
     * @param y The coordinate of the mouse position during the click
     */
    public void mouseLeftClickPressed(GfxObject graphicObject, int x, int y);
    
    /**
     * Invoked when a the mouse has been moved
     * 
     * @param x The abscissa of the mouse position during the move
     * @param y The coordinate of the mouse position during the move
     */
    public void mouseMoved(int x, int y);
    
    /**
     * Invoked when a mouse button has been release on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param x The abscissa of the mouse position during the click
     * @param y The coordinate of the mouse position during the click
     */
    public void mouseReleased(GfxObject graphicObject, int x, int y);
    
    /**
     * Invoked when the right mouse button has been pressed on a gfxObject.
     * 
     * @param graphicObject the <code>GraphicObject</code> which launches the event
     * @param x The abscissa of the mouse position during the click
     * @param y The coordinate of the mouse position during the click
     */
    public void mouseRightClickPressed(GfxObject graphicObject, int x, int y);

}
