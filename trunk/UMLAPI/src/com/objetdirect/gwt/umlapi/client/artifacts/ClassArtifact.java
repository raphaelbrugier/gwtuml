/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;

/**
 * This class is an artifact used to represent a class. <br>
 * A class is divided in three {@link NodePartArtifact} : <ul>
 * <li>{@link ClassPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ClassPartAttributesArtifact} For the attribute list part</li>
 * <li>{@link ClassPartMethodsArtifact} For the method list part</li>
 * </ul>
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassArtifact extends NodeArtifact {
    ClassPartAttributesArtifact classAttributes;
    ClassPartMethodsArtifact classMethods;
    ClassPartNameArtifact className;

    /**
     * Default constructor, initializes the ClassArtifact with the name "Class"
     */
    public ClassArtifact() {
	this("Class");
    }
    
    /**
     * ClassArtifact constructor, initializes the {@link NodeArtifact} with a name and without stereotype
     * 
     * @param className The name of the class, sent to {@link ClassPartNameArtifact} constructor
     */
    public ClassArtifact(final String className) {
	this(className, "");
    }
    /**
     * ClassArtifact constructor, initializes all {@link NodePartArtifact}
     * 
     * @param className The name of the class, sent to {@link ClassPartNameArtifact} constructor
     * @param stereotype The stereotype of the class, sent to {@link ClassPartNameArtifact} constructor
     */
    public ClassArtifact(final String className, final String stereotype) {
	super();
	this.className = new ClassPartNameArtifact(className, stereotype);
	this.classAttributes = new ClassPartAttributesArtifact();
	this.classMethods = new ClassPartMethodsArtifact();	
	this.nodeParts.add(this.className);
	this.nodeParts.add(this.classAttributes);
	this.nodeParts.add(this.classMethods);
	this.className.setNodeArtifact(this);
	this.classAttributes.setNodeArtifact(this);
	this.classMethods.setNodeArtifact(this);
    }

    /**
     * Add an {@link UMLClassAttribute} to this class
     * @param attribute The attribute, sent to {@link ClassPartAttributesArtifact}
     */
    public void addAttribute(final UMLClassAttribute attribute) {
	this.classAttributes.add(attribute);
    }
    /**
     * Add a {@link UMLClassMethod} to this class
     * @param method The method, sent to {@link ClassPartMethodsArtifact}
     */

    public void addMethod(final UMLClassMethod method) {
	this.classMethods.add(method);
    }

    /**
     * Getter for the attributes
     * 
     * @return the list of attributes of this class
     */
    public List<UMLClassAttribute> getAttributes() {
	return this.classAttributes.getList();
    }

    /**
     * Getter for the name
     * 
     * @return the name of this class
     */
    @Override
    public String getName() {
	return this.className.getClassName();
    }
    /**
     * Getter for the methods
     * 
     * @return the list of methods of this class
     */
    public List<UMLClassMethod> getMethods() {
	return this.classMethods.getList();
    }
    
    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	final MenuBarAndTitle classNameRightMenu = this.className.getRightMenu();
	final MenuBarAndTitle classAttributesRightMenu = this.classAttributes
		.getRightMenu();
	final MenuBarAndTitle classMethodsRightMenu = this.classMethods
		.getRightMenu();

	rightMenu.setName("Class" + this.className.getClassName());

	rightMenu.addItem(classNameRightMenu.getName(), classNameRightMenu
		.getSubMenu());
	rightMenu.addItem(classAttributesRightMenu.getName(),
		classAttributesRightMenu.getSubMenu());
	rightMenu.addItem(classMethodsRightMenu.getName(),
		classMethodsRightMenu.getSubMenu());

	return rightMenu;
    }


    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "Class$" + this.getLocation() + "!" + this.className.toURL() + "!" + this.classAttributes.toURL() + "!" + this.classMethods.toURL();
	
    }
}
