package hyatt.api.rcc.service;

import java.util.List;

import org.springframework.web.client.RestClientException;

import hyatt.api.model.AppKeyValue;
import hyatt.api.model.Errata;
import hyatt.api.model.ErrataRequest;
import hyatt.api.model.Property;

public interface RccService {

	/**
	 * Get all/specified Property data from RCC system.
	 * 
	 * @param propertyId Property ID
	 * @return List of CmsProperty objects.
	 */
	List<Property> listProperties(Integer propertyId) throws RestClientException;

	/**
	 * Get all Errata data from RCC system.
	 * 
	 * @param errataRequest ErrataRequest object
	 * @return A list of Errata objects
	 */
	List<Errata> listErratas(ErrataRequest errataRequest) throws RestClientException;
	
	/**
	 * Get all App Key Value pairs data from RCC system.
	 * 
	 * @return A list of AppKeyValue pairs
	 * @throws RestClientException
	 */
	List<AppKeyValue> listAppKeyValues() throws RestClientException;
}