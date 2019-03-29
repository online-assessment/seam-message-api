package com.seam.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seam.messages.exception.SeamException;
import com.seam.messages.pojo.Message;
import com.seam.messages.services.MessageReader;

/**
 * The controller to get the messages
 */
@RestController
@RequestMapping(value = "/messages")
public class MessageReaderController {

	@Autowired
	private MessageReader messageReader;

	@GetMapping(value = "/latest")
	public Message getLatestMessage(@RequestParam("subscriberId") String subscriberId) throws SeamException {
		return messageReader.getLatestMessage(subscriberId);
	}

	@GetMapping(value = "/unread")
	public Message getFirstUnreadMessage(@RequestParam("subscriberId") String subscriberId) throws SeamException {
		return messageReader.getFirstUnreadMessage(subscriberId);
	}
}
