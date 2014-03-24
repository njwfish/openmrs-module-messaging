/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.messaging.util.MessagingUtils;

/**
 * This class contains the logic that is run every time this module
 * is either started or shutdown
 */
public class MessagingModuleActivator extends BaseModuleActivator {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * A boolean used to protect against multiple started() calls
	 */
	private boolean startedCalled = false;
	
	public void started() {
		log.info("Started Messaging Module");
		if(!startedCalled){ 
			MessagingUtils.createGatewayManagerTask();
			MessagingUtils.createPatientAttributes();
			startedCalled = true;
		}
	}

	public void willStop() {
	}
}