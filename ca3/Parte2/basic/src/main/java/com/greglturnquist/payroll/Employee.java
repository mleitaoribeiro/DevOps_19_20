/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity // <1>
public class Employee {

	private @Id @GeneratedValue Long id; // <2>
	private String firstName;
	private String lastName;
	private String description;
	private String jobTitle;
	private String email;

	private Employee() {}

	public Employee(String firstName, String lastName, String description, String jobTitle, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobTitle = jobTitle;
		setEmail(email);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) &&
			Objects.equals(firstName, employee.firstName) &&
			Objects.equals(lastName, employee.lastName) &&
			Objects.equals(description, employee.description) &&
			Objects.equals(jobTitle,employee.jobTitle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, description);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if (id != null && id != 0) {
			this.id = id;
		}
		else throw new IllegalArgumentException("The id is invalid.");
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName != null && firstName.length() != 0) {
			this.firstName = firstName;
		}
		else throw new IllegalArgumentException("The firstName is invalid.");
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName != null && lastName.length() != 0) {
			this.lastName = lastName;
		}
		else throw new IllegalArgumentException("The lastName is invalid.");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null && description.length() != 0) {
			this.description = description;
		}
		else throw new IllegalArgumentException("The description is invalid.");
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		if (jobTitle != null && jobTitle.length() != 0) {
			this.jobTitle = jobTitle;
		}
		else throw new IllegalArgumentException("The jobTitle is invalid.");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String at = "@";
		if (email != null && email.length() != 0 && email.contains(at)) {
			this.email = email;
		}
		else throw new IllegalArgumentException("The email is invalid.");
	}

	@Override
	public String toString() {
		return "Employee{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", description='" + description + '\'' +
			", jobTitle='" + jobTitle + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
// end::code[]
