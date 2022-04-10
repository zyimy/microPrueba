package com.microservicios.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name = "tbl_bike")
public class Bike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "model")
	private String model;
	
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name="userId")
	private int userId;
	
	


	public Bike() {
		super();
	}


	public Bike(String model, String brand,int userId) {
		
		this.model = model;
		this.brand = brand;
		this.userId= userId;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Override
	public String toString() {
		return "Bike [id=" + id + ", model=" + model + ", brand=" + brand + ", userId=" + userId + "]";
	}


  
	
	

}
