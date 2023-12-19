/**
 * @author Starling Diaz on 11/19/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 11/19/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

import static com.twilio.rest.api.v2010.account.Message.creator;

@Slf4j
public class SmsUtils {
  //OBSERVATION YOU NEED TO BUY A TWILIO NUMBER TO USE IN THE PROJECT
  private static final String FROM_NUMBER = "<Your own number from Twilio>";
  private static final String SID_KEY = "<Your own key>";
  public static final String TOKEN_KEY = "<Your own key>";

  public static void sendSMS(String toNumber, String messageBody) {
      Twilio.init(SID_KEY, TOKEN_KEY);
      Message message = creator(new PhoneNumber("+" + toNumber), new PhoneNumber(FROM_NUMBER), messageBody).create();
      log.info("Message sent to {} with body {}", toNumber, messageBody);
  }
}
