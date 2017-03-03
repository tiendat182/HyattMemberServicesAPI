package hyatt.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"name",
"logo"
})
public class Amenity implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonProperty("name")
	private String name;

	@JsonProperty("logo")
	private String logo;

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
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo
	 *            the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(logo).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Property) == false) {
			return false;
		}
		Amenity rhs = ((Amenity) other);
		return new EqualsBuilder().append(name, rhs.name).append(logo, rhs.logo).isEquals();
	}
}
