package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
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
public class ObjectArtifact extends NodeArtifact {
    ObjectPartAttributesArtifact classAttributes;
    ObjectPartNameArtifact className;

    /**
     * Default constructor, initializes the ClassArtifact with the name "Class"
     */
    public ObjectArtifact() {
	this("Object");
    }
    
    /**
     * ClassArtifact constructor, initializes the {@link NodeArtifact} with a name and without stereotype
     * 
     * @param className The name of the class, sent to {@link ClassPartNameArtifact} constructor
     */
    public ObjectArtifact(final String className) {
	this(className, "");
    }
    /**
     * ClassArtifact constructor, initializes all {@link NodePartArtifact}
     * 
     * @param className The name of the class, sent to {@link ClassPartNameArtifact} constructor
     * @param stereotype The stereotype of the class, sent to {@link ClassPartNameArtifact} constructor
     */
    public ObjectArtifact(final String className, final String stereotype) {
	this.className = new ObjectPartNameArtifact(className, stereotype);
	this.classAttributes = new ObjectPartAttributesArtifact();
	this.nodeParts.add(this.className);
	this.nodeParts.add(this.classAttributes);
	this.className.setNodeArtifact(this);
	this.classAttributes.setNodeArtifact(this);
    }

    /**
     * Add an {@link Attribute} to this class
     * @param attribute The attribute, sent to {@link ClassPartAttributesArtifact}
     */
    public void addAttribute(final Attribute attribute) {
	this.classAttributes.add(attribute);
    }

    /**
     * Getter for the attributes
     * 
     * @return the list of attributes of this class
     */
    public List<Attribute> getAttributes() {
	return this.classAttributes.getList();
    }

    /**
     * Getter for the name
     * 
     * @return the name of this class
     */
    public String getClassName() {
	return this.className.getClassName();
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	final MenuBarAndTitle classNameRightMenu = this.className.getRightMenu();
	final MenuBarAndTitle classAttributesRightMenu = this.classAttributes
		.getRightMenu();

	rightMenu.setName("Object " + this.className.getClassName());

	rightMenu.addItem(classNameRightMenu.getName(), classNameRightMenu
		.getSubMenu());
	rightMenu.addItem(classAttributesRightMenu.getName(),
		classAttributesRightMenu.getSubMenu());


	return rightMenu;
    }
}
