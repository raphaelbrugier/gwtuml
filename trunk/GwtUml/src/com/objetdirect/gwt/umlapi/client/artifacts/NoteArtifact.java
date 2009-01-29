package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;


public class NoteArtifact extends BoxArtifact {

	public final static int TEXT_XMARGIN = 8;
	public final static int TEXT_YMARGIN = 20;	
	public static final int DEFAULT_HEIGHT = 50;

	private NoteEditor editor;

	public NoteArtifact() {
		this.editor = new NoteEditor(this);
	}

	public void setContent(String content) {
		this.content = content;
		set(contentText, createNoteText());
	}	
	public String getContent() {
		return content;
	}

	public void addDependency(NoteLinkArtifact dependency) {
		dependencies.add(dependency);
	}

	public float[] getOpaque() {
		float[] opaque = new float[] {
				getX()									, getY()					,
				getX()									, getY() + getHeight()		,
				getX() + getWidth()						, getY() + getHeight()		,
				getX() + getWidth()						, getY() + getCornerHeight(),
				getX() + getWidth() - getCornerWidth()	, getY()					,
		};
		return opaque;
	}

	GfxObject createNoteText() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject contentText = gPlatform.buildText(content);
		gPlatform.setFont(contentText, font);
		gPlatform.setFillColor(contentText, ThemeManager.getForegroundColor());
		gPlatform.translate(contentText, TEXT_XMARGIN, TEXT_YMARGIN);
		return contentText;
	}

	protected GfxObject buildGfxObject() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();
		contentText[0]=createNoteText();
		borderPath = getBorderPath();
		cornerPath = getCornerPath();
		gPlatform.addToVirtualGroup(vg, borderPath);
		gPlatform.addToVirtualGroup(vg, contentText[0]);
		gPlatform.addToVirtualGroup(vg, cornerPath);
		return vg;
	}

	protected GfxObject getBorderPath() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject path = gPlatform.buildPath();
		gPlatform.moveTo(path, 0							, 0					);
		gPlatform.lineTo(path, getWidth() - getCornerWidth(), 0					);
		gPlatform.lineTo(path, getWidth()					, getCornerHeight()	);
		gPlatform.lineTo(path, getWidth()					, getHeight()		);
		gPlatform.lineTo(path, 0							, getHeight()		);
		gPlatform.lineTo(path, 0							, 0					);
		gPlatform.setFillColor(path, ThemeManager.getBackgroundColor());
		gPlatform.setStroke(path, ThemeManager.getForegroundColor(), 1);
		return path;
	}

	protected GfxObject getCornerPath() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject path = gPlatform.buildPath();
		gPlatform.moveTo(path, getWidth() - getCornerWidth(), 0					);
		gPlatform.lineTo(path, getWidth() - getCornerWidth(), getCornerHeight()	);
		gPlatform.lineTo(path, getWidth()					, getCornerHeight()	);		
		gPlatform.setFillColor(path, ThemeManager.getBackgroundColor());
		gPlatform.setStroke(path, ThemeManager.getForegroundColor(), 1);
		return path;
	}

	public int getWidth() {
		int width = TEXT_XMARGIN+(int)GfxManager.getInstance().getWidthFor(contentText[0])+TEXT_XMARGIN; 
		return width > DEFAULT_WIDTH ? width : DEFAULT_WIDTH;
	}

	public int getHeight() {
		int height = TEXT_YMARGIN+(int)GfxManager.getInstance().getHeightFor(contentText[0])+TEXT_YMARGIN;
		return height > DEFAULT_HEIGHT ? height : DEFAULT_HEIGHT;
	}

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(contentText[0]);
		comps.add(borderPath);
		comps.add(cornerPath);
		return comps;
	}

	public void select() {
		GfxManager.getInstance().setStroke(borderPath, ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getInstance().setStroke(cornerPath, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void unselect() {
		GfxManager.getInstance().setStroke(borderPath, ThemeManager.getForegroundColor(), 1);
		GfxManager.getInstance().setStroke(cornerPath, ThemeManager.getForegroundColor(), 1);
	}

	public void adjusted() {
		super.adjusted();
		for (int i=0; i<dependencies.size(); i++) {
			NoteLinkArtifact elem = dependencies.get(i);
			elem.adjust();
		}
	}

	public Object getSubPart(GfxObject o) {
		// TODO Auto-generated method stub
		return null;
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		editor.editContent();
	}

	@Override
	public GfxObject getOutline() {    	

		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();
		GfxObject outlineBorderPath = getBorderPath();
		GfxObject outlineCornerPath = getCornerPath();
		gPlatform.addToVirtualGroup(vg, outlineBorderPath);
		gPlatform.addToVirtualGroup(vg, outlineCornerPath);
		gPlatform.setStrokeStyle(outlineBorderPath, GfxStyle.DASH);
		gPlatform.setStrokeStyle(outlineCornerPath, GfxStyle.DASH);
		gPlatform.setStroke(outlineBorderPath, ThemeManager.getHighlightedForegroundColor(), 1);
		gPlatform.setStroke(outlineCornerPath, ThemeManager.getHighlightedForegroundColor(), 1);
		return vg;
	}

	private int getCornerHeight() {
		return getHeight() / 3;
	}

	private int getCornerWidth() {
		return getWidth() / 3;
	}

	String content="";
	GfxObject[] contentText  = new GfxObject[1];
	GfxObject borderPath = null;
	GfxObject cornerPath = null;
	List<NoteLinkArtifact> dependencies = new ArrayList<NoteLinkArtifact>();
	GfxObject vg;

	public LinkedHashMap <String, Command> getRightMenu() {
		
		LinkedHashMap <String, Command> rightMenu = new LinkedHashMap<String, Command>();
		
		Command doNothing = new Command() { 
			public void execute() {
			}

		};	
		rightMenu.put("Note", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Delete", doNothing);
		return rightMenu;
	}
}
