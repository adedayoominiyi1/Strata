/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.curve.node;

import static com.opengamma.strata.basics.date.Tenor.TENOR_10Y;
import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static com.opengamma.strata.collect.TestHelper.date;
import static com.opengamma.strata.product.common.BuySell.BUY;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Set;

import org.testng.annotations.Test;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.StandardId;
import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.data.ImmutableMarketData;
import com.opengamma.strata.data.MarketData;
import com.opengamma.strata.data.MarketDataNotFoundException;
import com.opengamma.strata.data.ObservableId;
import com.opengamma.strata.market.ValueType;
import com.opengamma.strata.market.curve.CurveNodeDate;
import com.opengamma.strata.market.observable.QuoteId;
import com.opengamma.strata.market.param.DatedParameterMetadata;
import com.opengamma.strata.market.param.ParameterMetadata;
import com.opengamma.strata.market.param.TenorDateParameterMetadata;
import com.opengamma.strata.product.swap.SwapTrade;
import com.opengamma.strata.product.swap.type.OvernightIborSwapConventions;
import com.opengamma.strata.product.swap.type.OvernightIborSwapTemplate;

/**
 * Test {@link FixedIborSwapCurveNode}.
 */
@Test
public class OvernightIborSwapCurveNodeTest {

  private static final ReferenceData REF_DATA = ReferenceData.standard();
  private static final LocalDate VAL_DATE = date(2015, 6, 30);

  private static final OvernightIborSwapTemplate TEMPLATE =
      OvernightIborSwapTemplate.of(TENOR_10Y, OvernightIborSwapConventions.USD_FED_FUND_AA_LIBOR_3M);
  private static final QuoteId QUOTE_ID = QuoteId.of(StandardId.of("OG-Ticker", "Deposit1"));
  private static final double SPREAD = 0.0015;
  private static final String LABEL = "Label";
  private static final String LABEL_AUTO = "10Y";

  private static final double TOLERANCE_DF = 1.0E-10;

  public void test_builder() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.builder()
        .label(LABEL)
        .template(TEMPLATE)
        .rateId(QUOTE_ID)
        .additionalSpread(SPREAD)
        .build();
    assertEquals(test.getLabel(), LABEL);
    assertEquals(test.getRateId(), QUOTE_ID);
    assertEquals(test.getAdditionalSpread(), SPREAD);
    assertEquals(test.getTemplate(), TEMPLATE);
    assertEquals(test.getDate(), CurveNodeDate.END);
  }

  public void test_of_noSpread() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID);
    assertEquals(test.getLabel(), LABEL_AUTO);
    assertEquals(test.getRateId(), QUOTE_ID);
    assertEquals(test.getAdditionalSpread(), 0.0d);
    assertEquals(test.getTemplate(), TEMPLATE);
  }

  public void test_of_withSpread() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    assertEquals(test.getLabel(), LABEL_AUTO);
    assertEquals(test.getRateId(), QUOTE_ID);
    assertEquals(test.getAdditionalSpread(), SPREAD);
    assertEquals(test.getTemplate(), TEMPLATE);
  }

  public void test_of_withSpreadAndLabel() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD, LABEL);
    assertEquals(test.getLabel(), LABEL);
    assertEquals(test.getRateId(), QUOTE_ID);
    assertEquals(test.getAdditionalSpread(), SPREAD);
    assertEquals(test.getTemplate(), TEMPLATE);
  }

  public void test_requirements() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    Set<ObservableId> set = test.requirements();
    Iterator<ObservableId> itr = set.iterator();
    assertEquals(itr.next(), QUOTE_ID);
    assertFalse(itr.hasNext());
  }

  public void test_trade() {
    OvernightIborSwapCurveNode node = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    LocalDate tradeDate = LocalDate.of(2015, 1, 22);
    double rate = 0.125;
    double quantity = -1234.56;
    MarketData marketData = ImmutableMarketData.builder(tradeDate).addValue(QUOTE_ID, rate).build();
    SwapTrade trade = node.trade(quantity, marketData, REF_DATA);
    SwapTrade expected = TEMPLATE.createTrade(tradeDate, BUY, -quantity, rate + SPREAD, REF_DATA);
    assertEquals(trade, expected);
  }

  public void test_trade_noMarketData() {
    OvernightIborSwapCurveNode node = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    LocalDate valuationDate = LocalDate.of(2015, 1, 22);
    MarketData marketData = MarketData.empty(valuationDate);
    assertThatExceptionOfType(MarketDataNotFoundException.class)
        .isThrownBy(() -> node.trade(1d, marketData, REF_DATA));
  }

  public void test_initialGuess() {
    OvernightIborSwapCurveNode node = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    LocalDate valuationDate = LocalDate.of(2015, 1, 22);
    double rate = 0.035;
    MarketData marketData = ImmutableMarketData.builder(valuationDate).addValue(QUOTE_ID, rate).build();
    assertEquals(node.initialGuess(marketData, ValueType.ZERO_RATE), rate);
    assertEquals(node.initialGuess(marketData, ValueType.FORWARD_RATE), rate);
    double df = Math.exp(-TENOR_10Y.get(ChronoUnit.YEARS) * rate);
    assertEquals(node.initialGuess(marketData, ValueType.DISCOUNT_FACTOR), df, TOLERANCE_DF);
    assertEquals(node.initialGuess(marketData, ValueType.PRICE_INDEX), 0d);
  }

  public void test_metadata_end() {
    OvernightIborSwapCurveNode node = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    LocalDate valuationDate = LocalDate.of(2015, 1, 22);
    ParameterMetadata metadata = node.metadata(valuationDate, REF_DATA);
    // 2015-01-22 is Thursday, start is 2015-01-26, but 2025-01-26 is Sunday, so end is 2025-01-27
    assertEquals(((TenorDateParameterMetadata) metadata).getDate(), LocalDate.of(2025, 1, 27));
    assertEquals(((TenorDateParameterMetadata) metadata).getTenor(), Tenor.TENOR_10Y);
  }

  public void test_metadata_fixed() {
    OvernightIborSwapCurveNode node =
        OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD, LABEL).withDate(CurveNodeDate.of(VAL_DATE));
    LocalDate valuationDate = LocalDate.of(2015, 1, 22);
    DatedParameterMetadata metadata = node.metadata(valuationDate, REF_DATA);
    assertEquals(metadata.getDate(), VAL_DATE);
    assertEquals(metadata.getLabel(), node.getLabel());
  }

  public void test_metadata_last_fixing() {
    OvernightIborSwapCurveNode node =
        OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD, LABEL).withDate(CurveNodeDate.LAST_FIXING);
    LocalDate valuationDate = LocalDate.of(2015, 1, 22);
    LocalDate fixingExpected = LocalDate.of(2024, 10, 24);
    DatedParameterMetadata metadata = node.metadata(valuationDate, REF_DATA);
    assertEquals(metadata.getDate(), fixingExpected);
    assertEquals(metadata.getLabel(), node.getLabel());
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    coverImmutableBean(test);
    OvernightIborSwapCurveNode test2 = OvernightIborSwapCurveNode.of(
        OvernightIborSwapTemplate.of(TENOR_10Y, OvernightIborSwapConventions.GBP_SONIA_OIS_1Y_LIBOR_3M),
        QuoteId.of(StandardId.of("OG-Ticker", "Deposit2")));
    coverBeanEquals(test, test2);
  }

  public void test_serialization() {
    OvernightIborSwapCurveNode test = OvernightIborSwapCurveNode.of(TEMPLATE, QUOTE_ID, SPREAD);
    assertSerialization(test);
  }

}
