package hyatt.api.model;

import java.util.HashMap;

public class JcrQueryModel {
	private String name;
	private String type;
	private String path;
	private String id;
	private HashMap<String, Object> properties;

	private HashMap<String, BaseJcrModel> children;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the children
	 */
	public HashMap<String, BaseJcrModel> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(HashMap<String, BaseJcrModel> children) {
		this.children = children;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the properties
	 */
	public HashMap<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}

}
