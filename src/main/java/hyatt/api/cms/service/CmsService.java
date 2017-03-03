package hyatt.api.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import hyatt.api.model.Errata;
import hyatt.api.model.NodeProperty;
import hyatt.api.model.Property;

public interface CmsService {
	/**
	 * Get all/specified Property data from CMS system based on the specified
	 * Property IDs/language from RCC system.
	 * 
	 * @param propertyIds
	 *            List of Property IDs from RCC
	 * @param languageCd
	 *            Requested language code
	 * @return A Map of CmsProperty objects.
	 */
	Map<Integer, Property> listProperties(List<Integer> propertyIds, String languageCd) throws RestClientException;

	/**
	 * Get all Errata data from CMS system based on the specified Errata
	 * ID/language from RCC system.
	 * 
	 * @param errataIds
	 *            List of Errata IDs from RCC
	 * @param languageCd
	 *            Requested language code
	 * 
	 * @return A Map of Errata objects
	 */
	Map<Integer, Errata> listErratas(List<Integer> errataIds, String languageCd) throws RestClientException;

	/**
	 * Get all Dictionaries of CMS system(e.g Home page).
	 * 
	 * @param languageCd
	 * @return A model of NodeProperty
	 */
	NodeProperty getNodeProperty(String languageCd);

	/**
	 * Process the first node of Dictionary Data.(NodeProperty Structure)
	 * 
	 * @param nodeProperty
	 * @param dictionaryModel
	 * @return A list of HashMap that contains all data of a static content
	 *         page( e.g. Home page)
	 */
	List<HashMap<String, Object>> processFirstNode(NodeProperty nodeProperty,
			List<HashMap<String, Object>> dictionaryModel, String languageCd);

	/**
	 * Building a navigator content of header, footer.
	 * 
	 * @param navigatorName
	 * @param languageCd
	 * @return A list of HashMap navigator data
	 */
	List<HashMap<String, Object>> navigatorBuilder(String navigatorName, String languageCd);
	
	/**
	 * Get all/specified Node data from Jahia CMS system based on the specified Node IDs/language.</br>
	 * For example: hyatt:deal, hyatt:events, hyatt:news, etc.
	 * 
	 * @param nodeId Node ID
	 * @param languageCd Requested language code
	 * @param nodeType Node type for SQL-2 query
	 * @param whereCondition Where condition for SQL-2 query
	 * @return List of Node data map
	 * @throws RestClientException
	 */
	List<Map<String, Object>> listDataByNodeType(Integer nodeId, String languageCd, String nodeType, String whereCondition)
			throws RestClientException;
	
	/**
	 * Get detailed error message by its code and language. 
	 * @param errorCd Error code
	 * @param languageCd Language code
	 * @return Detailed message in selected language.
	 */
	String listErrorMessageByCode(Integer errorCd, String languageCd);
}