package com.microservicios.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	
	@Column(name = "email")
	private String email;
	
	


	public User() {
		super();
	}


	public User(String nombre, String email) {
		super();
		this.nombre = nombre;
		this.email = email;
	}


 


	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	

}
