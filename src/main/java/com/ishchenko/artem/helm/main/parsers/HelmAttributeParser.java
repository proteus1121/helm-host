package com.ishchenko.artem.helm.main.parsers;

import com.ishchenko.artem.helm.main.model.AssetKind;
import com.ishchenko.artem.helm.main.model.HelmAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

  public HelmAttributes getAttributes(final AssetKind assetKind, final byte[] bytes) throws IOException {
    try (InputStream is = new ByteArrayInputStream(bytes)) {
      switch (assetKind) {
        case HELM_PACKAGE:
          return getAttributesFromInputStream(is);
        case HELM_PROVENANCE:
          return getAttributesProvenanceFromInputStream(is);
        default:
          return new HelmAttributes();
      }
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
