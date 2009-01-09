package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public interface UMLElementListener {
	
	void itemEdited(UMLElement elem, GraphicObject item);

}
