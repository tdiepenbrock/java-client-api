/*
 * Copyright 2012-2013 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.client.admin;

import com.marklogic.client.util.RequestLogger;
import com.marklogic.client.util.RequestParameters;
import com.marklogic.client.io.marker.StructureReadHandle;
import com.marklogic.client.io.marker.TextReadHandle;
import com.marklogic.client.io.marker.TextWriteHandle;

/**
 * A Resource Extensions Manager supports writing, reading, and deleting
 * a Resource Services extension as well as listing the installed
 * Resource Services extensions.  A Resource Services extension implements
 * server operations on a kind of database resource not supported
 * by default.
 */
public interface ResourceExtensionsManager {
	/**
	 * Reads the list of resource service extensions installed on the server.
	 * @param listHandle	a handle on a JSON or XML representation of the list
	 * @return	the list handle
	 */
    public <T extends StructureReadHandle> T listServices(T listHandle);

    /**
     * Reads the XQuery implementation of the services for a resource.
     * @param resourceName	the name of the resource
     * @param sourceHandle	a handle for reading the text of the XQuery implementation.
     * @return	the XQuery source code
     */
    public <T extends TextReadHandle> T readServices(String resourceName, T sourceHandle);

    /**
     * Installs the services that implement a resource.
     * @param resourceName	the name of the resource
     * @param sourceHandle	a handle on the source for the XQuery implementation
     * @param metadata	the metadata about the resource services
     * @param methodParams	a declaration of the parameters for the services
     */
    public void writeServices(String resourceName, TextWriteHandle sourceHandle, ExtensionMetadata metadata, MethodParameters... methodParams);

    /**
     * Uninstalls the services that implement a resource.
     * @param resourceName	the name of the resource
     */
    public void deleteServices(String resourceName);

    /**
     * Starts debugging client requests. You can suspend and resume debugging output
     * using the methods of the logger.
     * 
     * @param logger	the logger that receives debugging output
     */
    public void startLogging(RequestLogger logger);
    /**
     *  Stops debugging client requests.
     */
    public void stopLogging();

    /**
     * Method Parameters declare the parameters accepted
     * by the Resource Services extension.
     */
    public class MethodParameters extends RequestParameters {
        private MethodType method;

        /**
         * Declares the parameters for a method the provides services for a resource.
         * @param method	the method type
         */
        public MethodParameters(MethodType method) {
        	super();
        	this.method = method;
        }

        /**
         * Returns the method for the parameters.
         * @return	the method type
         */
        public MethodType getMethod() {
        	return method;
        }

        /**
         * Returns the hash code for the method.
         */
        @Override
		public int hashCode() {
			return getMethod().hashCode();
		}
        /**
         * Returns whether the method declaration is the same.
         */
		@Override
		public boolean equals(Object other) {
			if (other == null)
				return false;
			if (!(other instanceof MethodParameters))
				return false;

			MethodParameters otherParam = (MethodParameters) other; 
			if (!getMethod().equals(otherParam.getMethod()))
				return false;

			return super.equals(otherParam);
		}
    }
}
