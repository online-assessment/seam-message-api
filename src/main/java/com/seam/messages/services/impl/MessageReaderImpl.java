package com.seam.messages.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.seam.messages.common.MessageConstant;
import com.seam.messages.exception.SeamException;
import com.seam.messages.pojo.Message;
import com.seam.messages.services.MessageReader;
import com.seam.messages.utils.MapperUtils;

@Service
public class MessageReaderImpl implements MessageReader {

	Logger log = LoggerFactory.getLogger(MessageReaderImpl.class);

	// using java 8
	@Override
	public Message getLatestMessage(String subscriberId) throws SeamException {

		Message latestMessage = null;
		List<Message> messages = MessageReader.getAllMessage();
		log.info("messages fetch count from DB : {}", messages.size());

		latestMessage = getLatestMessageBySubscriberId(messages, subscriberId);
		log.debug("Latest Message {}  for subscriber {}", latestMessage, subscriberId);

		if (latestMessage == null) {
			throw new SeamException("Message not available for subscriber : " + subscriberId);
		} else {
			return latestMessage;
		}
	}

	// using java 7

	/**
	 * Get the first unread message of the given subscriber
	 * 
	 * My business : here we have sender_id and reciever_id
	 * 
	 * so my code will fetch first unread message based on sender id
	 * 
	 * ex : for subscriber id=9020be9e-958a-4536-8164-4a8bbc17c3da
	 * 
	 * we have 4 Message object but 2 message object readAt value is 0 ,so i fetched
	 * first unread from these 2
	 *
	 * #CONFUSION : we need to find out based on sender_id or reciever_id ?
	 *
	 * as of now my code will work for sender_id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Message getFirstUnreadMessage(String subscriberId) throws SeamException {

		Message firstUnreadMessage = null;
		Map<String, String> map = null;

		List<Message> messages = MessageReader.getAllMessage();
		log.info("messages fetch count from DB : {}", messages.size());

		try {
			for (Message message : messages) {
				if (message.getSenderId().equals(subscriberId)) {
					map = MapperUtils.getMapper().convertValue(message.getReadAt(), Map.class);
					for (String key : map.keySet()) {
						if (map.get(key).equals(MessageConstant.UNREAD_VALUE)) {
							firstUnreadMessage = message;
						}
					}
				}
			}

			log.debug("First UnreadMessage {} for subscriber {} ", firstUnreadMessage, subscriberId);

			if (firstUnreadMessage == null) {
				throw new SeamException("First Unread Message not available for subscriber : " + subscriberId);
			}

		} catch (Exception ex) {
			throw new SeamException("No data found for : " + subscriberId);
		}
		return firstUnreadMessage;
	}

	private Message getLatestMessageBySubscriberId(List<Message> messages, String subscriberId) throws SeamException {
		Message message = null;
		try {
			message = messages.size() != 0
					? Collections.max(messages.stream().filter(msg -> msg.getSenderId().equals(subscriberId))
							.collect(Collectors.toList()), Comparator.comparing(Message::getCreatedAt))
					: null;

		} catch (Exception ex) {
			log.error("ERROR : {}", ex.getMessage());
			throw new SeamException("Unable to fetch latest message");
		}
		return message;
	}
}
