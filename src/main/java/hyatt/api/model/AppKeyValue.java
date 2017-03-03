package hyatt.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "label", "value", "data_type" })
public class AppKeyValue {

	@JsonProperty("label")
	private String label;
	@JsonProperty("value")
	private String value;
	@JsonProperty("data_type")
	private String dataType;

	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}

	@JsonProperty("data_type")
	public String getDataType() {
		return dataType;
	}

	@JsonProperty("data_type")
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(label).append(value).append(dataType).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof AppKeyValue) == false) {
			return false;
		}
		AppKeyValue rhs = ((AppKeyValue) other);
		return new EqualsBuilder().append(label, rhs.label).append(value, rhs.value).append(dataType, rhs.dataType).isEquals();
	}
}