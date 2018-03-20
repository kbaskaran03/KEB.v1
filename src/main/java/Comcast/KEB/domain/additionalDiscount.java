package Comcast.KEB.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="extra_disc")
public class additionalDiscount {
	@Id
	@Column(name="type")
		private String typeProduct;
	@Column(name="disc")
		private String discProduct;
	public String getTypeProduct() {
		return typeProduct;
	}
	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}
	public String getDiscProduct() {
		return discProduct;
	}
	public void setDiscProduct(String discProduct) {
		this.discProduct = discProduct;
	}
}
