package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class NoteArtifact extends BoxArtifact {

	public static final int DEFAULT_HEIGHT = 50;
	public final static int TEXT_XMARGIN = 8;
	public final static int TEXT_YMARGIN = 20;

	GfxObject borderPath = null;

	String content = "";

	GfxObject[] contentText = new GfxObject[1];

	GfxObject cornerPath = null;

	List<NoteLinkArtifact> dependencies = new ArrayList<NoteLinkArtifact>();

	GfxObject vg;

	private NoteEditor editor;

	public NoteArtifact() {
		this.editor = new NoteEditor(this);
	}

	public void addDependency(NoteLinkArtifact dependency) {
		dependencies.add(dependency);
	}

	@Override
	public void adjusted() {
		super.adjusted();
		for (int i = 0; i < dependencies.size(); i++) {
			NoteLinkArtifact elem = dependencies.get(i);
			elem.adjust();
		}
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		editor.editContent();
	}

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(contentText[0]);
		comps.add(borderPath);
		comps.add(cornerPath);
		return comps;
	}

	public String getContent() {
		return content;
	}

	@Override
	public int getHeight() {
		int height = TEXT_YMARGIN
				+ (int) GfxManager.getPlatform().getHeightFor(contentText[0])
				+ TEXT_YMARGIN;
		return height > DEFAULT_HEIGHT ? height : DEFAULT_HEIGHT;
	}

	@Override
	public float[] getOpaque() {
		float[] opaque = new float[] { getX(), getY(), getX(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getCornerHeight(),
				getX() + getWidth() - getCornerWidth(), getY(), };
		return opaque;
	}

	@Override
	public GfxObject getOutline() {
		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject outlineBorderPath = getBorderPath();
		GfxObject outlineCornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
		GfxManager.getPlatform().setStrokeStyle(outlineBorderPath,
				GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(outlineCornerPath,
				GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(outlineBorderPath,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(outlineCornerPath,
				ThemeManager.getHighlightedForegroundColor(), 1);
		return vg;
	}

	public LinkedHashMap<String, Command> getRightMenu() {

		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();

		Command doNothing = new Command() {
			public void execute() {
			}

		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Note", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}

	public Object getSubPart(GfxObject o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		int width = TEXT_XMARGIN
				+ (int) GfxManager.getPlatform().getWidthFor(contentText[0])
				+ TEXT_XMARGIN;
		return width > DEFAULT_WIDTH ? width : DEFAULT_WIDTH;
	}

	public void select() {
		GfxManager.getPlatform().setStroke(borderPath,
				ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(cornerPath,
				ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void setContent(String content) {
		this.content = content;
		set(contentText, createNoteText());
	}

	public void unselect() {
		GfxManager.getPlatform().setStroke(borderPath,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(cornerPath,
				ThemeManager.getForegroundColor(), 1);
	}
	GfxObject createNoteText() {

		GfxObject contentText = GfxManager.getPlatform().buildText(content);
		GfxManager.getPlatform().setFont(contentText, font);
		GfxManager.getPlatform().setFillColor(contentText,
				ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(contentText, TEXT_XMARGIN,
				TEXT_YMARGIN);
		return contentText;
	}
	@Override
	protected GfxObject buildGfxObject() {

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		contentText[0] = createNoteText();
		borderPath = getBorderPath();
		cornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(vg, borderPath);
		GfxManager.getPlatform().addToVirtualGroup(vg, contentText[0]);
		GfxManager.getPlatform().addToVirtualGroup(vg, cornerPath);
		return vg;
	}
	protected GfxObject getBorderPath() {

		GfxObject path = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(path, 0, 0);
		GfxManager.getPlatform().lineTo(path, getWidth() - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(path, getWidth(), getCornerHeight());
		GfxManager.getPlatform().lineTo(path, getWidth(), getHeight());
		GfxManager.getPlatform().lineTo(path, 0, getHeight());
		GfxManager.getPlatform().lineTo(path, 0, 0);
		GfxManager.getPlatform().setFillColor(path,
				ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		return path;
	}
	protected GfxObject getCornerPath() {

		GfxObject path = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(path, getWidth() - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(path, getWidth() - getCornerWidth(),
				getCornerHeight());
		GfxManager.getPlatform().lineTo(path, getWidth(), getCornerHeight());
		GfxManager.getPlatform().setFillColor(path,
				ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		return path;
	}
	private int getCornerHeight() {
		return getHeight() / 3;
	}

	private int getCornerWidth() {
		return getWidth() / 3;
	}
}
