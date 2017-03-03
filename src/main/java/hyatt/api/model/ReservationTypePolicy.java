package hyatt.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"number_of_nights_allowed",
"reservation_type"
})
public class ReservationTypePolicy implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("reservation_type")
	private String reservationType;
	
	@JsonProperty("number_of_nights_allowed")
	private List<Object> numberOfNightsAllowed = new ArrayList<Object>();

	/**
	 * 
	 * @return The reservationType
	 */
	public String getReservationType() {
		return reservationType;
	}

	/**
	 * 
	 * @param reservationType
	 *            The reservation_type
	 */
	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	/**
	 * 
	 * @return The numberOfNightsAllowed
	 */
	public List<Object> getNumberOfNightsAllowed() {
		return numberOfNightsAllowed;
	}

	/**
	 * 
	 * @param numberOfNightsAllowed
	 *            The number_of_nights_allowed
	 */
	public void setNumberOfNightsAllowed(List<Object> numberOfNightsAllowed) {
		this.numberOfNightsAllowed = numberOfNightsAllowed;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(reservationType).append(numberOfNightsAllowed).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ReservationTypePolicy) == false) {
			return false;
		}
		ReservationTypePolicy rhs = ((ReservationTypePolicy) other);
		return new EqualsBuilder().append(reservationType, rhs.reservationType).append(numberOfNightsAllowed, rhs.numberOfNightsAllowed)
				.isEquals();
	}
}
