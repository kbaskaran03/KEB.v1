package Comcast.KEB.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.Scope;

@Entity
@Table(name="dealer")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@org.springframework.context.annotation.Scope(scopeName="request")
public class Dealer implements Serializable{
	@Id
	@Column(name="dist_id")
		Long dealerID;
	
	@Column(name="cust_name")
	String customerName;
	@Column(name="firstname")
	String firstName;
	@Column(name="lastname")
	String lastName;
	/*@ManyToOne(fetch=FetchType.LAZY)
*/	
	@ManyToOne
	@JoinColumn(name="country_id",referencedColumnName="country_id")
	Country countryID;
	
	public long getDealerID() {
		return dealerID;
	}
	public void setDealerID(long dealerID) {
		this.dealerID = dealerID;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Country getCountryID() {
		return countryID;
	}
	public void setCountryID(Country countryID) {
		this.countryID = countryID;
	}
	
	public String toString()
	{
		return dealerID+""+customerName;
	}
}
