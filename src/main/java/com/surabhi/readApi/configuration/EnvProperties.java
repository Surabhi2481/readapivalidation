package com.surabhi.readApi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources(value = {@PropertySource("classpath:services.properties")})
public class EnvProperties {
    @Value("${abc_mongoUrl}")
    private String abc_mongoUrl;

    @Value("${abc_cassandraUrl}")
    private String abc_cassandraUrl;

    @Value("${def_mongoUrl}")
    private String def_mongoUrl;

    @Value("${def_cassandraUrl}")
    private String def_cassandraUrl;

    public String getAbc_mongoUrl() {
        return abc_mongoUrl;
    }

    public void setAbc_mongoUrl(String abc_mongoUrl) {
        this.abc_mongoUrl = abc_mongoUrl;
    }

    public String getAbc_cassandraUrl() {
        return abc_cassandraUrl;
    }

    public void setAbc_cassandraUrl(String abc_cassandraUrl) {
        this.abc_cassandraUrl = abc_cassandraUrl;
    }

    public String getDef_mongoUrl() {
        return def_mongoUrl;
    }

    public void setDef_mongoUrl(String def_mongoUrl) {
        this.def_mongoUrl = def_mongoUrl;
    }

    public String getDef_cassandraUrl() {
        return def_cassandraUrl;
    }

    public void setDef_cassandraUrl(String def_cassandraUrl) {
        this.def_cassandraUrl = def_cassandraUrl;
    }
}
