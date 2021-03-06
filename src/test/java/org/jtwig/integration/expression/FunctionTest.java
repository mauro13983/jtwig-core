package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FunctionTest {
    @Test
    public void functionCallWithOneArgumentOnly() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ trim ' word ' }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("word"));
    }
}
