package hyatt.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Errata implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("errata_id")
	private Integer errataId;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("property_id")
	private Integer propertyId;
	
	@JsonProperty("start_date")
	private String startDate;
	
	@JsonProperty("end_date")
	private String endDate;

	/**
	 * 
	 * @return The errataId
	 */
	public Integer getErrataId() {
		return errataId;
	}

	/**
	 * 
	 * @param errataId
	 *            The errata_id
	 */
	public void setErrataId(Integer errataId) {
		this.errataId = errataId;
	}

	/**
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return The startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * @param startDate
	 *            The start_date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return The endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate
	 *            The end_date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}