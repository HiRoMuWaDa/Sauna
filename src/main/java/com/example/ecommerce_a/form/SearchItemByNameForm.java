package com.example.ecommerce_a.form;

/**
 * @author cyjoh
 *
 */
public class SearchItemByNameForm {

	private String searchingName;
	
	private Integer sort;
	
	
	public SearchItemByNameForm(String searchingName, Integer sort) {
		this.searchingName = searchingName;
		this.sort = sort;
	}

	public String getSearchingName() {
		return searchingName;
	}

	public void setSearchingName(String searchingName) {
		this.searchingName = searchingName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "SearchItemByNameForm [searchingName=" + searchingName + ", sort=" + sort + "]";
	}
	


}
