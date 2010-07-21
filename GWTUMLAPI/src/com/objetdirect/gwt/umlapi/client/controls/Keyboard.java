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

import static com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType.CLASS;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType.OBJECT;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType.SEQUENCE;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyCodes;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.helpers.HelpManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.ClassDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.ObjectDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.SequenceDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
// TODO split this class in the 3 childs classes depending on the diagram type.
public class Keyboard {

	private UMLCanvas umlCanvas;
	private final DiagramType diagramType;

	// TODO oups we really need to split this in 3 children class with inheritance.
	private ClassDiagram classDiagram;
	private ObjectDiagram objectDiagram;
	private SequenceDiagram sequenceDiagram;

	public Keyboard(ClassDiagram classDiagram) {
		this.classDiagram = classDiagram;
		umlCanvas = (UMLCanvas) classDiagram; //TODO Ugly, should add methods used in umlCanvas to the Diagram interface.
		diagramType = CLASS;
	}

	public Keyboard(ObjectDiagram objectDiagram) {
		this.objectDiagram = objectDiagram;
		umlCanvas = (UMLCanvas) objectDiagram; //TODO
		diagramType = OBJECT;
	}

	public Keyboard(SequenceDiagram sequenceDiagram) {
		this.sequenceDiagram = sequenceDiagram;
		umlCanvas = (UMLCanvas) sequenceDiagram; //TODO
		diagramType = SEQUENCE;
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
			case 'c':
				if (!isCtrlDown) {
					classDiagram.addNewClass();
				}
				break;

			case 'a':
				if (!isCtrlDown) {
					classDiagram.toLinkMode(LinkKind.AGGREGATION_RELATION);
				}
				break;

			case 'l':
				classDiagram.toLinkMode(LinkKind.ASSOCIATION_RELATION);
				break;

			case 'k':
				classDiagram.toLinkMode(LinkKind.COMPOSITION_RELATION);
				break;

			case 'g':
				classDiagram.toLinkMode(LinkKind.GENERALIZATION_RELATION);
				break;

			case 's':
				classDiagram.toLinkMode(LinkKind.CLASSRELATION);
				break;
		}
	}

	private void objectDiagramPush(final char keyCode, final boolean isCtrlDown) {
		switch (keyCode) {
			case 'c':
				if (!isCtrlDown) {
					objectDiagram.addNewClass();
				}
				break;

			case 'o':
				objectDiagram.addNewObject();
				break;

			case 'd':
				objectDiagram.toLinkMode(LinkKind.DEPENDENCY_RELATION);
				break;

			case 'i':
				objectDiagram.toLinkMode(LinkKind.INSTANTIATION);
				break;
		}
	}

	private void sequenceDiagramPush(final char keyCode) {
		switch (keyCode) {
			case 'm':
				sequenceDiagram.toLinkMode(LinkKind.ASYNCHRONOUS_MESSAGE);
				break;
			case 'p':
				sequenceDiagram.toLinkMode(LinkKind.SYNCHRONOUS_MESSAGE);
				break;
			case 'b':
				sequenceDiagram.toLinkMode(LinkKind.OBJECT_CREATION_MESSAGE);
				break;
			case 'j':
				sequenceDiagram.toLinkMode(LinkKind.LOST_MESSAGE);
				break;

			case 'y':
				sequenceDiagram.toLinkMode(LinkKind.FOUND_MESSAGE);
				break;
			case 'f':
				sequenceDiagram.addNewLifeLine();
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
