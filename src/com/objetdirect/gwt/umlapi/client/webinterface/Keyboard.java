/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class Keyboard {

    private static boolean imEnabled = true;
    /**
     * @param keyCode
     */
    public static void push(final char keyCode) {
	push(keyCode, false, false, false, false);
    }
    /**
     * @param keyCode 
     * @param isCtrlDown 
     * @param isAltDown 
     * @param isShiftDown 
     * @param isMetaDown 
     */
    public static void push(final char keyCode, final boolean isCtrlDown, final boolean isAltDown, final boolean isShiftDown, final boolean isMetaDown) {
	if (!imEnabled) {
	    Log.trace("Keyboard pushed but disabled");
	    return;
	}
	Log.trace("Keyboard down Key :" + keyCode + "(" + (int) keyCode + ") with ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
	switch (keyCode) {
	case 'C':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassType()) {
		Session.getActiveCanvas().addNewClass();
	    }
	    break;
	case 'O':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isObjectType()) {
		Session.getActiveCanvas().addNewObject();
	    }
	    break;
	case 'F':
	    if(Session.getActiveCanvas().getUMLDiagram().getType() == Type.SEQUENCE) {
		Session.getActiveCanvas().addNewLifeLine();
	    }
	    break;
	case 'N':
	    Session.getActiveCanvas().addNewNote();
	    break;
	case 'A':
	    if(isCtrlDown) {
		Session.getActiveCanvas().selectAll();
	    }
	    else {
		if(Session.getActiveCanvas().getUMLDiagram().getType().isClassOrObjectType()) {
		    Session.getActiveCanvas().toLinkMode(RelationKind.AGGREGATION);
		}
	    }
	    break;
	case 'L':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassOrObjectType()) {
	    Session.getActiveCanvas().toLinkMode(RelationKind.ASSOCIATION);
	    }
	    break;
	case 'K':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassOrObjectType()) {
	    Session.getActiveCanvas().toLinkMode(RelationKind.COMPOSITION);
	    }
	    break;
	case 'D':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassOrObjectType()) {
	    Session.getActiveCanvas().toLinkMode(RelationKind.DEPENDENCY);
	    }
	    break;
	case 'G':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassType()) {
		Session.getActiveCanvas().toLinkMode(RelationKind.GENERALIZATION);
	    }
	    break;
	case 'R':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassType()) {
		Session.getActiveCanvas().toLinkMode(RelationKind.REALIZATION);
	    }
	    break;
	case 'T':
	    Session.getActiveCanvas().toLinkMode(RelationKind.NOTE);
	    break;
	case 'S':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isClassType()) {
		Session.getActiveCanvas().toLinkMode(RelationKind.CLASSRELATION);
	    }
	    break;
	case 'I':
	    if(Session.getActiveCanvas().getUMLDiagram().getType().isHybridType()) {
		Session.getActiveCanvas().toLinkMode(RelationKind.INSTANTIATION);
	    }
	    break;
	case 'U':
	    HistoryManager.upgradeDiagramURL(Session.getActiveCanvas().toUrl());
	    break;
	case KeyCodes.KEY_DELETE:
	    Session.getActiveCanvas().removeSelected();
	    break;
	case 'H':
	    HelpManager.bringHelpPopup();
	}

	int speed = Direction.getDependingOnModifierSpeed(isCtrlDown, isAltDown, isMetaDown, isShiftDown);
	switch (keyCode) {
	case KeyCodes.KEY_UP:
	    Session.getActiveCanvas().moveSelected(Direction.UP.withSpeed(speed));
	    break;
	case KeyCodes.KEY_DOWN:
	    Session.getActiveCanvas().moveSelected(Direction.DOWN.withSpeed(speed));
	    break;
	case KeyCodes.KEY_LEFT:
	    Session.getActiveCanvas().moveSelected(Direction.LEFT.withSpeed(speed));
	    break;
	case KeyCodes.KEY_RIGHT:
	    Session.getActiveCanvas().moveSelected(Direction.RIGHT.withSpeed(speed));
	    break;
	default:
	    break;
	}

    }
    /**
     * Getter to the current state of {@link Keyboard}
     * 
     * @return true if it is enabled false otherwise
     */
    public static boolean isEnabled() {
	return imEnabled;
    }

    /**
     * Set the {@link Keyboard} state. This is used to disable {@link Keyboard} while editing for instance
     * 
     * @param isEnabled The status : True to activate False to disable 
     */
    public static void setEnabled(final boolean isEnabled) {
	Keyboard.imEnabled = isEnabled;
    }

}
