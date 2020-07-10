package com.ishchenko.artem.helm.main;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Base64;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ishchenko.artem.helm.parsers.HelmAttributeParser;
import com.ishchenko.artem.helm.parsers.ProvenanceParser;
import com.ishchenko.artem.helm.parsers.TgzParser;
import com.ishchenko.artem.helm.parsers.YamlParser;
import lombok.SneakyThrows;

public class Handler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @SneakyThrows
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
    String assetKindHeader = input.getHeaders().get(AssetKind.NAME);
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
        helmAttributeParser.getAttributes(assetKind, new ByteArrayInputStream(Base64.getDecoder().decode(input.getBody())));

    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
    responseEvent.setBody(gson.toJson(attributes));
    responseEvent.setStatusCode(200);

    return responseEvent;
  }
}
