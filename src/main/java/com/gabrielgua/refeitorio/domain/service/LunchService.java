package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.LunchNotFoundException;
import com.gabrielgua.refeitorio.domain.exception.LunchStatusException;
import com.gabrielgua.refeitorio.domain.model.Lunch;
import com.gabrielgua.refeitorio.domain.model.LunchStatus;
import com.gabrielgua.refeitorio.domain.model.Price;
import com.gabrielgua.refeitorio.domain.repository.LunchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class LunchService {

    private final LunchRepository repository;

    private static final BigDecimal PRICE_PER_KG = BigDecimal.valueOf(15);
    private static final BigDecimal DISCOUNT_NORMAL = BigDecimal.valueOf(0.25);
    private static final BigDecimal DISCOUNT_HALF = BigDecimal.valueOf(0.5);
    private static final BigDecimal DISCOUNT_FREE = BigDecimal.ONE;

    private static final BigDecimal SALARY_ANALYST = BigDecimal.valueOf(3000);
    private static final BigDecimal SALARY_MANAGER = BigDecimal.valueOf(5000);
    private static final BigDecimal SALARY_INTERN = BigDecimal.valueOf(1000);


    @Transactional
    public Lunch save(Lunch lunch) {
        return repository.save(lunch);
    }

    @Transactional(readOnly = true)
    public Lunch findById(Long lunchId) {
        return repository.findById(lunchId).orElseThrow(() -> new LunchNotFoundException(lunchId));
    }

    @Transactional
    public void confirm(Lunch lunch) {
        if (lunch.getStatus() == LunchStatus.CONFIRMED) {
            throw new LunchStatusException("Cannot confirm a confirmed lunch.");
        }
        lunch.setStatus(LunchStatus.CONFIRMED);
        repository.save(lunch);
    }

    @Transactional
    public void cancel(Lunch lunch) {
        if (lunch.getStatus() == LunchStatus.CONFIRMED) {
            throw new LunchStatusException("Cannot cancel a confirmed lunch.");
        }

        repository.delete(lunch);
    }

    public Price calculatePrice(BigDecimal salary, BigDecimal weight) {
        var price = PRICE_PER_KG.multiply(weight);
        return calculateDiscount(salary, price);
    }

    public Price calculateDiscount(BigDecimal salary, BigDecimal price) {
        var discount = DISCOUNT_FREE;

        if (salary.compareTo(SALARY_MANAGER) >= 0) {
            discount = BigDecimal.ZERO;
        } else if (salary.compareTo(SALARY_ANALYST) >= 0) {
            discount = DISCOUNT_NORMAL;
        } else if (salary.compareTo(SALARY_INTERN) > 0) {
            discount = DISCOUNT_HALF;
        };


        var discounted_price = price.multiply(discount).setScale(2, RoundingMode.HALF_UP);
        var final_price = price.subtract(discounted_price).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

        return Price.builder()
                .discount(discount)
                .original_price(price)
                .final_price(final_price)
                .discounted_price(discounted_price)
                .build();
    }

}