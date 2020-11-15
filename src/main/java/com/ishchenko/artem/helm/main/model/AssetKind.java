package com.ishchenko.artem.helm.main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AssetKind
{
  HELM_INDEX(".yaml"),
  HELM_PROVENANCE(".tgz.prov"),
  HELM_PACKAGE(".tgz");

  public static final String NAME = "AssetKind";

  @Getter
  private final String extension;

  public static AssetKind getAssetKindByFileName(final String name) {
    if (name.endsWith(HELM_PACKAGE.getExtension())) {
      return AssetKind.HELM_PACKAGE;
    }
    else if (name.endsWith(HELM_PROVENANCE.getExtension())) {
      return AssetKind.HELM_PROVENANCE;
    }
    return AssetKind.HELM_INDEX;
  }
}
