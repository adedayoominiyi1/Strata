/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.engine.marketdata;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.engine.marketdata.scenarios.CellKey;
import com.opengamma.strata.engine.marketdata.scenarios.LocalScenarioDefinition;

/**
 *
 */
@BeanDefinition
public final class LocalScenarioDefinitions implements ImmutableBean {

  private static final LocalScenarioDefinitions EMPTY = new LocalScenarioDefinitions(ImmutableList.of(), ImmutableMap.of());

  /** . */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableList<LocalScenarioDefinition> scenarioDefinitions;

  /** . */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableMap<CellKey, ImmutableList<Integer>> cellScenarioDefinitions;

  public static LocalScenarioDefinitions empty() {
    return EMPTY;
  }

  /**
   * Returns local scenario definitions for a set of tasks that perform calculations.
   *
   * @param taskRequirements  requirements for a set of tasks that perform calculations
   * @return local scenario definitions for a set of tasks that perform calculations
   */
  public static LocalScenarioDefinitions of(List<CalculationTaskRequirements> taskRequirements) {
    Map<LocalScenarioDefinition, Integer> localScenariosBuilder = new LinkedHashMap<>();
    // This isn't a builder because we need to call containsKey()
    Map<CellKey, ImmutableList<Integer>> cellScenariosBuilder = new HashMap<>();

    for (CalculationTaskRequirements requirements : taskRequirements) {
      ImmutableList.Builder<Integer> scenarioIndexBuilder = ImmutableList.builder();

      for (LocalScenarioDefinition scenario : requirements.getLocalScenarios()) {
        Integer existingIndex = localScenariosBuilder.get(scenario);

        if (existingIndex != null) {
          scenarioIndexBuilder.add(existingIndex);
        } else {
          int newIndex = localScenariosBuilder.size();
          localScenariosBuilder.put(scenario, newIndex);
          scenarioIndexBuilder.add(newIndex);
        }
      }
      CellKey key = CellKey.of(requirements.getRowIndex(), requirements.getColumnIndex());

      if (cellScenariosBuilder.containsKey(key)) {
        // TODO
        throw new IllegalArgumentException();
      }
      cellScenariosBuilder.put(key, scenarioIndexBuilder.build());

    }
    return new LocalScenarioDefinitions(
        ImmutableList.copyOf(localScenariosBuilder.keySet()),
        ImmutableMap.copyOf(cellScenariosBuilder));
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code LocalScenarioDefinitions}.
   * @return the meta-bean, not null
   */
  public static LocalScenarioDefinitions.Meta meta() {
    return LocalScenarioDefinitions.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(LocalScenarioDefinitions.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static LocalScenarioDefinitions.Builder builder() {
    return new LocalScenarioDefinitions.Builder();
  }

  private LocalScenarioDefinitions(
      List<LocalScenarioDefinition> scenarioDefinitions,
      Map<CellKey, ImmutableList<Integer>> cellScenarioDefinitions) {
    JodaBeanUtils.notNull(scenarioDefinitions, "scenarioDefinitions");
    JodaBeanUtils.notNull(cellScenarioDefinitions, "cellScenarioDefinitions");
    this.scenarioDefinitions = ImmutableList.copyOf(scenarioDefinitions);
    this.cellScenarioDefinitions = ImmutableMap.copyOf(cellScenarioDefinitions);
  }

  @Override
  public LocalScenarioDefinitions.Meta metaBean() {
    return LocalScenarioDefinitions.Meta.INSTANCE;
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
   * Gets .
   * @return the value of the property, not null
   */
  public ImmutableList<LocalScenarioDefinition> getScenarioDefinitions() {
    return scenarioDefinitions;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets .
   * @return the value of the property, not null
   */
  public ImmutableMap<CellKey, ImmutableList<Integer>> getCellScenarioDefinitions() {
    return cellScenarioDefinitions;
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
      LocalScenarioDefinitions other = (LocalScenarioDefinitions) obj;
      return JodaBeanUtils.equal(getScenarioDefinitions(), other.getScenarioDefinitions()) &&
          JodaBeanUtils.equal(getCellScenarioDefinitions(), other.getCellScenarioDefinitions());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getScenarioDefinitions());
    hash = hash * 31 + JodaBeanUtils.hashCode(getCellScenarioDefinitions());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("LocalScenarioDefinitions{");
    buf.append("scenarioDefinitions").append('=').append(getScenarioDefinitions()).append(',').append(' ');
    buf.append("cellScenarioDefinitions").append('=').append(JodaBeanUtils.toString(getCellScenarioDefinitions()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code LocalScenarioDefinitions}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code scenarioDefinitions} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableList<LocalScenarioDefinition>> scenarioDefinitions = DirectMetaProperty.ofImmutable(
        this, "scenarioDefinitions", LocalScenarioDefinitions.class, (Class) ImmutableList.class);
    /**
     * The meta-property for the {@code cellScenarioDefinitions} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<CellKey, ImmutableList<Integer>>> cellScenarioDefinitions = DirectMetaProperty.ofImmutable(
        this, "cellScenarioDefinitions", LocalScenarioDefinitions.class, (Class) ImmutableMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "scenarioDefinitions",
        "cellScenarioDefinitions");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 56152016:  // scenarioDefinitions
          return scenarioDefinitions;
        case 449543950:  // cellScenarioDefinitions
          return cellScenarioDefinitions;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public LocalScenarioDefinitions.Builder builder() {
      return new LocalScenarioDefinitions.Builder();
    }

    @Override
    public Class<? extends LocalScenarioDefinitions> beanType() {
      return LocalScenarioDefinitions.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code scenarioDefinitions} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableList<LocalScenarioDefinition>> scenarioDefinitions() {
      return scenarioDefinitions;
    }

    /**
     * The meta-property for the {@code cellScenarioDefinitions} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<CellKey, ImmutableList<Integer>>> cellScenarioDefinitions() {
      return cellScenarioDefinitions;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 56152016:  // scenarioDefinitions
          return ((LocalScenarioDefinitions) bean).getScenarioDefinitions();
        case 449543950:  // cellScenarioDefinitions
          return ((LocalScenarioDefinitions) bean).getCellScenarioDefinitions();
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
   * The bean-builder for {@code LocalScenarioDefinitions}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<LocalScenarioDefinitions> {

    private List<LocalScenarioDefinition> scenarioDefinitions = ImmutableList.of();
    private Map<CellKey, ImmutableList<Integer>> cellScenarioDefinitions = ImmutableMap.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(LocalScenarioDefinitions beanToCopy) {
      this.scenarioDefinitions = beanToCopy.getScenarioDefinitions();
      this.cellScenarioDefinitions = beanToCopy.getCellScenarioDefinitions();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 56152016:  // scenarioDefinitions
          return scenarioDefinitions;
        case 449543950:  // cellScenarioDefinitions
          return cellScenarioDefinitions;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 56152016:  // scenarioDefinitions
          this.scenarioDefinitions = (List<LocalScenarioDefinition>) newValue;
          break;
        case 449543950:  // cellScenarioDefinitions
          this.cellScenarioDefinitions = (Map<CellKey, ImmutableList<Integer>>) newValue;
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
    public LocalScenarioDefinitions build() {
      return new LocalScenarioDefinitions(
          scenarioDefinitions,
          cellScenarioDefinitions);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code scenarioDefinitions} property in the builder.
     * @param scenarioDefinitions  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder scenarioDefinitions(List<LocalScenarioDefinition> scenarioDefinitions) {
      JodaBeanUtils.notNull(scenarioDefinitions, "scenarioDefinitions");
      this.scenarioDefinitions = scenarioDefinitions;
      return this;
    }

    /**
     * Sets the {@code scenarioDefinitions} property in the builder
     * from an array of objects.
     * @param scenarioDefinitions  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder scenarioDefinitions(LocalScenarioDefinition... scenarioDefinitions) {
      return scenarioDefinitions(ImmutableList.copyOf(scenarioDefinitions));
    }

    /**
     * Sets the {@code cellScenarioDefinitions} property in the builder.
     * @param cellScenarioDefinitions  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder cellScenarioDefinitions(Map<CellKey, ImmutableList<Integer>> cellScenarioDefinitions) {
      JodaBeanUtils.notNull(cellScenarioDefinitions, "cellScenarioDefinitions");
      this.cellScenarioDefinitions = cellScenarioDefinitions;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("LocalScenarioDefinitions.Builder{");
      buf.append("scenarioDefinitions").append('=').append(JodaBeanUtils.toString(scenarioDefinitions)).append(',').append(' ');
      buf.append("cellScenarioDefinitions").append('=').append(JodaBeanUtils.toString(cellScenarioDefinitions));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
