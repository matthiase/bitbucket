package com.birdbox.cabinet;

import com.birdbox.cabinet.resource.StatusResource;
import com.birdbox.cabinet.health.TemplateHealthCheck;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class CabinetService extends Service<CabinetConfiguration> {
    public static void main(String[] args) throws Exception {
        new CabinetService().run(args);
    }

    @Override
    public void initialize(Bootstrap<CabinetConfiguration> bootstrap) {
        bootstrap.setName("status");
    }

    @Override
    public void run(CabinetConfiguration configuration,
                    Environment environment) {
      final String template = configuration.getTemplate();
      final String defaultStatus = configuration.getDefaultStatus();
      environment.addResource(new StatusResource(template, defaultStatus));
      environment.addHealthCheck(new TemplateHealthCheck(template));
    }

}
