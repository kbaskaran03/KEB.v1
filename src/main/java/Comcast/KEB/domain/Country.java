package Comcast.KEB.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Entity
@Table(name="country")

public class Country {
@Id
	@Column(name="country_id")
		String countryId;
	
	@Column(name="country_name")
	String countryName;
	@Column(name="isd_code")
	String isdCode;
	@Column(name="currency_id")
	String currencyId;
	@Column(name="currency_name")
	String currencyName;
	@Column(name="currency_symbol")
	String currencySymbol;
	@Column(name="ip_address")
	String ipAddress;
	@Column(name="user_id")
	String userId;
	@Column(name="enter_dt_tm")
	Timestamp enter_dt_tm;
	@Column(name="updt_dt_tm")	
	Timestamp updt_dt_tm;
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getIsdCode() {
		return isdCode;
	}
	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getEnter_dt_tm() {
		return enter_dt_tm;
	}
	public void setEnter_dt_tm(Timestamp enter_dt_tm) {
		this.enter_dt_tm = enter_dt_tm;
	}
	public Timestamp getUpdt_dt_tm() {
		return updt_dt_tm;
	}
	public void setUpdt_dt_tm(Timestamp updt_dt_tm) {
		this.updt_dt_tm = updt_dt_tm;
	}

	public String toJSONString() throws JsonProcessingException
	{
ObjectWriter writer= new ObjectMapper().writer();
return writer.writeValueAsString(this);
	}

}
