/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.rate.fra;

import static com.opengamma.platform.finance.rate.fra.FraDiscountingMethod.ISDA;
import static com.opengamma.strata.basics.BuySell.BUY;
import static com.opengamma.strata.basics.currency.Currency.GBP;
import static com.opengamma.strata.basics.currency.Currency.USD;
import static com.opengamma.strata.basics.date.BusinessDayConventions.MODIFIED_FOLLOWING;
import static com.opengamma.strata.basics.date.HolidayCalendars.GBLO;
import static com.opengamma.strata.basics.index.IborIndices.GBP_LIBOR_2M;
import static com.opengamma.strata.basics.index.IborIndices.GBP_LIBOR_3M;
import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.assertThrowsIllegalArg;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static com.opengamma.strata.collect.TestHelper.date;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import org.testng.annotations.Test;

import com.opengamma.platform.finance.rate.IborRateObservation;
import com.opengamma.strata.basics.date.AdjustableDate;
import com.opengamma.strata.basics.date.BusinessDayAdjustment;
import com.opengamma.strata.basics.date.DaysAdjustment;

/**
 * Test.
 */
@Test
public class ExpandedFraTest {

  private static final double NOTIONAL_1M = 1_000_000d;
  private static final double NOTIONAL_2M = 2_000_000d;
  private static final BusinessDayAdjustment BDA_MOD_FOLLOW = BusinessDayAdjustment.of(MODIFIED_FOLLOWING, GBLO);
  private static final DaysAdjustment MINUS_TWO_DAYS = DaysAdjustment.ofBusinessDays(-2, GBLO);

  //-------------------------------------------------------------------------
  public void test_builder() {
    ExpandedFra test = ExpandedFra.builder()
        .paymentDate(date(2015, 6, 16))
        .startDate(date(2015, 6, 15))
        .endDate(date(2015, 9, 15))
        .yearFraction(0.25d)
        .fixedRate(0.25d)
        .floatingRate(IborRateObservation.of(GBP_LIBOR_3M, date(2015, 6, 12)))
        .currency(GBP)
        .notional(NOTIONAL_1M)
        .discounting(ISDA)
        .build();
    assertEquals(test.getPaymentDate(), date(2015, 6, 16));
    assertEquals(test.getStartDate(), date(2015, 6, 15));
    assertEquals(test.getEndDate(), date(2015, 9, 15));
    assertEquals(test.getYearFraction(), 0.25d, 0d);
    assertEquals(test.getFixedRate(), 0.25d, 0d);
    assertEquals(test.getFloatingRate(), IborRateObservation.of(GBP_LIBOR_3M, date(2015, 6, 12)));
    assertEquals(test.getCurrency(), GBP);
    assertEquals(test.getNotional(), NOTIONAL_1M, 0d);
    assertEquals(test.getDiscounting(), ISDA);
  }

  public void test_builder_datesInOrder() {
    assertThrowsIllegalArg(() -> Fra.builder()
        .buySell(BUY)
        .paymentDate(AdjustableDate.of(date(2015, 6, 16), BDA_MOD_FOLLOW))
        .startDate(date(2015, 6, 15))
        .endDate(date(2015, 6, 14))
        .fixedRate(0.25d)
        .index(GBP_LIBOR_3M)
        .fixingOffset(MINUS_TWO_DAYS)
        .notional(NOTIONAL_1M)
        .build());
  }

  public void test_expand() {
    ExpandedFra test = ExpandedFra.builder()
        .paymentDate(date(2015, 6, 16))
        .startDate(date(2015, 6, 15))
        .endDate(date(2015, 9, 15))
        .yearFraction(0.25d)
        .fixedRate(0.25d)
        .floatingRate(IborRateObservation.of(GBP_LIBOR_3M, date(2015, 6, 12)))
        .currency(GBP)
        .notional(NOTIONAL_1M)
        .discounting(ISDA)
        .build();
    assertSame(test.expand(), test);
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    ExpandedFra test = ExpandedFra.builder()
        .paymentDate(date(2015, 6, 16))
        .startDate(date(2015, 6, 15))
        .endDate(date(2015, 9, 15))
        .yearFraction(0.25d)
        .fixedRate(0.25d)
        .floatingRate(IborRateObservation.of(GBP_LIBOR_3M, date(2015, 6, 12)))
        .currency(GBP)
        .notional(NOTIONAL_1M)
        .discounting(ISDA)
        .build();
    coverImmutableBean(test);
    ExpandedFra test2 = ExpandedFra.builder()
        .paymentDate(date(2015, 6, 17))
        .startDate(date(2015, 6, 16))
        .endDate(date(2015, 9, 16))
        .yearFraction(0.26d)
        .fixedRate(0.27d)
        .floatingRate(IborRateObservation.of(GBP_LIBOR_2M, date(2015, 6, 12)))
        .currency(USD)
        .notional(NOTIONAL_2M)
        .discounting(FraDiscountingMethod.NONE)
        .build();
    coverBeanEquals(test, test2);
  }

  public void test_serialization() {
    ExpandedFra test = ExpandedFra.builder()
        .paymentDate(date(2015, 6, 16))
        .startDate(date(2015, 6, 15))
        .endDate(date(2015, 9, 15))
        .yearFraction(0.25d)
        .fixedRate(0.25d)
        .floatingRate(IborRateObservation.of(GBP_LIBOR_3M, date(2015, 6, 12)))
        .currency(GBP)
        .notional(NOTIONAL_1M)
        .discounting(ISDA)
        .build();
    assertSerialization(test);
  }

}
