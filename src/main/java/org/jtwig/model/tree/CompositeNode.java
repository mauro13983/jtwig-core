package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.CompositeRenderable;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeNode extends Node {
    private final Collection<Node> nodes;

    public CompositeNode(Position position, Collection<Node> nodes) {
        super(position);
        this.nodes = nodes;
    }

    @Override
    public Renderable render(RenderContext context) {
        Collection<Renderable> renderables = new ArrayList<>();
        for (Node node : nodes) {
            renderables.add(context.nodeRenderer().render(node));
        }
        return new CompositeRenderable(renderables);
    }

    @Override
    public void visit(NodeVisitor visitor) {
        super.visit(visitor);
        for (Node node : nodes) {
            node.visit(visitor);
        }
    }
}