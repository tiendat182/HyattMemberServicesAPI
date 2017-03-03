package hyatt.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO object used to build Jahia CMS's SQL-2 query content.
 *
 */
public class CmsSql2QueryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("query")
	private String query;

	@JsonProperty("limit")
	private Integer limit;

	@JsonProperty("offset")
	private Integer offset;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(query).append(limit).append(offset).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof CmsSql2QueryVO) == false) {
			return false;
		}
		CmsSql2QueryVO rhs = ((CmsSql2QueryVO) other);
		return new EqualsBuilder().append(query, rhs.query).append(limit, rhs.limit).append(offset, rhs.offset).isEquals();
	}
}
