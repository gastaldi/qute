package com.github.mkouba.qute.quarkus.example;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.mkouba.qute.Template;
import com.github.mkouba.qute.Template.Rendering;
import com.github.mkouba.qute.quarkus.ResourcePath;
import com.github.mkouba.qute.quarkus.VariantTemplate;
import com.github.mkouba.qute.quarkus.example.api.MailInstance;
import com.github.mkouba.qute.quarkus.example.api.TemplateInstance;

@Path("/")
public class DetailResource {

    @Inject
    Template detail;

    @ResourcePath("DetailResource/item2")
    VariantTemplate item;

    @Path("item")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String item() {
        return detail.render(new Item("Alpha", BigDecimal.valueOf(1000)));
    }

    @Path("item2")
    @GET
    @Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    public TemplateInstance item2() {
        // TODO remove once we are fine with VariantTemplate API 
        return new TemplateInstance(new Item("Alpha", BigDecimal.valueOf(1000)));
    }

    @Path("item3")
    @GET
    @Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    public Rendering item3() {
        return item.render().setData(new Item("Alpha", BigDecimal.valueOf(1000)));
    }

    @Path("item-mail")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> itemMail() {
        // TODO remove once we have a replacement for MailInstance
        return new MailInstance(new Item("Alpha", BigDecimal.valueOf(1000)))
                .subject("TEST")
                .from("stef@epardaud.fr")
                .to("stef@epardaud.fr")
                .send().thenApply(v -> "OK");
    }

}
