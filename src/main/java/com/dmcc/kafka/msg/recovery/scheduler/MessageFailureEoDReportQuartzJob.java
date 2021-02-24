package com.dmcc.kafka.msg.recovery.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.dmcc.kafka.msg.recovery.service.EventMessageProcesserService;

public class MessageFailureEoDReportQuartzJob implements Job {
	@Autowired
	private EventMessageProcesserService eventMessageProcesserService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("*** Executed the Schedular to send EoD Report for Failue Messages***");

		eventMessageProcesserService.sendEoDMessageFailureNotification();

	}

}
