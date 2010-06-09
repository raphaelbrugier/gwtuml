/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Keyboard {

	/**
	 * @param keyCode
	 */
	public void push(final char keyCode) {
		push(keyCode, false, false, false, false);
	}

	/**
	 * @param keyCode
	 * @param isCtrlDown
	 * @param isAltDown
	 * @param isShiftDown
	 * @param isMetaDown
	 */
	public void push(final char keyCode, final boolean isCtrlDown, final boolean isAltDown, final boolean isShiftDown, final boolean isMetaDown) {
		Log.trace("Keyboard::push() down Key :" + keyCode + "(" + (int) keyCode + ") with ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
		switch (keyCode) {
			case 'x':
				if (isCtrlDown) {
					Session.getActiveCanvas().cut();
				}
				break;
			case 'v':
				if (isCtrlDown) {
					Session.getActiveCanvas().paste();
				}
				break;
			case 'c':
				if (isCtrlDown) {
					Session.getActiveCanvas().copy();
				} else {
					if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.CLASS) {
						Session.getActiveCanvas().addNewClass();
					}
				}
				break;
			case 'o':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.OBJECT) {
					Session.getActiveCanvas().addNewObject();
				}
				break;
			case 'f':
				if (Session.getActiveCanvas().getUMLDiagram()  == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().addNewLifeLine();
				}
				break;
			case 'n':
				Session.getActiveCanvas().addNewNote();
				break;
			case 'a':
				if (isCtrlDown) {
					Session.getActiveCanvas().selectAll();
				} else {
					if (Session.getActiveCanvas().getUMLDiagram().isClassOrObjectType()) {
						Session.getActiveCanvas().toLinkMode(LinkKind.AGGREGATION_RELATION);
					}
				}
				break;
			case 'l':
				if (Session.getActiveCanvas().getUMLDiagram().isClassOrObjectType()) {
					Session.getActiveCanvas().toLinkMode(LinkKind.ASSOCIATION_RELATION);
				}
				break;
			case 'k':
				if (Session.getActiveCanvas().getUMLDiagram().isClassOrObjectType()) {
					Session.getActiveCanvas().toLinkMode(LinkKind.COMPOSITION_RELATION);
				}
				break;
			case 'd':
				if (Session.getActiveCanvas().getUMLDiagram().isClassOrObjectType()) {
					Session.getActiveCanvas().toLinkMode(LinkKind.DEPENDENCY_RELATION);
				}
				break;
			case 'g':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.CLASS) {
					Session.getActiveCanvas().toLinkMode(LinkKind.GENERALIZATION_RELATION);
				}
				break;
			case 'r':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.CLASS) {
					Session.getActiveCanvas().toLinkMode(LinkKind.REALIZATION_RELATION);
				}
				break;
			case 't':
				Session.getActiveCanvas().toLinkMode(LinkKind.NOTE);
				break;
			case 's':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.CLASS) {
					Session.getActiveCanvas().toLinkMode(LinkKind.CLASSRELATION);
				}
				break;
			case 'i':
				if (Session.getActiveCanvas().getUMLDiagram().isHybridType()) {
					Session.getActiveCanvas().toLinkMode(LinkKind.INSTANTIATION);
				}
				break;
			case 'm':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().toLinkMode(LinkKind.ASYNCHRONOUS_MESSAGE);
				}
				break;
			case 'p':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().toLinkMode(LinkKind.SYNCHRONOUS_MESSAGE);
				}
				break;
			case 'b':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().toLinkMode(LinkKind.OBJECT_CREATION_MESSAGE);
				}
				break;
			case 'j':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().toLinkMode(LinkKind.LOST_MESSAGE);
				}
				break;

			case 'y':
				if (Session.getActiveCanvas().getUMLDiagram() == DiagramType.SEQUENCE) {
					Session.getActiveCanvas().toLinkMode(LinkKind.FOUND_MESSAGE);
				}
				break;

			case KeyCodes.KEY_DELETE:
				Session.getActiveCanvas().removeSelected();
				break;
			case 'h':
				HelpManager.bringHelpPopup();
		}

		final int speed = Direction.getDependingOnModifierSpeed(isCtrlDown, isAltDown, isMetaDown, isShiftDown);
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
}
