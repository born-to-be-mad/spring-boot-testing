package by.dma.springboottesting.layers.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PricingService {
    private static final BigDecimal BASE_PRICE = new BigDecimal("9.99");
    private static final BigDecimal RATION = new BigDecimal("1.5");
    private static final MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_EVEN);

    private final AvailabilityVerifier productVerifier;

    public PricingService(AvailabilityVerifier productVerifier) {
        this.productVerifier = productVerifier;
    }

    public BigDecimal calculatePrice(String productName) {
        if (productVerifier.isInStock(productName)) {
            return BASE_PRICE;
        }

        return BASE_PRICE.multiply(RATION, MATH_CONTEXT);
    }
}
