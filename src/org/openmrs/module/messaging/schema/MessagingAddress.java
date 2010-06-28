package org.openmrs.module.messaging.schema;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;

/**
 * MessagingAddress objects are wrappers around a string that represents a place
 * that a message is sent to or from. All addresses must be representable using
 * less than 255 characters of plain-text ('address' is a varchar(255) in the
 * database). </br></br>When creating your own messaging services, you can
 * extend MessagingAddress to create your own address types or you can just use
 * this class as is.</br> </br> Creating MessagingAddress objects
 * should be done using a class that implements the {@link AddressFactory}
 * interface, which is responsible for making sure that addresses are properly
 * formatted. Any addresses created outside of the address factory are not
 * guaranteed to be valid.</br></br> Messaging Addresses are stored as
 * foreign-keyed person attributes, and the only address objects that are stored
 * in the database are addresses of people in the system.
 * 
 * @see PersonAttribute
 * @see AddressFactory
 */
public class MessagingAddress extends BaseOpenmrsData {

	public MessagingAddress(){}
	
	public MessagingAddress(String address, String password, Person person) {
		super();
		this.address = address;
		this.password = password;
		this.person = person;
	}

	public MessagingAddress(String address, Person person) {
		super();
		this.address = address;
		this.person = person;
	}

	protected Integer messagingAddressId;

	/**
	 * The plain text address
	 */
	protected String address;
	
	/**
	 * The password, if needed
	 */
	protected String password;
	
	/**
	 * The person this address is for
	 */
	protected Person person;
	
	/**
	 * Boolean that represents whether or not this is the preferred
	 * method of contact for a patient
	 */
	protected Boolean preferred = false;
	
	/**
	 * The protocol that is used to send messages to this address
	 */
	protected String protocolId;
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Integer getId() {
		return messagingAddressId;
	}

	public void setId(Integer id) {
		this.messagingAddressId = id;
	}

	/**
	 * @param preferred the preferred to set
	 */
	public void setPreferred(Boolean preferred) {
		this.preferred = preferred;
	}

	/**
	 * @return the preferred
	 */
	public Boolean getPreferred() {
		return preferred;
	}
	
	public boolean equals(MessagingAddress other){
		return this.messagingAddressId == other.getId();
	}

	/**
	 * @param protocolId the protocolId to set
	 */
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	/**
	 * @return the protocolId
	 */
	public String getProtocolId() {
		return protocolId;
	}
}
