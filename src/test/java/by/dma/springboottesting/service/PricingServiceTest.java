package by.dma.springboottesting.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    @Mock
    private AvailabilityVerifier mockedProductVerifier;

    @InjectMocks
    private PricingService cut;

    @Test
    void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
        Mockito.when(mockedProductVerifier.isInStock("AirPods"))
               .thenReturn(true);

        assertEquals(new BigDecimal("9.99"), cut.calculatePrice("AirPods"));
    }

    @Test
    void shouldReturnHigherPriceWhenProductIsNotInStockOfCompetitor() {
        Mockito.when(mockedProductVerifier.isInStock("AirPods"))
               .thenReturn(false);

        assertEquals(new BigDecimal("14.98"), cut.calculatePrice("AirPods"));
    }
}
