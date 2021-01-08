package com.ishchenko.artem.helm.main.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ishchenko.artem.helm.main.util.JodaDateTimeDeserializer;
import com.ishchenko.artem.helm.main.util.JodaDateTimeSerializer;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ChartIndex {
    private String apiVersion;
    private Map<String, List<ChartEntry>> entries;
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    private DateTime generated;

    public ChartIndex() {
        this.entries = new HashMap<>();
    }
}
