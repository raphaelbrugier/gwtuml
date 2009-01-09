package com.objetdirect.gwt.umlapi.client.engine;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public interface UMLElementListener {
	
	void itemEdited(UMLElement elem, GfxObject item);

}
