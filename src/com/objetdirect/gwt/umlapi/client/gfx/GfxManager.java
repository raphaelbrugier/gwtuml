package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * This class permits to load one implementation of the graphic system and use it
 *  
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class GfxManager {
    private static GfxPlatform instance;

    /**
     * Getter of the current {@link GfxPlatform} instance
     * 
     * @return The {@link GfxPlatform} instance set by {@link GfxManager#setPlatform(GfxPlatform)}
     */
    public static GfxPlatform getPlatform() {
	return instance;
    }

    /**
     * Setter of the current {@link GfxPlatform} instance
     * 
     * @param gfxPlateform The current {@link GfxPlatform} instance to be set
     */
    public static void setPlatform(final GfxPlatform gfxPlateform) {
	instance = gfxPlateform;
    }

    /**
     * Set the current {@link GfxPlatform} from its index 
     * 
     * @param platformIndex The platform index of the new one
     */
    public static void setPlatform(int platformIndex) {
	//FIXME: Find a better way
	switch(platformIndex) {
	case 0:
	    setPlatform(new TatamiGfxPlatfrom());
	    break;
	case 1:
	    setPlatform(new IncubatorGfxPlatform());
	    break;
	case 2:
	    setPlatform(new GWTCanvasGfxPlatform());
	    break;
	default:
	    setPlatform(new TatamiGfxPlatfrom());
	}
    }
}
