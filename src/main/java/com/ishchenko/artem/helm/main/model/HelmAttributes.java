package com.ishchenko.artem.helm.main.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HelmAttributes
{
  private final Map<HelmProperties, Object> attributesEnumMap;

  public HelmAttributes() {
    attributesEnumMap = new EnumMap<>(HelmProperties.class);
  }

  public HelmAttributes(final Map<String, Object> attributesMap) {
    attributesEnumMap = new EnumMap<>(HelmProperties.class);
    attributesMap.forEach((key, value) -> {
      Optional<HelmProperties> propertyOpt = HelmProperties.findByPropertyName(key);
      if (value != null && propertyOpt.isPresent()) {
        attributesEnumMap.put(propertyOpt.get(), value);
      }
    });
  }

  public String getName() {
    return getValue(HelmProperties.NAME, String.class);
  }

  public String getVersion() {
    return getValue(HelmProperties.VERSION, String.class);
  }

  public String getAppVersion() {
    return getValue(HelmProperties.APP_VERSION, String.class);
  }

  public String getDescription() {
    return getValue(HelmProperties.DESCRIPTION, String.class);
  }

  public String getIcon() {
    return getValue(HelmProperties.ICON, String.class);
  }

  public List<Map<String, String>> getMaintainers() {

    return getValue(HelmProperties.MAINTAINERS, List.class);
  }

  public List<String> getSources() {
    return getValue(HelmProperties.SOURCES, List.class);
  }

  public void setName(final String name) {
    attributesEnumMap.put(HelmProperties.NAME, name);
  }

  public void setDescription(final String description) {
    attributesEnumMap.put(HelmProperties.DESCRIPTION, description);
  }

  public void setVersion(final String version) {
    attributesEnumMap.put(HelmProperties.VERSION, version);
  }

  public void setIcon(final String icon) {
    attributesEnumMap.put(HelmProperties.ICON, icon);
  }

  public void setAppVersion(final String appVersion) {
    attributesEnumMap.put(HelmProperties.APP_VERSION, appVersion);
  }

  private <T> T getValue(HelmProperties property, Class<T> tClass) {
    return tClass.cast(attributesEnumMap.get(property));
  }

  @Override
  public String toString() {
    return attributesEnumMap.toString();
  }
}


