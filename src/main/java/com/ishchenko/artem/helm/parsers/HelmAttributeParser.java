package com.ishchenko.artem.helm.parsers;

import java.io.IOException;
import java.io.InputStream;

import com.ishchenko.artem.helm.main.AssetKind;
import com.ishchenko.artem.helm.main.HelmAttributes;

public class HelmAttributeParser
{
  private TgzParser tgzParser;

  private YamlParser yamlParser;

  private ProvenanceParser provenanceParser;

  public HelmAttributeParser(
      final TgzParser tgzParser,
      final YamlParser yamlParser,
      final ProvenanceParser provenanceParser)
  {
    this.tgzParser = tgzParser;
    this.yamlParser = yamlParser;
    this.provenanceParser = provenanceParser;
  }

  public HelmAttributes getAttributes(final AssetKind assetKind, final InputStream inputStream) throws IOException {
    switch (assetKind) {
      case HELM_PACKAGE:
        return getAttributesFromInputStream(inputStream);
      case HELM_PROVENANCE:
        return getAttributesProvenanceFromInputStream(inputStream);
      default:
        return new HelmAttributes();
    }
  }

  private HelmAttributes getAttributesProvenanceFromInputStream(final InputStream inputStream) throws IOException {
    return provenanceParser.parse(inputStream);
  }

  private HelmAttributes getAttributesFromInputStream(final InputStream inputStream) throws IOException {
    try (InputStream is = tgzParser.getChartFromInputStream(inputStream)) {
      return new HelmAttributes(yamlParser.load(is));
    }
  }
}
