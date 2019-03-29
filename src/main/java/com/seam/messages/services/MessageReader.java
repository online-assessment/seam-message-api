package com.seam.messages.services;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.seam.messages.exception.SeamException;
import com.seam.messages.pojo.Message;
import com.seam.messages.utils.MapperUtils;

public interface MessageReader {

	/**
	 * Get the latest message of the given subscriber
	 *
	 * @param subscriberId the id of the subscriber whose message is to be found out
	 * @return Latest message of the subscriber
	 * @throws SeamException
	 */
	Message getLatestMessage(String subscriberId) throws SeamException;

	/**
	 * Get the first unread message of the given subscriber
	 *
	 * @param subscriberId the id of the subscriber whose message is to be found out
	 * @return First unread message of the subscriber
	 * @throws SeamException 
	 */
	Message getFirstUnreadMessage(String subscriberId) throws SeamException;

	public static List<Message> getAllMessage() {
		List<Message> messages = new LinkedList<>();
		try {
			// Read the message folder
			File folder = ResourceUtils.getFile("classpath:db");
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.isDirectory()) {
					File[] files = new File(file.getAbsolutePath()).listFiles();
					// If this pathname does not denote a directory, then listFiles() returns null.

					for (File file1 : files) {
						if (file1.isFile()) {
							// Get the message node from the file
							JsonNode messageNode = JsonLoader.fromFile(file1);

							// Convert the message node to the corresponding object
							Message message = MapperUtils.getMapper().convertValue(messageNode, Message.class);
							messages.add(message);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return messages;

	}
}
