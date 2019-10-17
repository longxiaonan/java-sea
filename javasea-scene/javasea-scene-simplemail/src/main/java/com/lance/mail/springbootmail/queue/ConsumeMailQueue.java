package com.lance.mail.springbootmail.queue;

import com.lance.mail.springbootmail.bean.MailBean;
import com.lance.mail.springbootmail.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费队列
 */
@Component
public class ConsumeMailQueue {
	private static final Logger logger = LoggerFactory.getLogger(ConsumeMailQueue.class);
	@Autowired
	MailUtil mailService;

	@PostConstruct
	public void startThread() {
		ExecutorService e = Executors.newFixedThreadPool(2);// 两个大小的固定线程池
		e.submit(new PollMail(mailService));
		e.submit(new PollMail(mailService));
	}

	class PollMail implements Runnable {
		MailUtil mailService;

		public PollMail(MailUtil mailService) {
			this.mailService = mailService;
		}

		@Override
		public void run() {
			while (true) {
				try {
					 MailBean mail = MailQueue.getMailQueue().consume();
					if (mail != null) {
						logger.info("剩余邮件总数:{}",MailQueue.getMailQueue().size());
						mailService.sendSimpleMail(mail);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@PreDestroy
	public void stopThread() {
		logger.info("destroy");
	}
}
