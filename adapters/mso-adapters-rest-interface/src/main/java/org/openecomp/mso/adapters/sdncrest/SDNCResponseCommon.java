/*-
 * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * Copyright (C) 2017 Huawei Technologies Co., Ltd. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.openecomp.mso.adapters.sdncrest;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.io.Serializable;
import org.openecomp.mso.logger.MsoLogger;

/**
 * Base class for all SDNC adapter responses, including errors.
 */
public abstract class SDNCResponseCommon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final MsoLogger LOGGER = MsoLogger.getMsoLogger (MsoLogger.Catalog.RA);

	// Identifies the MSO transaction with SDNC.
	private String sdncRequestId;

	// Response code, either from SDNC, or generated by the SDNC adapter.
	// 2XX responses are considered success responses.
	private String responseCode;

	// Response message, either from SDNC, or generated by the SDNC adapter.
	private String responseMessage;

	// Indicates if the response is final (Y or N).
	private String ackFinalIndicator;

	public SDNCResponseCommon(String sdncRequestId, String responseCode,
			String responseMessage, String ackFinalIndicator) {
		this.sdncRequestId = sdncRequestId;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.ackFinalIndicator = ackFinalIndicator;
	}

	public SDNCResponseCommon() {
	}

	@JsonProperty("sdncRequestId")
	@XmlElement(name = "sdncRequestId")
	public String getSDNCRequestId() {
		return sdncRequestId;
	}

	@JsonProperty("sdncRequestId")
	public void setSDNCRequestId(String sdncRequestId) {
		this.sdncRequestId = sdncRequestId;
	}

	@JsonProperty("responseCode")
	@XmlElement(name = "responseCode")
	public String getResponseCode() {
		return responseCode;
	}

	@JsonProperty("responseCode")
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@JsonProperty("responseMessage")
	@XmlElement(name = "responseMessage")
	public String getResponseMessage() {
		return responseMessage;
	}

	@JsonProperty("responseMessage")
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@JsonProperty("ackFinalIndicator")
	@XmlElement(name = "ackFinalIndicator")
	public String getAckFinalIndicator() {
		return ackFinalIndicator;
	}

	@JsonProperty("ackFinalIndicator")
	public void setAckFinalIndicator(String ackFinalIndicator) {
		this.ackFinalIndicator = ackFinalIndicator;
	}

	public String toJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			return mapper.writeValueAsString(this);
		} catch (IOException e) {
		    LOGGER.debug("Exception:", e);
			throw new UnsupportedOperationException("Cannot convert "
				+ getClass().getSimpleName() + " to JSON", e);
		}
	}
}