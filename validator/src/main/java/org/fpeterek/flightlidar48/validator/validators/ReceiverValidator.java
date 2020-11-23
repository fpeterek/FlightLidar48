package org.fpeterek.flightlidar48.validator.validators;

import org.fpeterek.flightlidar48.validator.Config;
import org.fpeterek.flightlidar48.validator.ReceiverData;

public class ReceiverValidator {

  public boolean receiverInputAccepted(ReceiverData recv) {

    /* Receiver cannot yet be assessed, so we accept it's input so long as it's valid */
    if (recv.totalRequests() < Config.get().assessmentThreshold) {
      return true;
    }

    /* Detection of attempts to fill up the system with an excessive amount of data        */
    /* Not precisely DDOS attack protection -> DDOS protection should be handled elsewhere */
    /* (using Envoy or something)                                                          */
    /* I'm just calling it a DDOS for my inability to come up with a better name           */
    var ddos = recv.avgReqTime() <= Config.get().ddosThreshold && recv.validPercentage() <= 70;

    /* If receiver doesn't send way too much data, we can be more lenient when detecting malicious intentions */
    var providesValidInfo = recv.validPercentage() > 50;

    return !ddos && providesValidInfo;

  }

}
