package hyatt.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"ada_option",
"ada_unit",
"property_id",
"type",
"unit_number"
})
public class AvailableUnitNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("ada_option")
	private String adaOption;
	@JsonProperty("ada_unit")
	private Boolean adaUnit;
	@JsonProperty("property_id")
	private Integer propertyId;
	@JsonProperty("type")
	private String type;
	@JsonProperty("unit_number")
	private String unitNumber;

	/**
	 * 
	 * @return The adaOption
	 */
	public String getAdaOption() {
		return adaOption;
	}

	/**
	 * 
	 * @param adaOption
	 *            The ada_option
	 */
	public void setAdaOption(String adaOption) {
		this.adaOption = adaOption;
	}

	/**
	 * 
	 * @return The adaUnit
	 */
	public Boolean getAdaUnit() {
		return adaUnit;
	}

	/**
	 * 
	 * @param adaUnit
	 *            The ada_unit
	 */
	public void setAdaUnit(Boolean adaUnit) {
		this.adaUnit = adaUnit;
	}

	/**
	 * 
	 * @return The propertyId
	 */
	public Integer getPropertyId() {
		return propertyId;
	}

	/**
	 * 
	 * @param propertyId
	 *            The property_id
	 */
	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * 
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The unitNumber
	 */
	public String getUnitNumber() {
		return unitNumber;
	}

	/**
	 * 
	 * @param unitNumber
	 *            The unit_number
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(adaOption).append(adaUnit).append(propertyId).append(type).append(unitNumber).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof AvailableUnitNumber) == false) {
			return false;
		}
		AvailableUnitNumber rhs = ((AvailableUnitNumber) other);
		return new EqualsBuilder().append(adaOption, rhs.adaOption).append(adaUnit, rhs.adaUnit).append(propertyId, rhs.propertyId)
				.append(type, rhs.type).append(unitNumber, rhs.unitNumber).isEquals();
	}
}
