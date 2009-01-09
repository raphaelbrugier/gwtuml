package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Path;
import com.objetdirect.tatami.client.gfx.Point;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

public class NoteArtifact extends BoxArtifact {

	protected static int TEXT_OFFSET = 25;
	protected static int TEXT_MARGIN = 8;
	
	public NoteArtifact() {
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void addDependency(NoteLinkArtifact dependency) {
		dependencies.add(dependency);
	}
	
	public float[] getOpaque() {
		float[] opaque = new float[] {
				getX()+TEXT_OFFSET-TEXT_MARGIN, getY(),
				getX(), getY()+TEXT_OFFSET-TEXT_MARGIN,
				getX(), getY()+getHeight(),
				getX()+getWidth(), getY()+getHeight(),
				getX()+getWidth(), getY()
		};
		return opaque;
	}
	
	protected GraphicObject buildGraphicObject() {
		vg = new VirtualGroup();
		contentText = new Text(content);
		contentText.translate(TEXT_OFFSET, TEXT_OFFSET);
		borderPath = getBorderPath();
		cornerPath = getCornerPath();
		vg.add(borderPath);
		vg.add(contentText);
		vg.add(cornerPath);
		return vg;
	}

	protected Path getBorderPath() {
		Path path = new Path();
		path.moveTo(new Point(TEXT_OFFSET-TEXT_MARGIN, 0));
		path.lineTo(new Point(getWidth(), 0));
		path.lineTo(new Point(getWidth(), getHeight()));
		path.lineTo(new Point(0, getHeight()));
		path.lineTo(new Point(0, TEXT_OFFSET-TEXT_MARGIN));
		path.lineTo(new Point(TEXT_OFFSET-TEXT_MARGIN, 0));
		path.setFillColor(Color.WHITE);
		path.setStroke(Color.BLACK, 1);
		return path;
	}
	
	protected Path getCornerPath() {
		Path path = new Path();
		path.moveTo(new Point(TEXT_OFFSET-TEXT_MARGIN, 0));
		path.lineTo(new Point(TEXT_OFFSET-TEXT_MARGIN, TEXT_OFFSET-TEXT_MARGIN));
		path.lineTo(new Point(0, TEXT_OFFSET-TEXT_MARGIN));
		path.lineTo(new Point(TEXT_OFFSET-TEXT_MARGIN, 0));
		path.setFillColor(Color.WHITE);
		path.setStroke(Color.BLACK, 1);
		return path;
	}

	public int getWidth() {
		return TEXT_OFFSET+(int)contentText.getWidth()/4*3+TEXT_MARGIN;
	}

	public int getHeight() {
		return TEXT_OFFSET+(int)contentText.getHeight()+TEXT_MARGIN;
	}

	public List<GraphicObject> getComponents() {
		List<GraphicObject> comps = new ArrayList<GraphicObject>();
		comps.add(contentText);
		comps.add(borderPath);
		comps.add(cornerPath);
		return comps;
	}
	
	public void select() {
		borderPath.setStroke(Color.BLUE, 2);
		cornerPath.setStroke(Color.BLUE, 2);
	}

	public void unselect() {
		borderPath.setStroke(Color.BLACK, 1);
		cornerPath.setStroke(Color.BLACK, 1);
	}
	
	public void adjusted() {
		super.adjusted();
		for (int i=0; i<dependencies.size(); i++) {
			NoteLinkArtifact elem = dependencies.get(i);
			elem.adjust();
		}
	}
	
	public Object getSubPart(GraphicObject o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	String content="";
	Text contentText = null;
	Path borderPath = null;
	Path cornerPath = null;
	List<NoteLinkArtifact> dependencies = new ArrayList<NoteLinkArtifact>();
	VirtualGroup vg;
}
