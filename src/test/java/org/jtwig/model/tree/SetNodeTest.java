package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SetNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final VariableExpression variableExpression = mock(VariableExpression.class);
    private final Expression expression = mock(Expression.class);
    private SetNode underTest = new SetNode(position, variableExpression, expression);

    @Test
    public void render() throws Exception {
        Object value = new Object();
        when(variableExpression.getIdentifier()).thenReturn("one");
        when(expression.calculate(renderContext())).thenReturn(JtwigValueFactory.value(value, new DefaultValueConfiguration()));

        Renderable result = underTest.render(renderContext());

        verify(renderContext().valueContext()).add(eq("one"), eq(value));
        assertThat(renderResult(result), is(""));
    }
}