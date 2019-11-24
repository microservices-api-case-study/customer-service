# Customer Service (Core)

## Operations

1. Get customers – Return all customer details in the table
	 Get url: http://port/service1/customers

2.   Create a customer by sending in customer details.
	 Get url: http://port/service1/customer
	a. When  “create customer” method is invoked. Insert the details in the customer table and publish “CustomerCreated” event along with the customer details(customer id, email, firstname, last name).
	b. Sales order service has to subscribe to the “CustomerCreated” event. For further details look into Sales Order Service slide.

Table:
1. Customer – id, email, first_name, last_name


