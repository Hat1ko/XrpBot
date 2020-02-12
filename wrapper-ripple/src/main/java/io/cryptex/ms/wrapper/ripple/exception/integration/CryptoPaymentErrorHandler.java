package io.cryptex.ms.wrapper.ripple.exception.integration;


import io.cryptex.ms.wrapper.ripple.constants.ServiceName;
import io.cryptex.ms.wrapper.ripple.dto.ErrorInfoDto;

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
