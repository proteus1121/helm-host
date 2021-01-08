package com.ishchenko.artem.helm.main.parsers;

import com.google.common.io.Resources;
import com.ishchenko.artem.helm.main.model.HelmAttributes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "bucket=test-bucket",
})
public class TgzParserTest {
    @Autowired
    private TgzParser tgzParser;
    @Autowired
    private YamlParser yamlParser;

    @Test
    public void getChartFromInputStream() throws IOException {
        try (InputStream is = tgzParser
                .getChartFromInputStream(Resources.getResource("distributed-jmeter-1.0.1.tgz").openStream())) {
            HelmAttributes helmAttributes = new HelmAttributes(yamlParser.load(is));
            Assert.assertEquals(
                    "{DESCRIPTION=A Distributed JMeter Helm chart, HOME=http://jmeter.apache.org/, ICON=http://jmeter.apache.org/images/logo.svg, APP_VERSION=3.3, MAINTAINERS=[{email=pedrocesar.ti@gmail.com, name=pedrocesar-ti}], NAME=distributed-jmeter, SOURCES=[https://github.com/pedrocesar-ti/distributed-jmeter-docker], VERSION=1.0.1}",
                    helmAttributes.toString());
        }
    }
}
