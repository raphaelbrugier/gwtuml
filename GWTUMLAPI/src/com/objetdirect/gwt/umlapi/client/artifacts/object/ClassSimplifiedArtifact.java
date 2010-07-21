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
package com.objetdirect.gwt.umlapi.client.artifacts.object;

import com.objetdirect.gwt.umlapi.client.artifacts.NodeArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This class is an artifact used to represent a class in the object diagram.<br>
 * It's a simplified version of the class artifact used in the class diagram. A class is divided in three
 * {@link NodePartArtifact} :
 * <ul>
 * <li>{@link ClassPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ClassPartAttributesArtifact} For the attribute list part</li>
 * <li>{@link ClassPartMethodsArtifact} For the method list part</li>
 * </ul>
 * 
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class ClassSimplifiedArtifact extends NodeArtifact {

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	ClassPartNameSimplifiedArtifact className;

	private UMLClass ownedClass;

	/** Default constructor ONLY for GWT-RPC serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ClassSimplifiedArtifact() {
	}

	/**
	 * ClassArtifact constructor, initializes all {@link NodePartArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param className
	 *            The name of the class, sent to {@link ClassPartNameArtifact} constructor
	 * @param stereotype
	 *            The stereotype of the class, sent to {@link ClassPartNameArtifact} constructor
	 */
	public ClassSimplifiedArtifact(final UMLCanvas canvas, int id, final String className) {
		super(canvas, id);
		ownedClass = new UMLClass(className);
		this.className = new ClassPartNameSimplifiedArtifact(canvas, ownedClass);
		nodeParts.add(this.className);
		this.className.setNodeArtifact(this);
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name of this class
	 */
	@Override
	public String getName() {
		return className.getClassName();
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle classNameRightMenu = className.getRightMenu();

		rightMenu.setName("Class" + className.getClassName());

		rightMenu.addItem(classNameRightMenu.getName(), classNameRightMenu.getSubMenu());

		return rightMenu;
	}

	public UMLClass toUMLComponent() {
		return ownedClass;
	}

	@Override
	public String toURL() {
		return "Class$" + this.getLocation() + "!" + className.toURL();

	}

	@Override
	public void setUpAfterDeserialization() {
		className.setUpAfterDeserialization();
	}
}
