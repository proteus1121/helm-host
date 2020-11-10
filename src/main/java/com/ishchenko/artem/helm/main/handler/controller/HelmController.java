package com.ishchenko.artem.helm.main.handler.controller;

import com.ishchenko.artem.helm.main.model.AssetKind;
import com.ishchenko.artem.helm.main.model.FlexConfig;
import com.ishchenko.artem.helm.main.model.HelmAttributes;
import com.ishchenko.artem.helm.main.model.Response;
import com.ishchenko.artem.helm.main.parsers.HelmAttributeParser;
import com.ishchenko.artem.helm.main.parsers.ProvenanceParser;
import com.ishchenko.artem.helm.main.parsers.TgzParser;
import com.ishchenko.artem.helm.main.parsers.YamlParser;
import com.ishchenko.artem.helm.main.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class HelmController
{
  private S3Service s3Service;
  private String bucket;

  @Autowired
  public HelmController(S3Service s3Service, @Value("${bucket}") String bucket) {
    this.s3Service = s3Service;
    this.bucket = bucket;
  }

  @RequestMapping(value = "/package/upload", method = RequestMethod.POST)
  @ResponseBody
  public FlexConfig upload(@RequestParam("file") MultipartFile body)
      throws IOException
  {
    HelmAttributeParser helmAttributeParser =
        new HelmAttributeParser(new TgzParser(), new YamlParser(), new ProvenanceParser());
    HelmAttributes attributes =
        helmAttributeParser.getAttributes(AssetKind.HELM_PACKAGE, body.getBytes());

    s3Service.put(bucket, body.getOriginalFilename(), body.getBytes());
    return new FlexConfig(attributes.getName(), attributes.getVersion(), null);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public Response hello() {
    return new Response(true, "Hello World");
  }
}
