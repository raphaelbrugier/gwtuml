package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
/**
 * @author  florian
 */
public abstract class ClassPartArtifact extends UMLArtifact {
	 
	protected int height;
	 
	protected int width;
	protected int classWidth;
 
	public abstract void setHeight(int height);
 
	public abstract void setWidth(int width);	
	protected GfxObject textVirtualGroup;
    protected ClassArtifact classArtifact;
	
	public abstract void computeBounds();
 
	public abstract void setClassWidth(int width);
	
	public abstract void edit();
	
	@Override
	public void buildGfxObjectWithAnimation() {
	    buildGfxObject();
	}
	public ClassArtifact getClassArtifact() {
		return classArtifact;
	}
 
	public void setClassArtifact(ClassArtifact classArtifact) {
		this.classArtifact = classArtifact;
	}
}
