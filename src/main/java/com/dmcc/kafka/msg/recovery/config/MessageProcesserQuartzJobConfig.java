package com.dmcc.kafka.msg.recovery.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmcc.kafka.msg.recovery.scheduler.MessageFailureEoDReportQuartzJob;
import com.dmcc.kafka.msg.recovery.scheduler.MessageProcessQuartzJob;

@Configuration
public class MessageProcesserQuartzJobConfig {
	private static final String CRON_EVERY_FIVE_MIN = "0 0/2 * ? * * *";

	private static final String CRON_EVERY_EoD = "0 0/10 * ? * * *";

	@Bean
	public JobDetail jobManageMessageProcesser() {
		return JobBuilder.newJob(MessageProcessQuartzJob.class).withIdentity("MsgProcessJob").storeDurably().build();
	}

	@Bean
	public Trigger jobManageMessageProcesserTrigger(JobDetail jobManageMessageProcesser) {
		return TriggerBuilder.newTrigger().forJob(jobManageMessageProcesser).withIdentity("MsgProcessJob")
				.withSchedule(CronScheduleBuilder.cronSchedule(CRON_EVERY_FIVE_MIN)).build();
	}

	@Bean
	public JobDetail jobManageEoDFailureMessgeReport() {
		return JobBuilder.newJob(MessageFailureEoDReportQuartzJob.class).withIdentity("EoDFailureMessgeReportJob").storeDurably()
				.build();
	}

	@Bean
	public Trigger jobManageEoDFailureMessgeReportTrigger(JobDetail jobManageEoDFailureMessgeReport) {
		return TriggerBuilder.newTrigger().forJob(jobManageEoDFailureMessgeReport).withIdentity("EoDFailureMessgeReportJob")
				.withSchedule(CronScheduleBuilder.cronSchedule(CRON_EVERY_EoD)).build();
	}
}
