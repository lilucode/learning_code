package cn.com.agree.ab.a5.runtime.lfc;

public class ArgComponent extends ArgElement {

	private String caption;
	private String editor = "";
	private String required = "";
	private String contains = "";

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getContains() {
		return contains;
	}

	public void setContains(String contains) {
		this.contains = contains;
	}

}
