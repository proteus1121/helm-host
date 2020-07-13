package com.ishchenko.artem.helm.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.ishchenko.artem.helm.parsers.HelmAttributeParser;
import com.ishchenko.artem.helm.parsers.ProvenanceParser;
import com.ishchenko.artem.helm.parsers.TgzParser;
import com.ishchenko.artem.helm.parsers.YamlParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelmController
{
  @RequestMapping(value = "/simple", method = RequestMethod.POST)
  @ResponseBody
  public FlexConfig hello(@RequestHeader(AssetKind.NAME) String assetKindHeader, @RequestBody byte[] body)
      throws IOException
  {
    System.out.println("body");
    System.out.println(body);
    if (assetKindHeader == null) {
      //todo AssertKindValidator
      throw new IllegalArgumentException("Header [" + AssetKind.NAME + "] is absent. Possible values: " +
          Arrays.toString(AssetKind.values()));
    }
    AssetKind assetKind = AssetKind.valueOf(assetKindHeader);
    // todo DI
    HelmAttributeParser helmAttributeParser =
        new HelmAttributeParser(new TgzParser(), new YamlParser(), new ProvenanceParser());
    HelmAttributes attributes =
        helmAttributeParser.getAttributes(assetKind, new ByteArrayInputStream(body));
    return new FlexConfig(attributes.getName(), attributes.getVersion(), null);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public Response hello() {
    return new Response(true, "Hello World");
  }
}
