package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;

/**
 * This object is an artifact used to represent an object / instance of a object. <br>
 * A object is divided in three {@link NodePartArtifact} : <ul>
 * <li>{@link ObjectPartNameArtifact} For the name and stereotype part</li>
 * <li>{@link ObjectPartAttributesArtifact} For the attribute list part</li>
 * </ul>
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ObjectArtifact extends NodeArtifact {
    ObjectPartAttributesArtifact Attributes;
    ObjectPartNameArtifact objectName;

    /**
     * Default constructor, initializes the ObjectArtifact with the name "Object"
     */
    public ObjectArtifact() {
	this("Object");
    }
    
    /**
     * ObjectArtifact constructor, initializes the {@link NodeArtifact} with a name and without stereotype
     * 
     * @param objectName The name of the object, sent to {@link ObjectPartNameArtifact} constructor
     */
    public ObjectArtifact(final String objectName) {
	this(objectName, "");
    }
    /**
     * ObjectArtifact constructor, initializes all {@link NodePartArtifact}
     * 
     * @param objectName The name of the object, sent to {@link ObjectPartNameArtifact} constructor
     * @param stereotype The stereotype of the object, sent to {@link ObjectPartNameArtifact} constructor
     */
    public ObjectArtifact(final String objectName, final String stereotype) {
	this.objectName = new ObjectPartNameArtifact(objectName, stereotype);
	this.Attributes = new ObjectPartAttributesArtifact();
	this.nodeParts.add(this.objectName);
	this.nodeParts.add(this.Attributes);
	this.objectName.setNodeArtifact(this);
	this.Attributes.setNodeArtifact(this);
    }

    /**
     * Add an {@link UMLObjectAttribute} to this object
     * @param attribute The attribute, sent to {@link ObjectPartAttributesArtifact}
     */
    public void addAttribute(final UMLObjectAttribute attribute) {
	this.Attributes.add(attribute);
    }

    /**
     * Getter for the attributes
     * 
     * @return the list of attributes of this object
     */
    public List<UMLObjectAttribute> getAttributes() {
	return this.Attributes.getList();
    }

    /**
     * Getter for the name
     * 
     * @return the name of this object
     */
    @Override
    public String getName() {
	return this.objectName.getObjectName();
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	final MenuBarAndTitle objectNameRightMenu = this.objectName.getRightMenu();
	final MenuBarAndTitle objectAttributesRightMenu = this.Attributes
		.getRightMenu();

	rightMenu.setName("Object " + this.objectName.getObjectName());

	rightMenu.addItem(objectNameRightMenu.getName(), objectNameRightMenu
		.getSubMenu());
	rightMenu.addItem(objectAttributesRightMenu.getName(),
		objectAttributesRightMenu.getSubMenu());


	return rightMenu;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#fromURL(java.lang.String)
     */
    @Override
    public void fromURL(String url) {
	// TODO Auto-generated method stub
	
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return this.getClass().getName() + ":" + this.objectName.getObjectName() + "," + this.getLocation();
    }
}
