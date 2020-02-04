package io.cryptex.ms.wrapper.ripple.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RippleBalanceConverter {

    private static final Long MILLION_DROPS = 1_000_000L;

    public static Double toDouble(String amount) {
        return BigDecimal.valueOf(Long.parseLong(amount)).divide(BigDecimal.valueOf(MILLION_DROPS)).doubleValue();
    }

    public static Long toAtomicUnits(Double amount) {
        return BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(MILLION_DROPS)).longValue();
    }
}
