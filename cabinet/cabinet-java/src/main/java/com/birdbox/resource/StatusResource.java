package com.birdbox.cabinet.resource;

import com.birdbox.cabinet.core.Status;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {
    private final String template;
    private final String defaultStatus;

    public StatusResource(String template, String defaultStatus) {
        this.template = template;
        this.defaultStatus = defaultStatus;
    }

    @GET
    @Timed
    public Status render(@QueryParam("name") Optional<String> name) {
        return new Status(String.format(template, name.or(defaultStatus)));
    }
}
