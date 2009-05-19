/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
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
	push(keyCode, false, false, false);
    }
    /**
     * @param keyCode 
     * @param isCtrlDown 
     * @param isAltDown 
     * @param isShiftDown 
     */
    public static void push(final char keyCode, final boolean isCtrlDown, final boolean isAltDown, final boolean isShiftDown) {
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
	case 'N':
	    Session.getActiveCanvas().addNewNote();
	    break;
	case 'A':
	    Session.getActiveCanvas().toLinkMode(RelationKind.AGGREGATION);
	    break;
	case 'L':
	    Session.getActiveCanvas().toLinkMode(RelationKind.ASSOCIATION);
	    break;
	case 'K':
	    Session.getActiveCanvas().toLinkMode(RelationKind.COMPOSITION);
	    break;
	case 'D':
	    Session.getActiveCanvas().toLinkMode(RelationKind.DEPENDENCY);
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
	if(isCtrlDown) {
	    switch (keyCode) {
	    case KeyCodes.KEY_UP:
		Session.getActiveCanvas().moveSelected(Direction.UP);
		break;
	    case KeyCodes.KEY_DOWN:
		Session.getActiveCanvas().moveSelected(Direction.DOWN);
		break;
	    case KeyCodes.KEY_LEFT:
		Session.getActiveCanvas().moveSelected(Direction.LEFT);
		break;
	    case KeyCodes.KEY_RIGHT:
		Session.getActiveCanvas().moveSelected(Direction.RIGHT);
		break;
	    default:
		break;
	    }
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
