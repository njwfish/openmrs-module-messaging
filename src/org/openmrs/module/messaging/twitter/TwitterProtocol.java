package org.openmrs.module.messaging.twitter;

import org.openmrs.Person;
import org.openmrs.module.messaging.schema.AddressFormattingException;
import org.openmrs.module.messaging.schema.Message;
import org.openmrs.module.messaging.schema.MessageFormattingException;
import org.openmrs.module.messaging.schema.MessagingAddress;
import org.openmrs.module.messaging.schema.Protocol;

import winterwell.jtwitter.Twitter;

/**
 * A protocol for sending Twitter messages
 * @author dieterichlawson
 *
 */
public class TwitterProtocol extends Protocol{

	private Twitter twitter;
	
	public TwitterProtocol(){
		twitter = new Twitter();
	}
	
	@Override
	public String getProtocolId() {
		return "twitter";
	}
	
	@Override
	public String getProtocolName() {
		return "Twitter";
	}
	
	/**
	 * Limits:
	 * - alphanumeric + underscores
	 * - 15 characters max, 1 character minimum
	 * 
	 * @see org.openmrs.module.messaging.schema.Protocol#addressContentIsValid(java.lang.String)
	 */
	@Override
	public boolean addressIsValid(String address) {
		return !address.matches("[^A-Za-z0-9_]") && address.length() <=15 && address.length() >0;
	}

	/**
	 * Limits:
	 * - alphanumeric + underscores
	 * - 15 chars max, 1 char min
	 * 
	 * @see org.openmrs.module.messaging.schema.Protocol#createAddress(java.lang.String, org.openmrs.Person)
	 */
	@Override
	public MessagingAddress createAddress(String address, Person person) throws AddressFormattingException {
		if(address == null){
			return null;
		}
		if(address.matches("[^A-Za-z0-9_]")){
			throw new AddressFormattingException("Username contains characters other than letters, numbers, and underscores");
		}else if(address.length() > 15){
			throw new AddressFormattingException("Username is longer than the 15 character limit");
		}else if(address.length() < 1){
			throw new AddressFormattingException("Username is blank");
		}else{
			return new MessagingAddress(address,person);
		}
	}

	/**
	 * Limits:
	 * - all address limits
	 * - 140 character tweet limit
	 * @see org.openmrs.module.messaging.schema.Protocol#createMessage(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Message createMessage(String messageContent, String toAddress, String fromAddress) throws MessageFormattingException,AddressFormattingException{
		try{
			MessagingAddress to = createAddress(toAddress,null);
		}catch(AddressFormattingException e){
			e.setDescription(e.getDescription().replace("Username", "To-address"));
			throw e;
		}
		try{
			MessagingAddress from = createAddress(fromAddress,null);
		}catch(AddressFormattingException e){
			e.setDescription(e.getDescription().replace("Username", "From-address"));
			throw e;
		}
		
		if(!messageContentIsValid(messageContent)){
			throw new MessageFormattingException("Tweet is longer than 140 characters");
		}
		
		return new Message(toAddress,fromAddress,messageContent);
	}


	/**
	 * Only 140 character messages are allowed
	 * @see org.openmrs.module.messaging.schema.Protocol#messageContentIsValid(java.lang.String)
	 */
	@Override
	public boolean messageContentIsValid(String content) {
		return content.length() <=140;
	}
	
	/**
	 * Returns true if this user exists in the Twitter system.
	 * This method accesses the internet, and there will be a short delay or
	 * failures due to twitter connectivity
	 * @param username
	 * @return Whether or not this user exists
	 */
	public boolean usernameExists(String username){
		return twitter.userExists(username);
	}
	
	/**
	 * Returns true if the supplied username and password can
	 * be used to log in to Twitter. This method accesses the internet,
	 * so there will be a short delay. Additionally, failures are possible
	 * if Twitter is down or something else is not working.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean isValidLogin(String username, String password){
		twitter = new Twitter(username, password);
		boolean result = twitter.isValidLogin();
		twitter = new Twitter();
		return result;
	}

	@Override
	public Message createMessage(String messageContent) throws MessageFormattingException {
		if(!messageContentIsValid(messageContent)){
			throw new MessageFormattingException("Tweet is longer than 140 characters");
		}
		
		return new Message(null,null,messageContent);
	}

}
