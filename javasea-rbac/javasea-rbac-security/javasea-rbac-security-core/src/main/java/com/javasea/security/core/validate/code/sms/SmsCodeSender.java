/**
 *
 */
package com.javasea.security.core.validate.code.sms;

/**
 * @author zhailiang
 *
 */
public interface SmsCodeSender {

	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
