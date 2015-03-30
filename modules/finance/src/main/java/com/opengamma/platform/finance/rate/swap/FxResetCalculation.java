/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.rate.swap;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableDefaults;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.date.DaysAdjustment;
import com.opengamma.strata.basics.index.FxIndex;
import com.opengamma.strata.basics.schedule.SchedulePeriod;
import com.opengamma.strata.collect.Messages;

/**
 * Defines the calculation of an FX rate conversion for the notional amount of a swap leg.
 * <p>
 * Interest rate swaps are based on a notional amount of money.
 * The notional can be specified in a currency other than that of the swap leg,
 * with an FX conversion applied at each payment period boundary.
 * <p>
 * The two currencies involved are the swap leg currency and the reference currency.
 * The swap leg currency is, in most cases, the currency that payment will occur in.
 * The reference currency is the currency in which the notional is actually defined.
 * ISDA refers to the payment currency as the <i>variable currency</i> and the reference
 * currency as the <i>constant currency</i>.
 * <p>
 * Defined by the 2006 ISDA definitions article 10.
 */
@BeanDefinition
public final class FxResetCalculation
    implements ImmutableBean, Serializable {

  /**
   * The FX index used to obtain the FX reset rate.
   * <p>
   * This is the index of FX used to obtain the FX reset rate.
   * An FX index is a daily rate of exchange between two currencies.
   * Note that the order of the currencies in the index does not matter, as the
   * conversion direction is fully defined by the reference and swap leg currencies.
   */
  @PropertyDefinition(validate = "notNull")
  private final FxIndex index;
  /**
   * The currency of the notional amount defined in the contract.
   * <p>
   * This is the currency of notional amount as defined in the contract.
   * The amount will be converted from this reference currency to the swap leg currency
   * when calculating the value of the leg.
   * <p>
   * The reference currency must be one of the two currencies of the index.
   * <p>
   * The reference currency is also known as the <i>constant currency</i>.
   */
  @PropertyDefinition(validate = "notNull")
  private final Currency referenceCurrency;
  /**
   * The base date that each FX reset fixing is made relative to, defaulted to 'PeriodStart'.
   * <p>
   * The FX reset fixing date is relative to either the start or end of each accrual period.
   */
  @PropertyDefinition(validate = "notNull")
  private final FxResetFixingRelativeTo fixingRelativeTo;
  /**
   * The offset of the FX reset fixing date from each adjusted accrual date.
   * <p>
   * The offset is applied to the base date specified by {@code fixingRelativeTo}.
   * The offset is typically a negative number of business days.
   */
  @PropertyDefinition(validate = "notNull")
  private final DaysAdjustment fixingOffset;

  //-------------------------------------------------------------------------
  @ImmutableDefaults
  private static void applyDefaults(Builder builder) {
    builder.fixingRelativeTo(FxResetFixingRelativeTo.PERIOD_START);
  }

  @ImmutableValidator
  private void validate() {
    if (!index.getCurrencyPair().contains(referenceCurrency)) {
      throw new IllegalArgumentException(
          Messages.format("Reference currency {} must be one of those in the FxIndex {}", referenceCurrency, index));
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Applies this {@code FxResetCalculation} to the the specified period.
   * <p>
   * The conversion locks the fixing date based on the specified schedule period
   * and the data held in this object.
   * 
   * @param period  the schedule period
   * @return the expanded reset
   */
  public FxReset applyToPeriod(SchedulePeriod period) {
    LocalDate fixingDate = fixingOffset.adjust(fixingRelativeTo.selectBaseDate(period));
    return FxReset.of(index, referenceCurrency, fixingDate);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FxResetCalculation}.
   * @return the meta-bean, not null
   */
  public static FxResetCalculation.Meta meta() {
    return FxResetCalculation.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FxResetCalculation.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static FxResetCalculation.Builder builder() {
    return new FxResetCalculation.Builder();
  }

  private FxResetCalculation(
      FxIndex index,
      Currency referenceCurrency,
      FxResetFixingRelativeTo fixingRelativeTo,
      DaysAdjustment fixingOffset) {
    JodaBeanUtils.notNull(index, "index");
    JodaBeanUtils.notNull(referenceCurrency, "referenceCurrency");
    JodaBeanUtils.notNull(fixingRelativeTo, "fixingRelativeTo");
    JodaBeanUtils.notNull(fixingOffset, "fixingOffset");
    this.index = index;
    this.referenceCurrency = referenceCurrency;
    this.fixingRelativeTo = fixingRelativeTo;
    this.fixingOffset = fixingOffset;
    validate();
  }

  @Override
  public FxResetCalculation.Meta metaBean() {
    return FxResetCalculation.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the FX index used to obtain the FX reset rate.
   * <p>
   * This is the index of FX used to obtain the FX reset rate.
   * An FX index is a daily rate of exchange between two currencies.
   * Note that the order of the currencies in the index does not matter, as the
   * conversion direction is fully defined by the reference and swap leg currencies.
   * @return the value of the property, not null
   */
  public FxIndex getIndex() {
    return index;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency of the notional amount defined in the contract.
   * <p>
   * This is the currency of notional amount as defined in the contract.
   * The amount will be converted from this reference currency to the swap leg currency
   * when calculating the value of the leg.
   * <p>
   * The reference currency must be one of the two currencies of the index.
   * <p>
   * The reference currency is also known as the <i>constant currency</i>.
   * @return the value of the property, not null
   */
  public Currency getReferenceCurrency() {
    return referenceCurrency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the base date that each FX reset fixing is made relative to, defaulted to 'PeriodStart'.
   * <p>
   * The FX reset fixing date is relative to either the start or end of each accrual period.
   * @return the value of the property, not null
   */
  public FxResetFixingRelativeTo getFixingRelativeTo() {
    return fixingRelativeTo;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the offset of the FX reset fixing date from each adjusted accrual date.
   * <p>
   * The offset is applied to the base date specified by {@code fixingRelativeTo}.
   * The offset is typically a negative number of business days.
   * @return the value of the property, not null
   */
  public DaysAdjustment getFixingOffset() {
    return fixingOffset;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FxResetCalculation other = (FxResetCalculation) obj;
      return JodaBeanUtils.equal(getIndex(), other.getIndex()) &&
          JodaBeanUtils.equal(getReferenceCurrency(), other.getReferenceCurrency()) &&
          JodaBeanUtils.equal(getFixingRelativeTo(), other.getFixingRelativeTo()) &&
          JodaBeanUtils.equal(getFixingOffset(), other.getFixingOffset());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getIndex());
    hash = hash * 31 + JodaBeanUtils.hashCode(getReferenceCurrency());
    hash = hash * 31 + JodaBeanUtils.hashCode(getFixingRelativeTo());
    hash = hash * 31 + JodaBeanUtils.hashCode(getFixingOffset());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("FxResetCalculation{");
    buf.append("index").append('=').append(getIndex()).append(',').append(' ');
    buf.append("referenceCurrency").append('=').append(getReferenceCurrency()).append(',').append(' ');
    buf.append("fixingRelativeTo").append('=').append(getFixingRelativeTo()).append(',').append(' ');
    buf.append("fixingOffset").append('=').append(JodaBeanUtils.toString(getFixingOffset()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FxResetCalculation}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code index} property.
     */
    private final MetaProperty<FxIndex> index = DirectMetaProperty.ofImmutable(
        this, "index", FxResetCalculation.class, FxIndex.class);
    /**
     * The meta-property for the {@code referenceCurrency} property.
     */
    private final MetaProperty<Currency> referenceCurrency = DirectMetaProperty.ofImmutable(
        this, "referenceCurrency", FxResetCalculation.class, Currency.class);
    /**
     * The meta-property for the {@code fixingRelativeTo} property.
     */
    private final MetaProperty<FxResetFixingRelativeTo> fixingRelativeTo = DirectMetaProperty.ofImmutable(
        this, "fixingRelativeTo", FxResetCalculation.class, FxResetFixingRelativeTo.class);
    /**
     * The meta-property for the {@code fixingOffset} property.
     */
    private final MetaProperty<DaysAdjustment> fixingOffset = DirectMetaProperty.ofImmutable(
        this, "fixingOffset", FxResetCalculation.class, DaysAdjustment.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "index",
        "referenceCurrency",
        "fixingRelativeTo",
        "fixingOffset");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case 727652476:  // referenceCurrency
          return referenceCurrency;
        case 232554996:  // fixingRelativeTo
          return fixingRelativeTo;
        case -317508960:  // fixingOffset
          return fixingOffset;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public FxResetCalculation.Builder builder() {
      return new FxResetCalculation.Builder();
    }

    @Override
    public Class<? extends FxResetCalculation> beanType() {
      return FxResetCalculation.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code index} property.
     * @return the meta-property, not null
     */
    public MetaProperty<FxIndex> index() {
      return index;
    }

    /**
     * The meta-property for the {@code referenceCurrency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> referenceCurrency() {
      return referenceCurrency;
    }

    /**
     * The meta-property for the {@code fixingRelativeTo} property.
     * @return the meta-property, not null
     */
    public MetaProperty<FxResetFixingRelativeTo> fixingRelativeTo() {
      return fixingRelativeTo;
    }

    /**
     * The meta-property for the {@code fixingOffset} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DaysAdjustment> fixingOffset() {
      return fixingOffset;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return ((FxResetCalculation) bean).getIndex();
        case 727652476:  // referenceCurrency
          return ((FxResetCalculation) bean).getReferenceCurrency();
        case 232554996:  // fixingRelativeTo
          return ((FxResetCalculation) bean).getFixingRelativeTo();
        case -317508960:  // fixingOffset
          return ((FxResetCalculation) bean).getFixingOffset();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code FxResetCalculation}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<FxResetCalculation> {

    private FxIndex index;
    private Currency referenceCurrency;
    private FxResetFixingRelativeTo fixingRelativeTo;
    private DaysAdjustment fixingOffset;

    /**
     * Restricted constructor.
     */
    private Builder() {
      applyDefaults(this);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(FxResetCalculation beanToCopy) {
      this.index = beanToCopy.getIndex();
      this.referenceCurrency = beanToCopy.getReferenceCurrency();
      this.fixingRelativeTo = beanToCopy.getFixingRelativeTo();
      this.fixingOffset = beanToCopy.getFixingOffset();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case 727652476:  // referenceCurrency
          return referenceCurrency;
        case 232554996:  // fixingRelativeTo
          return fixingRelativeTo;
        case -317508960:  // fixingOffset
          return fixingOffset;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          this.index = (FxIndex) newValue;
          break;
        case 727652476:  // referenceCurrency
          this.referenceCurrency = (Currency) newValue;
          break;
        case 232554996:  // fixingRelativeTo
          this.fixingRelativeTo = (FxResetFixingRelativeTo) newValue;
          break;
        case -317508960:  // fixingOffset
          this.fixingOffset = (DaysAdjustment) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public FxResetCalculation build() {
      return new FxResetCalculation(
          index,
          referenceCurrency,
          fixingRelativeTo,
          fixingOffset);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code index} property in the builder.
     * @param index  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder index(FxIndex index) {
      JodaBeanUtils.notNull(index, "index");
      this.index = index;
      return this;
    }

    /**
     * Sets the {@code referenceCurrency} property in the builder.
     * @param referenceCurrency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder referenceCurrency(Currency referenceCurrency) {
      JodaBeanUtils.notNull(referenceCurrency, "referenceCurrency");
      this.referenceCurrency = referenceCurrency;
      return this;
    }

    /**
     * Sets the {@code fixingRelativeTo} property in the builder.
     * @param fixingRelativeTo  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fixingRelativeTo(FxResetFixingRelativeTo fixingRelativeTo) {
      JodaBeanUtils.notNull(fixingRelativeTo, "fixingRelativeTo");
      this.fixingRelativeTo = fixingRelativeTo;
      return this;
    }

    /**
     * Sets the {@code fixingOffset} property in the builder.
     * @param fixingOffset  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fixingOffset(DaysAdjustment fixingOffset) {
      JodaBeanUtils.notNull(fixingOffset, "fixingOffset");
      this.fixingOffset = fixingOffset;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("FxResetCalculation.Builder{");
      buf.append("index").append('=').append(JodaBeanUtils.toString(index)).append(',').append(' ');
      buf.append("referenceCurrency").append('=').append(JodaBeanUtils.toString(referenceCurrency)).append(',').append(' ');
      buf.append("fixingRelativeTo").append('=').append(JodaBeanUtils.toString(fixingRelativeTo)).append(',').append(' ');
      buf.append("fixingOffset").append('=').append(JodaBeanUtils.toString(fixingOffset));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
