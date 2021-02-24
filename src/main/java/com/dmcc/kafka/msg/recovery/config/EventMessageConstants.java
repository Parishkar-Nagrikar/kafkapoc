package com.dmcc.kafka.msg.recovery.config;

public class EventMessageConstants {

	private EventMessageConstants() {

	}

	public static final int NO_OF_ATTEPTS = 3;
	public static final String EVENT_MESSAGE_STATUS_FAILED = "FAILED";
	public static final String EVENT_MESSAGE_STATUS_ERROR = "ERROR";
	public static final String EVENT_MESSAGE_STATUS_NOTIFIED = "NOTIFIED";
}