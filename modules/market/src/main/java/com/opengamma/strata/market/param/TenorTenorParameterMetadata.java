/*
 * Copyright (C) 2019 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.param;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.ImmutablePreBuild;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.collect.tuple.Pair;

/**
 * Parameter metadata based on an expiry tenor, an underlying tenor and their respective year fractions.
 */
@BeanDefinition(builderScope = "private")
public final class TenorTenorParameterMetadata
    implements ParameterMetadata, ImmutableBean, Serializable {

  /**
   * The expiry tenor associated with the parameter.
   */
  @PropertyDefinition(validate = "notNull")
  private final Tenor expiryTenor;
  /**
   * The underlying tenor associated with the parameter.
   */
  @PropertyDefinition(validate = "notNull")
  private final Tenor underlyingTenor;
  /**
   * The expiry tenor year fraction.
   */
  @PropertyDefinition
  private final double expiryYearFraction;
  /**
   * The underlying tenor year fraction.
   */
  @PropertyDefinition
  private final double underlyingTenorYearFraction;
  /**
   * The label that describes the parameter, defaulted to both tenors.
   */
  @PropertyDefinition(validate = "notEmpty", overrideGet = true)
  private final String label;

  //-------------------------------------------------------------------------

  /**
   * Creates node metadata with expiry tenor and underlying tenor.
   *
   * @param expiryTenor the expiry tenor
   * @param underlyingTenor the underlying
   * @param expiryTenorYearFraction the expiry tenor year fraction
   * @param underlyingTenorYearFraction the underlying tenor year fraction
   * @return the metadata
   */
  public static TenorTenorParameterMetadata of(
      Tenor expiryTenor,
      Tenor underlyingTenor,
      double expiryTenorYearFraction,
      double underlyingTenorYearFraction) {

    String label = Pair.of(expiryTenor, underlyingTenor).toString();
    return new TenorTenorParameterMetadata(
        expiryTenor,
        underlyingTenor,
        expiryTenorYearFraction,
        underlyingTenorYearFraction,
        label);
  }

  /**
   * Creates node metadata with expiry tenor, underlying tenor and label.
   *
   * @param expiryTenor the expiry tenor
   * @param underlyingTenor the underlying
   * @param expiryTenorYearFraction the expiry tenor year fraction
   * @param underlyingTenorYearFraction the underlying tenor year fraction
   * @param label the label
   * @return the metadata
   */
  public static TenorTenorParameterMetadata of(
      Tenor expiryTenor,
      Tenor underlyingTenor,
      double expiryTenorYearFraction,
      double underlyingTenorYearFraction,
      String label) {

    return new TenorTenorParameterMetadata(
        expiryTenor,
        underlyingTenor,
        expiryTenorYearFraction,
        underlyingTenorYearFraction,
        label);
  }

  @ImmutablePreBuild
  private static void preBuild(Builder builder) {
    if (builder.label == null) {
      builder.label = Pair.of(builder.expiryTenor, builder.underlyingTenor).toString();
    }
  }

  @Override
  public Pair<Tenor, Tenor> getIdentifier() {
    return Pair.of(expiryTenor, underlyingTenor);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code TenorTenorParameterMetadata}.
   * @return the meta-bean, not null
   */
  public static TenorTenorParameterMetadata.Meta meta() {
    return TenorTenorParameterMetadata.Meta.INSTANCE;
  }

  static {
    MetaBean.register(TenorTenorParameterMetadata.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private TenorTenorParameterMetadata(
      Tenor expiryTenor,
      Tenor underlyingTenor,
      double expiryYearFraction,
      double underlyingTenorYearFraction,
      String label) {
    JodaBeanUtils.notNull(expiryTenor, "expiryTenor");
    JodaBeanUtils.notNull(underlyingTenor, "underlyingTenor");
    JodaBeanUtils.notEmpty(label, "label");
    this.expiryTenor = expiryTenor;
    this.underlyingTenor = underlyingTenor;
    this.expiryYearFraction = expiryYearFraction;
    this.underlyingTenorYearFraction = underlyingTenorYearFraction;
    this.label = label;
  }

  @Override
  public TenorTenorParameterMetadata.Meta metaBean() {
    return TenorTenorParameterMetadata.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the expiry tenor associated with the parameter.
   * @return the value of the property, not null
   */
  public Tenor getExpiryTenor() {
    return expiryTenor;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying tenor associated with the parameter.
   * @return the value of the property, not null
   */
  public Tenor getUnderlyingTenor() {
    return underlyingTenor;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the expiry tenor year fraction.
   * @return the value of the property
   */
  public double getExpiryYearFraction() {
    return expiryYearFraction;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying tenor year fraction.
   * @return the value of the property
   */
  public double getUnderlyingTenorYearFraction() {
    return underlyingTenorYearFraction;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the label that describes the parameter, defaulted to both tenors.
   * @return the value of the property, not empty
   */
  @Override
  public String getLabel() {
    return label;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      TenorTenorParameterMetadata other = (TenorTenorParameterMetadata) obj;
      return JodaBeanUtils.equal(expiryTenor, other.expiryTenor) &&
          JodaBeanUtils.equal(underlyingTenor, other.underlyingTenor) &&
          JodaBeanUtils.equal(expiryYearFraction, other.expiryYearFraction) &&
          JodaBeanUtils.equal(underlyingTenorYearFraction, other.underlyingTenorYearFraction) &&
          JodaBeanUtils.equal(label, other.label);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(expiryTenor);
    hash = hash * 31 + JodaBeanUtils.hashCode(underlyingTenor);
    hash = hash * 31 + JodaBeanUtils.hashCode(expiryYearFraction);
    hash = hash * 31 + JodaBeanUtils.hashCode(underlyingTenorYearFraction);
    hash = hash * 31 + JodaBeanUtils.hashCode(label);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("TenorTenorParameterMetadata{");
    buf.append("expiryTenor").append('=').append(expiryTenor).append(',').append(' ');
    buf.append("underlyingTenor").append('=').append(underlyingTenor).append(',').append(' ');
    buf.append("expiryYearFraction").append('=').append(expiryYearFraction).append(',').append(' ');
    buf.append("underlyingTenorYearFraction").append('=').append(underlyingTenorYearFraction).append(',').append(' ');
    buf.append("label").append('=').append(JodaBeanUtils.toString(label));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code TenorTenorParameterMetadata}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code expiryTenor} property.
     */
    private final MetaProperty<Tenor> expiryTenor = DirectMetaProperty.ofImmutable(
        this, "expiryTenor", TenorTenorParameterMetadata.class, Tenor.class);
    /**
     * The meta-property for the {@code underlyingTenor} property.
     */
    private final MetaProperty<Tenor> underlyingTenor = DirectMetaProperty.ofImmutable(
        this, "underlyingTenor", TenorTenorParameterMetadata.class, Tenor.class);
    /**
     * The meta-property for the {@code expiryYearFraction} property.
     */
    private final MetaProperty<Double> expiryYearFraction = DirectMetaProperty.ofImmutable(
        this, "expiryYearFraction", TenorTenorParameterMetadata.class, Double.TYPE);
    /**
     * The meta-property for the {@code underlyingTenorYearFraction} property.
     */
    private final MetaProperty<Double> underlyingTenorYearFraction = DirectMetaProperty.ofImmutable(
        this, "underlyingTenorYearFraction", TenorTenorParameterMetadata.class, Double.TYPE);
    /**
     * The meta-property for the {@code label} property.
     */
    private final MetaProperty<String> label = DirectMetaProperty.ofImmutable(
        this, "label", TenorTenorParameterMetadata.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "expiryTenor",
        "underlyingTenor",
        "expiryYearFraction",
        "underlyingTenorYearFraction",
        "label");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 465802573:  // expiryTenor
          return expiryTenor;
        case -824175261:  // underlyingTenor
          return underlyingTenor;
        case -616415694:  // expiryYearFraction
          return expiryYearFraction;
        case 497454690:  // underlyingTenorYearFraction
          return underlyingTenorYearFraction;
        case 102727412:  // label
          return label;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends TenorTenorParameterMetadata> builder() {
      return new TenorTenorParameterMetadata.Builder();
    }

    @Override
    public Class<? extends TenorTenorParameterMetadata> beanType() {
      return TenorTenorParameterMetadata.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code expiryTenor} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Tenor> expiryTenor() {
      return expiryTenor;
    }

    /**
     * The meta-property for the {@code underlyingTenor} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Tenor> underlyingTenor() {
      return underlyingTenor;
    }

    /**
     * The meta-property for the {@code expiryYearFraction} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> expiryYearFraction() {
      return expiryYearFraction;
    }

    /**
     * The meta-property for the {@code underlyingTenorYearFraction} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> underlyingTenorYearFraction() {
      return underlyingTenorYearFraction;
    }

    /**
     * The meta-property for the {@code label} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> label() {
      return label;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 465802573:  // expiryTenor
          return ((TenorTenorParameterMetadata) bean).getExpiryTenor();
        case -824175261:  // underlyingTenor
          return ((TenorTenorParameterMetadata) bean).getUnderlyingTenor();
        case -616415694:  // expiryYearFraction
          return ((TenorTenorParameterMetadata) bean).getExpiryYearFraction();
        case 497454690:  // underlyingTenorYearFraction
          return ((TenorTenorParameterMetadata) bean).getUnderlyingTenorYearFraction();
        case 102727412:  // label
          return ((TenorTenorParameterMetadata) bean).getLabel();
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
   * The bean-builder for {@code TenorTenorParameterMetadata}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<TenorTenorParameterMetadata> {

    private Tenor expiryTenor;
    private Tenor underlyingTenor;
    private double expiryYearFraction;
    private double underlyingTenorYearFraction;
    private String label;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 465802573:  // expiryTenor
          return expiryTenor;
        case -824175261:  // underlyingTenor
          return underlyingTenor;
        case -616415694:  // expiryYearFraction
          return expiryYearFraction;
        case 497454690:  // underlyingTenorYearFraction
          return underlyingTenorYearFraction;
        case 102727412:  // label
          return label;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 465802573:  // expiryTenor
          this.expiryTenor = (Tenor) newValue;
          break;
        case -824175261:  // underlyingTenor
          this.underlyingTenor = (Tenor) newValue;
          break;
        case -616415694:  // expiryYearFraction
          this.expiryYearFraction = (Double) newValue;
          break;
        case 497454690:  // underlyingTenorYearFraction
          this.underlyingTenorYearFraction = (Double) newValue;
          break;
        case 102727412:  // label
          this.label = (String) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public TenorTenorParameterMetadata build() {
      preBuild(this);
      return new TenorTenorParameterMetadata(
          expiryTenor,
          underlyingTenor,
          expiryYearFraction,
          underlyingTenorYearFraction,
          label);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("TenorTenorParameterMetadata.Builder{");
      buf.append("expiryTenor").append('=').append(JodaBeanUtils.toString(expiryTenor)).append(',').append(' ');
      buf.append("underlyingTenor").append('=').append(JodaBeanUtils.toString(underlyingTenor)).append(',').append(' ');
      buf.append("expiryYearFraction").append('=').append(JodaBeanUtils.toString(expiryYearFraction)).append(',').append(' ');
      buf.append("underlyingTenorYearFraction").append('=').append(JodaBeanUtils.toString(underlyingTenorYearFraction)).append(',').append(' ');
      buf.append("label").append('=').append(JodaBeanUtils.toString(label));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}