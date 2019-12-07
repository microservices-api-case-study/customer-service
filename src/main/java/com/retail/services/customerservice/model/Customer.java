package com.retail.services.customerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(hidden=true)
	private Long id;

	@ApiModelProperty(example="abc@def.com")
	@NotNull(message = "Email is required")
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Email is not valid")
	@Column(name = "email")
	private String email;

	@ApiModelProperty(example="John")
	@NotNull(message = "First name is required")
	@NotBlank(message = "First name cannot be empty")
	@Column(name = "first_name")
	private String firstName;

	@ApiModelProperty(example="Smith")
	@NotNull(message = "Last name is required")
	@NotBlank(message = "Last Name cannot be empty")
	@Column(name = "last_name")
	private String lastName;

	public Customer() {
	}

	public Customer(Long id, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=");
		builder.append(id);
		builder.append(", email=");
		builder.append(email);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append("]");
		return builder.toString();
	}

}
