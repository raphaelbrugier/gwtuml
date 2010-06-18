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

import java.util.List;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;

/**
 * This object is an artifact used to represent an object / instance of a object. <br>
 * A object is divided in three {@link NodePartArtifact} :
 * <ul>
 * <li>{@link ObjectPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ObjectPartAttributesArtifact} For the attribute list part</li>
 * </ul>
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectArtifact extends NodeArtifact {
	private ObjectPartAttributesArtifact objectAttributes;
	ObjectPartNameArtifact objectName;

	/** Default constructor ONLY for GWT-Rpc serialization */
	@Deprecated
	@SuppressWarnings("unused")
	private ObjectArtifact() {
	}

	/**
	 * Initializes the ObjectArtifact with the name "Object"
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param id
	 *            The artifacts's id
	 */
	public ObjectArtifact(final UMLCanvas canvas, int id) {
		this(canvas, id, "obj", "Object");
	}

	/**
	 * ObjectArtifact constructor, initializes the {@link NodeArtifact} with a name and without stereotype
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param objectInstance
	 *            The instance name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param objectName
	 *            The name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 */
	public ObjectArtifact(final UMLCanvas canvas, int id, final String objectInstance, final String objectName) {
		this(canvas, id, objectInstance, objectName, "");
	}

	/**
	 * ObjectArtifact constructor, initializes all {@link NodePartArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param id
	 *            The artifacts's id
	 * @param objectInstance
	 *            The instance name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param objectName
	 *            The name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param stereotype
	 *            The stereotype of the object, sent to {@link ObjectPartNameArtifact} constructor
	 */
	public ObjectArtifact(final UMLCanvas canvas, int id, final String objectInstance, final String objectName, final String stereotype) {
		super(canvas, id);
		this.objectName = new ObjectPartNameArtifact(canvas, objectInstance, objectName, stereotype);
		objectAttributes = new ObjectPartAttributesArtifact(canvas);
		nodeParts.add(this.objectName);
		nodeParts.add(objectAttributes);
		this.objectName.setNodeArtifact(this);
		objectAttributes.setNodeArtifact(this);
	}

	/**
	 * Add an {@link UMLObjectAttribute} to this object
	 * 
	 * @param attribute
	 *            The attribute, sent to {@link ObjectPartAttributesArtifact}
	 */
	public void addAttribute(final UMLObjectAttribute attribute) {
		objectAttributes.add(attribute);
	}

	/**
	 * Getter for the attributes
	 * 
	 * @return the list of attributes of this object
	 */
	public List<UMLObjectAttribute> getAttributes() {
		return objectAttributes.getList();
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name of this object
	 */
	@Override
	public String getName() {
		return objectName.getObjectName();
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle objectNameRightMenu = objectName.getRightMenu();
		final MenuBarAndTitle objectAttributesRightMenu = objectAttributes.getRightMenu();

		rightMenu.setName("Object " + objectName.getObjectName());

		rightMenu.addItem(objectNameRightMenu.getName(), objectNameRightMenu.getSubMenu());
		rightMenu.addItem(objectAttributesRightMenu.getName(), objectAttributesRightMenu.getSubMenu());

		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "Object$" + this.getLocation() + "!" + objectName.toURL() + "!" + objectAttributes.toURL();
	}

	@Override
	public void setUpAfterDeserialization() {
		objectAttributes.setUpAfterDeserialization();
		objectName.setUpAfterDeserialization();
	}
}
