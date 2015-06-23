package com.birdbox.cabinet;

import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CabinetConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultStatus = "ok";

    public String getTemplate() {
        return template;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }
}
