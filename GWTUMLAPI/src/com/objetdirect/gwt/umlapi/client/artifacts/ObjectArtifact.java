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

import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
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
	private ObjectPartAttributesArtifact objectAttributesArtifact;
	private ObjectPartNameArtifact objectNameArtifact;

	/**
	 * The umlObject from the metaModel which is displayed by this artifact.
	 */
	private UMLObject umlObject;

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
		this(canvas, id, "Object", "obj");
	}

	/**
	 * ObjectArtifact constructor, initializes the {@link NodeArtifact} with a name and without stereotype
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param instanceName
	 *            The instance name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param objectName
	 *            The name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 */
	public ObjectArtifact(final UMLCanvas canvas, int id, final String instanceName, final String objectName) {
		this(canvas, id, instanceName, objectName, "");
	}

	/**
	 * ObjectArtifact constructor, initializes all {@link NodePartArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param id
	 *            The artifacts's id
	 * @param instanceName
	 *            The instance name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param objectName
	 *            The name of the object, sent to {@link ObjectPartNameArtifact} constructor
	 * @param stereotype
	 *            The stereotype of the object, sent to {@link ObjectPartNameArtifact} constructor
	 */
	public ObjectArtifact(final UMLCanvas canvas, int id, final String instanceName, final String objectName, final String stereotype) {
		super(canvas, id);
		String stereotypeFormatted = stereotype.equals("") ? "" : "«" + stereotype + "»";
		umlObject = new UMLObject(instanceName, objectName, stereotypeFormatted);

		objectNameArtifact = new ObjectPartNameArtifact(canvas, umlObject);
		objectAttributesArtifact = new ObjectPartAttributesArtifact(canvas, umlObject.getObjectAttributes());
		nodeParts.add(objectNameArtifact);
		nodeParts.add(objectAttributesArtifact);
		objectNameArtifact.setNodeArtifact(this);
		objectAttributesArtifact.setNodeArtifact(this);
	}

	/**
	 * Add an {@link UMLObjectAttribute} to this object
	 * 
	 * @param attribute
	 *            The attribute, sent to {@link ObjectPartAttributesArtifact}
	 */
	public void addAttribute(final UMLObjectAttribute attribute) {
		objectAttributesArtifact.add(attribute);
	}

	/**
	 * Getter for the attributes
	 * 
	 * @return the list of attributes of this object
	 */
	public List<UMLObjectAttribute> getAttributes() {
		return objectAttributesArtifact.getList();
	}

	/**
	 * @return the objectNameArtifact
	 */
	ObjectPartNameArtifact getObjectNameArtifact() {
		return objectNameArtifact;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name of this object
	 */
	@Override
	public String getName() {
		return umlObject.getObjectName();
	}

	/**
	 * Get the metamodel uml component wrapped by the artifact.
	 * 
	 * @return the umlObject
	 */
	public UMLObject toUmlComponent() {
		return umlObject;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle objectNameRightMenu = objectNameArtifact.getRightMenu();
		final MenuBarAndTitle objectAttributesRightMenu = objectAttributesArtifact.getRightMenu();

		rightMenu.setName("Object " + getName());

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
		return "Object$" + this.getLocation() + "!" + objectNameArtifact.toURL() + "!" + objectAttributesArtifact.toURL();
	}

	@Override
	public void setUpAfterDeserialization() {
		objectAttributesArtifact.setUpAfterDeserialization();
		objectNameArtifact.setUpAfterDeserialization();
	}
}
