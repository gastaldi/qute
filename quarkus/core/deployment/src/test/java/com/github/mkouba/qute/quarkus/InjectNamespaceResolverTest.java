package com.github.mkouba.qute.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.mkouba.qute.Template;

import io.quarkus.test.QuarkusUnitTest;

public class InjectNamespaceResolverTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(SimpleBean.class, Hello.class)
                    .addAsResource(new StringAsset("{inject:hello.ping}"), "META-INF/resources/templates/foo.html"));

    @Inject
    SimpleBean simpleBean;

    @Test
    public void testInjection() {
        assertEquals("pong", simpleBean.foo.render().getResult());
    }

    @Dependent
    public static class SimpleBean {

        @Inject
        Template foo;

    }

}
