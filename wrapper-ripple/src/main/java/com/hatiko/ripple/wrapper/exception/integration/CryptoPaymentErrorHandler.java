package com.hatiko.ripple.wrapper.exception.integration;


import com.hatiko.ripple.wrapper.constants.ServiceName;
import com.hatiko.ripple.wrapper.dto.ErrorInfoDto;

public class CryptoPaymentErrorHandler extends BasicResponseErrorHandler {

    @Override
    String getServiceName() {
        return ServiceName.CRYPTOPAYMENT.name();
    }

    @Override
    void handleError(ErrorInfoDto errorInfoDto) {
        throw new RemoteMsPassThroughException(errorInfoDto);
    }
}
