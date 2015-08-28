package org.jtwig.model.tree;

import com.google.common.base.Optional;

import org.jtwig.context.RenderContext;
import org.jtwig.context.values.NewlyScopedValueContext;
import org.jtwig.context.values.ScopeType;
import org.jtwig.context.values.ValueContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.CompositeRenderable;
import org.jtwig.util.Cursor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class ForLoopNode extends ContentNode {
    private final Optional<VariableExpression> keyVariableExpression;
    private final VariableExpression variableExpression;
    private final Expression expression;

    public ForLoopNode(Position position, VariableExpression keyVariableExpression, VariableExpression variableExpression, Expression expression, Node content) {
        super(position, content);
        if (variableExpression == null) {
            this.variableExpression = keyVariableExpression;
            this.keyVariableExpression = Optional.absent();
        } else {
            this.keyVariableExpression = Optional.of(keyVariableExpression);
            this.variableExpression = variableExpression;
        }
        this.expression = expression;
    }

    public VariableExpression getVariableExpression() {
        return variableExpression;
    }

    public Optional<VariableExpression> getKeyVariableExpression() {
        return keyVariableExpression;
    }

    @Override
    public CompositeRenderable render(RenderContext context) {
        Collection<Renderable> renderables = new ArrayList<>();
        NewlyScopedValueContext valueContext = (NewlyScopedValueContext) context.valueContext();
        if (keyVariableExpression.isPresent()) {
            Map<Object, Object> objects = expression.calculate(context).asMap();
            Cursor cursor = new Cursor(valueContext, new ArrayList<>(objects.entrySet()));
            for (Map.Entry<Object, Object> entry : objects.entrySet()) {
                valueContext.add(keyVariableExpression.get().getIdentifier(), entry.getKey());
                valueContext.add(variableExpression.getIdentifier(), entry.getValue());
                valueContext.addLocal("loop", cursor);
                renderables.add(super.render(context));
                cursor.step();
            }
        } else {
            Collection<Object> objects = expression.calculate(context).asCollection();
            Cursor cursor = new Cursor(valueContext, objects);
            for (Object value : objects) {
                valueContext.add(variableExpression.getIdentifier(), value);
                valueContext.addLocal("loop", cursor);
                renderables.add(super.render(context));
                cursor.step();
            }
        }
        return new CompositeRenderable(renderables);
    }

    @Override
    public ScopeType scopeType() {
        return ScopeType.SHARE_EDIT_OLD;
    }
}