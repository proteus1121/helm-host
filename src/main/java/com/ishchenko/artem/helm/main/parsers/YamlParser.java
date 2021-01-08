package com.ishchenko.artem.helm.main.parsers;/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2018-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishchenko.artem.helm.main.model.ChartIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service for getting attributes from yaml files, writing to yaml files
 */
@Service
public class YamlParser
{
  private ObjectMapper mapper;

  @Autowired
  public YamlParser(@Qualifier("helm") ObjectMapper mapper) {
    this.mapper = mapper;
  }

  private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE
          = new TypeReference<Map<String, Object>>() { };

  public Map<String, Object> load(InputStream is) throws IOException {
    checkNotNull(is);
    return mapper.readValue(is, MAP_TYPE_REFERENCE);
  }

  public String getYamlContent(final ChartIndex index) {
    try {
      return mapper.writeValueAsString(index);
    }
    catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  public void write(final OutputStream os, final ChartIndex index) {
    try (OutputStreamWriter writer = new OutputStreamWriter(os)) {
      String result = getYamlContent(index);
      writer.write(result);
    }
    catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }
}
