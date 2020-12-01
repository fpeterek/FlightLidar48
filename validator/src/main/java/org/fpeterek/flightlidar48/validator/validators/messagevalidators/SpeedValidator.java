package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class SpeedValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    /* Although, theoretically, an aircraft could be moving backwards and still generate enough lift in a very strong */
    /* wind, let's just hope no one ever gets into such a situation and presume nobody is going to fly a C172 into    */
    /* a hurricane                                                                                                    */
    /* Let's also assume we don't plan to track aircraft flying at MACH4 and above (3000 kts ~= 4.5 mach)             */
    return 0 <= msg.speed() && msg.speed() <= 3000;
  }

}
