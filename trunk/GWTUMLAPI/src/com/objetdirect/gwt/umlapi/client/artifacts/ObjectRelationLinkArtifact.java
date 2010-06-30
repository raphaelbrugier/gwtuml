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
package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.gfx.GfxStyle.SOLID;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment.NONE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment.WIRE_ARROW;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.OBJECT_RELATION;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.EditorPart;
import com.objetdirect.gwt.umlapi.client.editors.SimpleFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.ObjectRelation;

/**
 * This object represents any relation artifact between two objects
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectRelationLinkArtifact extends RelationLinkArtifact {

	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	private transient GfxObject arrowVirtualGroup;
	private transient GfxObject line;
	private transient GfxObject textVirtualGroup;
	private transient GfxObject leftRoleGfxObject;
	private transient GfxObject rightRoleGfxObject;

	private ObjectArtifact leftObjectArtifact;
	private ObjectArtifact rightObjectArtifact;
	private ObjectRelation objectRelation;

	private int current_delta;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ObjectRelationLinkArtifact() {
	}

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public ObjectRelationLinkArtifact(final UMLCanvas canvas, int id, final ObjectArtifact left, final ObjectArtifact right) {
		super(canvas, id, left, right, OBJECT_RELATION);

		if (right == left) { // An object can't reference itself
			throw new IllegalArgumentException();
		}

		leftObjectArtifact = left;
		left.addDependency(this, right);
		rightObjectArtifact = right;
		right.addDependency(this, left);

		objectRelation = new ObjectRelation(leftObjectArtifact.toUmlComponent(), rightObjectArtifact.toUmlComponent());
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		if (editedGfxObject == null) {
			return;
		}

		if (editedGfxObject.equals(leftRoleGfxObject)) {
			edit(editedGfxObject, buildLeftRoleEditorPart());
		} else if (editedGfxObject.equals(rightRoleGfxObject)) {
			edit(editedGfxObject, buildRightRoleEditorPart());
		}
	}

	private void edit(final GfxObject editedGfxObject, EditorPart editorPart) {

		final SimpleFieldEditor editor = new SimpleFieldEditor(canvas, this, editorPart);
		editor.startEdition(editorPart.getText(), editedGfxObject.getLocation().getX(), editedGfxObject.getLocation().getY(), GfxManager.getPlatform()
				.getTextWidthFor(editedGfxObject)
				+ OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
	}

	public EditorPart buildLeftRoleEditorPart() {
		EditorPart editor = new EditorPart() {
			@Override
			public void setText(String newText) {
				objectRelation.setLeftRole(newText);
			}

			@Override
			public String getText() {
				return objectRelation.getLeftRole();
			}
		};

		return editor;
	}

	public EditorPart buildRightRoleEditorPart() {
		EditorPart editor = new EditorPart() {
			@Override
			public void setText(String newText) {
				objectRelation.setRightRole(newText);
			}

			@Override
			public String getText() {
				return objectRelation.getRightRole();
			}
		};

		return editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		String menuName = objectRelation.getLeftObject().getObjectName() + " - " + objectRelation.getRightObject().getObjectName();
		rightMenu.setName(menuName);

		final MenuBar leftSide = buildRoleMenuBar(leftRoleGfxObject, buildLeftRoleEditorPart());
		final MenuBar rightSide = buildRoleMenuBar(rightRoleGfxObject, buildRightRoleEditorPart());

		final MenuBar leftNavigability = setNavigabilityCommands(true);
		leftSide.addItem("Navigability", leftNavigability);

		final MenuBar rightNavigability = setNavigabilityCommands(false);
		rightSide.addItem("Navigability", rightNavigability);

		rightMenu.addItem(objectRelation.getLeftObject().getObjectName() + " side", leftSide);
		rightMenu.addItem(objectRelation.getRightObject().getObjectName() + " side", rightSide);
		return rightMenu;
	}

	/**
	 * Build a menubar to edit or create a role. The role is the name for a side of the relation.
	 * 
	 * If the role is empty, the menu bar is just a "create" item.
	 * 
	 * Else, the menu has two items : edit or delete.
	 * 
	 * @param gfxObjectToEdit
	 *            If the role is empty, null Else the gfxObject which display the role. Ie leftRoleGfxObject or
	 *            rightRoleGfxObject.
	 * @param editorPart
	 *            The editor the edit the role
	 * @return
	 */
	private MenuBar buildRoleMenuBar(final GfxObject gfxObjectToEdit, final EditorPart editorPart) {
		MenuBar menu = new MenuBar(true);

		if (editorPart.getText().isEmpty()) {
			Command createCommand = new Command() {
				@Override
				public void execute() {
					editorPart.setText("Role");
					rebuildGfxObject();
				}
			};

			menu.addItem("Role", createCommand);
		} else {
			Command editCommand = new Command() {
				@Override
				public void execute() {
					edit(gfxObjectToEdit);
				}
			};

			Command deleteCommand = new Command() {
				@Override
				public void execute() {
					editorPart.setText("");
					rebuildGfxObject();
				}
			};

			menu.addItem("Edit", editCommand);
			menu.addItem("Delete", deleteCommand);
		}

		return menu;
	}

	private MenuBar setNavigabilityCommands(final boolean isLeft) {
		final MenuBar navigabilityMenu = new MenuBar(true);

		Command navigableCommand = new Command() {
			@Override
			public void execute() {
				if (isLeft) {
					objectRelation.setLeftNavigable(true);
				} else {
					objectRelation.setRightNavigable(true);
				}
				rebuildGfxObject();
			}
		};

		Command notNavigable = new Command() {
			@Override
			public void execute() {
				if (isLeft) {
					objectRelation.setLeftNavigable(false);
				} else {
					objectRelation.setRightNavigable(false);
				}
				rebuildGfxObject();
			}
		};

		navigabilityMenu.addItem("Navigable", navigableCommand);
		navigabilityMenu.addItem("Not navigable", notNavigable);

		return navigabilityMenu;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 * 
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public ObjectArtifact getRightObjectArtifact() {
		return rightObjectArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		leftObjectArtifact.removeDependency(this);
		rightObjectArtifact.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	// TODO Should use the metamodel object objectRelation, but need changes on the UrlParser.
	public String toURL() {
		return "ObjectRelationLink$<" + leftObjectArtifact.getId() + ">!<" + rightObjectArtifact.getId() + ">!" + relation.getLinkKind().getName() + "!"
				+ relation.getName() + "!" + relation.getLinkStyle().getName() + "!" + relation.getLeftAdornment().getName() + "!"
				+ relation.getRightAdornment().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		line.setStroke(ThemeManager.getTheme().getObjectRelationForegroundColor(), 1);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getObjectRelationForegroundColor(), 1);
	}

	Anchor getAnchorType(final ObjectArtifact objectArtifact, final Point point) {
		if (point.getX() == objectArtifact.getLocation().getX()) {
			return Anchor.LEFT;
		} else if (point.getY() == objectArtifact.getLocation().getY()) {
			return Anchor.TOP;
		} else if (point.getX() == objectArtifact.getLocation().getX() + objectArtifact.getWidth()) {
			return Anchor.RIGHT;
		} else if (point.getY() == objectArtifact.getLocation().getY() + objectArtifact.getHeight()) {
			return Anchor.BOTTOM;
		}
		return Anchor.UNKNOWN;
	}

	int getTextX(final GfxObject text, final boolean isLeft) {

		Point relative_point1 = leftPoint;
		Point relative_point2 = rightPoint;
		final int textWidth = GfxManager.getPlatform().getTextWidthFor(text);
		if (!isLeft) {
			relative_point1 = rightPoint;
			relative_point2 = leftPoint;
		}
		switch (this.getAnchorType(isLeft ? leftObjectArtifact : rightObjectArtifact, relative_point1)) {
			case LEFT:
				return relative_point1.getX() - textWidth - OptionsManager.get("RectangleLeftPadding");
			case RIGHT:
				return relative_point1.getX() + OptionsManager.get("RectangleRightPadding");
			case TOP:
			case BOTTOM:
			case UNKNOWN:
				if (relative_point1.getX() < relative_point2.getX()) {
					return relative_point1.getX() - textWidth - OptionsManager.get("RectangleLeftPadding");
				}
				return relative_point1.getX() + OptionsManager.get("RectangleRightPadding");
		}
		return 0;
	}

	int getTextY(final GfxObject text, final boolean isLeft) {
		Point relative_point1 = leftPoint;
		Point relative_point2 = rightPoint;
		if (!isLeft) {
			relative_point1 = rightPoint;
			relative_point2 = leftPoint;
		}
		final int textHeight = GfxManager.getPlatform().getTextHeightFor(text);
		final int delta = current_delta;
		current_delta += 8; // TODO : Fix Height
		switch (this.getAnchorType(isLeft ? leftObjectArtifact : rightObjectArtifact, relative_point1)) {
			case LEFT:
			case RIGHT:
				if (relative_point1.getY() > relative_point2.getY()) {
					return relative_point1.getY() + OptionsManager.get("RectangleBottomPadding") + delta;
				}
				return relative_point1.getY() - textHeight - OptionsManager.get("RectangleTopPadding") - delta;
			case TOP:
				return relative_point1.getY() - textHeight - OptionsManager.get("RectangleTopPadding") - delta;
			case BOTTOM:
			case UNKNOWN:
				return relative_point1.getY() + OptionsManager.get("RectangleBottomPadding") + delta;
		}
		return 0;
	}

	@Override
	protected void buildGfxObject() {
		line = this.buildLine();

		line.setStroke(ThemeManager.getTheme().getClassRelationForegroundColor(), 1);
		line.setStrokeStyle(SOLID);
		line.addToVirtualGroup(gfxObject);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		arrowVirtualGroup.addToVirtualGroup(gfxObject);
		final GfxObject leftArrow = GeometryManager.getPlatform().buildAdornment(leftPoint, leftDirectionPoint, getAdornmentFromNavigability(true));
		final GfxObject rightArrow = GeometryManager.getPlatform().buildAdornment(rightPoint, rightDirectionPoint, getAdornmentFromNavigability(false));

		if (leftArrow != null) {
			leftArrow.addToVirtualGroup(arrowVirtualGroup);
		}
		if (rightArrow != null) {
			rightArrow.addToVirtualGroup(arrowVirtualGroup);
		}
		// Making the text group :
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

		current_delta = 0;
		if (!objectRelation.getLeftRole().equals("")) {
			leftRoleGfxObject = this.createText(objectRelation.getLeftRole(), true);
			leftRoleGfxObject.addToVirtualGroup(textVirtualGroup);
		}
		current_delta = 0;
		if (!objectRelation.getRightRole().equals("")) {
			rightRoleGfxObject = this.createText(objectRelation.getRightRole(), false);
			rightRoleGfxObject.addToVirtualGroup(textVirtualGroup);
		}

		gfxObject.moveToBack();
	}

	private LinkAdornment getAdornmentFromNavigability(boolean isLeft) {
		if (isLeft) {
			if (objectRelation.isLeftNavigable()) {
				return WIRE_ARROW;
			}
			return NONE;
		}
		if (objectRelation.isRightNavigable()) {
			return WIRE_ARROW;
		}
		return NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();
		line.setStroke(ThemeManager.getTheme().getObjectRelationHighlightedForegroundColor(), 2);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getObjectRelationHighlightedForegroundColor(), 2);
	}

	private GfxObject createText(final String text, boolean isLeft) {

		final GfxObject textGfxObject = GfxManager.getPlatform().buildText(text, Point.getOrigin());
		textGfxObject.setFont(OptionsManager.getSmallFont());
		textGfxObject.setStroke(ThemeManager.getTheme().getObjectRelationBackgroundColor(), 0);
		textGfxObject.setFillColor(ThemeManager.getTheme().getObjectRelationForegroundColor());
		textGfxObject.translate(new Point(this.getTextX(textGfxObject, isLeft), this.getTextY(textGfxObject, isLeft)));

		return textGfxObject;
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
