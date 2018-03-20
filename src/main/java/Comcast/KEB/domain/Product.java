package Comcast.KEB.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
@Id
@Column(name="prodtype_id")
	private String prodtypeID;
@Column(name="type")
	private String type;
@Column(name="discount")
	private String discount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProdtypeID() {
	return prodtypeID;
}

public void setProdtypeID(String prodtypeID) {
	this.prodtypeID = prodtypeID;
}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
