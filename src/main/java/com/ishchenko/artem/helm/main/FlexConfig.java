package com.ishchenko.artem.helm.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlexConfig
{
  private String name;

  private String version;

  private String group;
}
