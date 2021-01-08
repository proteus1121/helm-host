package com.ishchenko.artem.helm.main.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

@Data
public class ChartEntry {
    private String description;
    private String name;
    private String version;
    private DateTime created;
    private String appVersion;
    private String digest;
    private String icon;
    private List<String> urls;
    private List<String> sources;
    private List<Map<String, String>> maintainers;
}
