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
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Keyboard {

	private final UMLCanvas umlCanvas;

	private final DiagramType diagramType;

	public Keyboard(UMLCanvas umlCanvas, DiagramType diagramType) {
		this.umlCanvas = umlCanvas;
		this.diagramType = diagramType;
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

		commonPush(keyCode);
		moveDiagramPush(keyCode, isCtrlDown, isAltDown, isShiftDown, isMetaDown);

		if (isCtrlDown) {
			ctrlDownPush(keyCode);
		}

		switch (diagramType) {
			case CLASS:
				classDiagramPush(keyCode, isCtrlDown);
				break;
			case OBJECT:
				objectDiagramPush(keyCode, isCtrlDown);
				break;
			case SEQUENCE:
				sequenceDiagramPush(keyCode);
				break;
		}
	}

	private void commonPush(final char keyCode) {
		switch (keyCode) {
			case 'n':
				umlCanvas.addNewNote();
				break;
			case 't':
				umlCanvas.toLinkMode(LinkKind.NOTE);
				break;
			case KeyCodes.KEY_DELETE:
				umlCanvas.removeSelected();
				break;
			case 'h':
				HelpManager.bringHelpPopup();
		}
	}

	private void ctrlDownPush(final char keyCode) {
		switch (keyCode) {
			case 'x':
				umlCanvas.cut();
				break;
			case 'v':
				umlCanvas.paste();
				break;
			case 'c':
				umlCanvas.copy();
				break;
			case 'a':
				umlCanvas.selectAll();
				break;
		}
	}

	private void classDiagramPush(final char keyCode, final boolean isCtrlDown) {
		switch (keyCode) {
			case 'a':
				if (!isCtrlDown) {
					umlCanvas.toLinkMode(LinkKind.AGGREGATION_RELATION);
				}
				break;

			case 'l':
				umlCanvas.toLinkMode(LinkKind.ASSOCIATION_RELATION);
				break;

			case 'k':
				umlCanvas.toLinkMode(LinkKind.COMPOSITION_RELATION);
				break;

			case 'g':
				umlCanvas.toLinkMode(LinkKind.GENERALIZATION_RELATION);
				break;

			case 's':
				umlCanvas.toLinkMode(LinkKind.CLASSRELATION);
				break;
		}
	}

	private void objectDiagramPush(final char keyCode, final boolean isCtrlDown) {
		switch (keyCode) {
			case 'c':
				if (!isCtrlDown) {
					umlCanvas.addNewClass();
				}
				break;

			case 'o':
				umlCanvas.addNewObject();
				break;

			case 'd':
				umlCanvas.toLinkMode(LinkKind.DEPENDENCY_RELATION);
				break;

			case 'i':
				umlCanvas.toLinkMode(LinkKind.INSTANTIATION);
				break;
		}
	}

	private void sequenceDiagramPush(final char keyCode) {
		switch (keyCode) {
			case 'm':
				umlCanvas.toLinkMode(LinkKind.ASYNCHRONOUS_MESSAGE);
				break;
			case 'p':
				umlCanvas.toLinkMode(LinkKind.SYNCHRONOUS_MESSAGE);
				break;
			case 'b':
				umlCanvas.toLinkMode(LinkKind.OBJECT_CREATION_MESSAGE);
				break;
			case 'j':
				umlCanvas.toLinkMode(LinkKind.LOST_MESSAGE);
				break;

			case 'y':
				umlCanvas.toLinkMode(LinkKind.FOUND_MESSAGE);
				break;
			case 'f':
				umlCanvas.addNewLifeLine();
				break;
		}
	}

	private void moveDiagramPush(final char keyCode, final boolean isCtrlDown, final boolean isAltDown, final boolean isShiftDown, final boolean isMetaDown) {
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
