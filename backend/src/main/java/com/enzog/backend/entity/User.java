package com.enzog.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name= "app_user")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	@NotBlank(message = "You must enter a valid name")
	private String name;
	
	@Column(name = "firstname")
	@NotBlank(message = "You must enter a valid firstname")
	private String firstname;
	
	@Column(name = "email")
	@Email(message = "Email must be valid")
	@NotBlank(message = "You must enter an email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "user_type_id", nullable = false)
	private UserType userType;
	
	
	public User() {}
	
	public User(String name, String firstname, String email, UserType userType) {
		this.name = name;
		this.firstname = firstname;
		this.email = email;
		
		this.userType = userType;
	}

	/* Getters & Setters */
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstname=" + firstname + ", email=" + email + ", userType="
				+ userType + "]";
	}
}
