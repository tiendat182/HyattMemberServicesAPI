package hyatt.api.model;

import java.util.List;

public class NodeProperty {
	private String path;
	private String nodename;
	private String text;
	private String value;
	private boolean hasChildren;
	private String primaryNodeType;
	private String identifier;
	private String image;
	private String date;
	private String checkbox;
	private List<NodeProperty> childNodes;

	/**
	 * @return the paths
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param paths
	 *            the paths to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodename() {
		return nodename;
	}

	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the hasChildren
	 */
	public boolean isHasChildren() {
		return hasChildren;
	}

	/**
	 * @param hasChildren
	 *            the hasChildren to set
	 */
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	/**
	 * @return the primaryNodeType
	 */
	public String getPrimaryNodeType() {
		return primaryNodeType;
	}

	/**
	 * @param primaryNodeType
	 *            the primaryNodeType to set
	 */
	public void setPrimaryNodeType(String primaryNodeType) {
		this.primaryNodeType = primaryNodeType;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the childNodes
	 */
	public List<NodeProperty> getChildNodes() {
		return childNodes;
	}

	/**
	 * @param childNodes
	 *            the childNodes to set
	 */
	public void setChildNodes(List<NodeProperty> childNodes) {
		this.childNodes = childNodes;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the checkbox
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox
	 *            the checkbox to set
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

}
