package com.ishchenko.artem.helm.main.model;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HelmProperties
{
  DESCRIPTION("description"),
  ENGINE("engine"),
  HOME("home"),
  ICON("icon"),
  APP_VERSION("appVersion"),
  KEYWORDS("keywords"),
  MAINTAINERS("maintainers"),
  NAME("name"),
  SOURCES("sources"),
  VERSION("version");

  @Getter
  private String propertyName;

  public static Optional<HelmProperties> findByPropertyName(String propertyName) {
    return Arrays.stream(HelmProperties.values())
        .filter(properties -> propertyName.equals(properties.getPropertyName()))
        .findAny();
  }
}
