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
package com.objetdirect.gwt.umlapi.client.controls;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.helpers.HelpManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Keyboard {

	private final UMLCanvas umlCanvas;

	public Keyboard(UMLCanvas umlCanvas) {
		this.umlCanvas = umlCanvas;
	}

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
					umlCanvas.cut();
				}
				break;
			case 'v':
				if (isCtrlDown) {
					umlCanvas.paste();
				}
				break;
			case 'c':
				if (isCtrlDown) {
					umlCanvas.copy();
				} else {
					if (umlCanvas.getUMLDiagram() == DiagramType.CLASS) {
						umlCanvas.addNewClass();
					}
				}
				break;
			case 'o':
				if (umlCanvas.getUMLDiagram() == DiagramType.OBJECT) {
					umlCanvas.addNewObject();
				}
				break;
			case 'f':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.addNewLifeLine();
				}
				break;
			case 'n':
				umlCanvas.addNewNote();
				break;
			case 'a':
				if (isCtrlDown) {
					umlCanvas.selectAll();
				} else {
					if (umlCanvas.getUMLDiagram().isClassOrObjectType()) {
						umlCanvas.toLinkMode(LinkKind.AGGREGATION_RELATION);
					}
				}
				break;
			case 'l':
				if (umlCanvas.getUMLDiagram().isClassOrObjectType()) {
					umlCanvas.toLinkMode(LinkKind.ASSOCIATION_RELATION);
				}
				break;
			case 'k':
				if (umlCanvas.getUMLDiagram().isClassOrObjectType()) {
					umlCanvas.toLinkMode(LinkKind.COMPOSITION_RELATION);
				}
				break;
			case 'd':
				if (umlCanvas.getUMLDiagram().isClassOrObjectType()) {
					umlCanvas.toLinkMode(LinkKind.DEPENDENCY_RELATION);
				}
				break;
			case 'g':
				if (umlCanvas.getUMLDiagram() == DiagramType.CLASS) {
					umlCanvas.toLinkMode(LinkKind.GENERALIZATION_RELATION);
				}
				break;
			case 'r':
				if (umlCanvas.getUMLDiagram() == DiagramType.CLASS) {
					umlCanvas.toLinkMode(LinkKind.REALIZATION_RELATION);
				}
				break;
			case 't':
				umlCanvas.toLinkMode(LinkKind.NOTE);
				break;
			case 's':
				if (umlCanvas.getUMLDiagram() == DiagramType.CLASS) {
					umlCanvas.toLinkMode(LinkKind.CLASSRELATION);
				}
				break;
			case 'i':
				if (umlCanvas.getUMLDiagram().isHybridType()) {
					umlCanvas.toLinkMode(LinkKind.INSTANTIATION);
				}
				break;
			case 'm':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.toLinkMode(LinkKind.ASYNCHRONOUS_MESSAGE);
				}
				break;
			case 'p':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.toLinkMode(LinkKind.SYNCHRONOUS_MESSAGE);
				}
				break;
			case 'b':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.toLinkMode(LinkKind.OBJECT_CREATION_MESSAGE);
				}
				break;
			case 'j':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.toLinkMode(LinkKind.LOST_MESSAGE);
				}
				break;

			case 'y':
				if (umlCanvas.getUMLDiagram() == DiagramType.SEQUENCE) {
					umlCanvas.toLinkMode(LinkKind.FOUND_MESSAGE);
				}
				break;

			case KeyCodes.KEY_DELETE:
				umlCanvas.removeSelected();
				break;
			case 'h':
				HelpManager.bringHelpPopup();
		}

		final int speed = Direction.getDependingOnModifierSpeed(isCtrlDown, isAltDown, isMetaDown, isShiftDown);
		switch (keyCode) {
			case KeyCodes.KEY_UP:
				umlCanvas.moveSelected(Direction.UP.withSpeed(speed));
				break;
			case KeyCodes.KEY_DOWN:
				umlCanvas.moveSelected(Direction.DOWN.withSpeed(speed));
				break;
			case KeyCodes.KEY_LEFT:
				umlCanvas.moveSelected(Direction.LEFT.withSpeed(speed));
				break;
			case KeyCodes.KEY_RIGHT:
				umlCanvas.moveSelected(Direction.RIGHT.withSpeed(speed));
				break;
			default:
				break;
		}
	}
}
