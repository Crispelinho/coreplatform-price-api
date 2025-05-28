package com.inditex.coreplatform.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inditex.coreplatform.application.usecases.GetApplicablePriceUseCase;
import com.inditex.coreplatform.application.usecases.GetPricesUseCase;
import com.inditex.coreplatform.domain.ports.IPriceService;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(IPriceService priceService) {
        return new GetApplicablePriceUseCase(priceService);
    }

    @Bean
    public GetPricesUseCase getPricesUseCase(IPriceService priceService) {
        return new GetPricesUseCase(priceService);
    }
}
