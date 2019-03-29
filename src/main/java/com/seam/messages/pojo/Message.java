package com.seam.messages.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The message object class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

	public String senderId;

	public String body;

	public List<String> attachmentIds;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Kolkata")
	public Date createdAt;

	public JsonNode readAt; // Unread Message will have value as 0 and the key is the receiver id

	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the attachmentIds
	 */
	public List<String> getAttachmentIds() {
		return attachmentIds;
	}

	/**
	 * @param attachmentIds the attachmentIds to set
	 */
	public void setAttachmentIds(List<String> attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the readAt
	 */
	public JsonNode getReadAt() {
		return readAt;
	}

	/**
	 * @param readAt the readAt to set
	 */
	public void setReadAt(JsonNode readAt) {
		this.readAt = readAt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [senderId=" + senderId + ", body=" + body + ", attachmentIds=" + attachmentIds + ", createdAt="
				+ createdAt + ", readAt=" + readAt + "]";
	}

}
