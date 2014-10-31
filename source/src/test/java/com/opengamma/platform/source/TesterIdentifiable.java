/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.source;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.platform.source.id.IdentifiableBean;
import com.opengamma.platform.source.id.StandardId;

/**
 * Simple bean for testing.
 */
@BeanDefinition
public final class TesterIdentifiable implements IdentifiableBean, ImmutableBean, Tester {

  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final StandardId standardId;

  @PropertyDefinition
  private final String name;

  @PropertyDefinition
  private final int widgetCount;

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code TesterIdentifiable}.
   * @return the meta-bean, not null
   */
  public static TesterIdentifiable.Meta meta() {
    return TesterIdentifiable.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(TesterIdentifiable.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static TesterIdentifiable.Builder builder() {
    return new TesterIdentifiable.Builder();
  }

  private TesterIdentifiable(
      StandardId standardId,
      String name,
      int widgetCount) {
    JodaBeanUtils.notNull(standardId, "standardId");
    this.standardId = standardId;
    this.name = name;
    this.widgetCount = widgetCount;
  }

  @Override
  public TesterIdentifiable.Meta metaBean() {
    return TesterIdentifiable.Meta.INSTANCE;
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
   * Gets the standardId.
   * @return the value of the property, not null
   */
  @Override
  public StandardId getStandardId() {
    return standardId;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name.
   * @return the value of the property
   */
  public String getName() {
    return name;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the widgetCount.
   * @return the value of the property
   */
  public int getWidgetCount() {
    return widgetCount;
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
      TesterIdentifiable other = (TesterIdentifiable) obj;
      return JodaBeanUtils.equal(getStandardId(), other.getStandardId()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          (getWidgetCount() == other.getWidgetCount());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getStandardId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getWidgetCount());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("TesterIdentifiable{");
    buf.append("standardId").append('=').append(getStandardId()).append(',').append(' ');
    buf.append("name").append('=').append(getName()).append(',').append(' ');
    buf.append("widgetCount").append('=').append(JodaBeanUtils.toString(getWidgetCount()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code TesterIdentifiable}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code standardId} property.
     */
    private final MetaProperty<StandardId> standardId = DirectMetaProperty.ofImmutable(
        this, "standardId", TesterIdentifiable.class, StandardId.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> name = DirectMetaProperty.ofImmutable(
        this, "name", TesterIdentifiable.class, String.class);
    /**
     * The meta-property for the {@code widgetCount} property.
     */
    private final MetaProperty<Integer> widgetCount = DirectMetaProperty.ofImmutable(
        this, "widgetCount", TesterIdentifiable.class, Integer.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "standardId",
        "name",
        "widgetCount");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return standardId;
        case 3373707:  // name
          return name;
        case -949038805:  // widgetCount
          return widgetCount;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public TesterIdentifiable.Builder builder() {
      return new TesterIdentifiable.Builder();
    }

    @Override
    public Class<? extends TesterIdentifiable> beanType() {
      return TesterIdentifiable.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code standardId} property.
     * @return the meta-property, not null
     */
    public MetaProperty<StandardId> standardId() {
      return standardId;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> name() {
      return name;
    }

    /**
     * The meta-property for the {@code widgetCount} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> widgetCount() {
      return widgetCount;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return ((TesterIdentifiable) bean).getStandardId();
        case 3373707:  // name
          return ((TesterIdentifiable) bean).getName();
        case -949038805:  // widgetCount
          return ((TesterIdentifiable) bean).getWidgetCount();
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
   * The bean-builder for {@code TesterIdentifiable}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<TesterIdentifiable> {

    private StandardId standardId;
    private String name;
    private int widgetCount;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(TesterIdentifiable beanToCopy) {
      this.standardId = beanToCopy.getStandardId();
      this.name = beanToCopy.getName();
      this.widgetCount = beanToCopy.getWidgetCount();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return standardId;
        case 3373707:  // name
          return name;
        case -949038805:  // widgetCount
          return widgetCount;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          this.standardId = (StandardId) newValue;
          break;
        case 3373707:  // name
          this.name = (String) newValue;
          break;
        case -949038805:  // widgetCount
          this.widgetCount = (Integer) newValue;
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
    public TesterIdentifiable build() {
      return new TesterIdentifiable(
          standardId,
          name,
          widgetCount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code standardId} property in the builder.
     * @param standardId  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder standardId(StandardId standardId) {
      JodaBeanUtils.notNull(standardId, "standardId");
      this.standardId = standardId;
      return this;
    }

    /**
     * Sets the {@code name} property in the builder.
     * @param name  the new value
     * @return this, for chaining, not null
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the {@code widgetCount} property in the builder.
     * @param widgetCount  the new value
     * @return this, for chaining, not null
     */
    public Builder widgetCount(int widgetCount) {
      this.widgetCount = widgetCount;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("TesterIdentifiable.Builder{");
      buf.append("standardId").append('=').append(JodaBeanUtils.toString(standardId)).append(',').append(' ');
      buf.append("name").append('=').append(JodaBeanUtils.toString(name)).append(',').append(' ');
      buf.append("widgetCount").append('=').append(JodaBeanUtils.toString(widgetCount));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
