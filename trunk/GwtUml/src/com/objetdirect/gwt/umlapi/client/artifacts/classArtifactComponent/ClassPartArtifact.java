package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * @author  florian
 */
public abstract class ClassPartArtifact extends UMLArtifact {

	/**
	 * @uml.property  name="height"
	 */
	protected int height;
	/**
	 * @uml.property  name="width"
	 */
	protected int width;
	protected int classWidth;

	/**
	 * @param height
	 * @uml.property  name="height"
	 */
	public abstract void setHeight(int height);
	/**
	 * @param width
	 * @uml.property  name="width"
	 */
	public abstract void setWidth(int width);	
	/**
	 * @uml.property  name="textVirtualGroup"
	 * @uml.associationEnd  
	 */
	protected GfxObject textVirtualGroup;
	/**
	 * @uml.property  name="classArtifact"
	 * @uml.associationEnd  
	 */
	protected ClassArtifact classArtifact;
	
	public abstract void computeBounds();
	/**
	 * @param width
	 * @uml.property  name="classWidth"
	 */
	public abstract void setClassWidth(int width);
	
	public abstract void edit();
	
	/**
	 * @return
	 * @uml.property  name="classArtifact"
	 */
	public ClassArtifact getClassArtifact() {
		return classArtifact;
	}
	/**
	 * @param classArtifact
	 * @uml.property  name="classArtifact"
	 */
	public void setClassArtifact(ClassArtifact classArtifact) {
		this.classArtifact = classArtifact;
	}
}
