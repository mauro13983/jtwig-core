package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
import org.jtwig.macro.render.ImportRender;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.ValueContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ImportNodeRenderTest {
    private final ImportRender importRender = mock(ImportRender.class);
    private ImportNodeRender underTest = new ImportNodeRender(importRender);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderResourceNotFound() throws Exception {
        String identifier = "identifier";
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        ResourceReference resource = mock(ResourceReference.class);
        ResourceReference newResource = mock(ResourceReference.class);
        String pathValue = "path";

        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression))
                .thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(resource);
        when(environment.getResourceEnvironment().getResourceService().resolve(resource, path)).thenReturn(newResource);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'path' not found"));

        underTest.render(request, importNode);
    }

    @Test
    public void renderMacroAliasesContextNotStarted() throws Exception {
        String identifier = "identifier";
        String path = "path";
        String pathValue = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        ResourceReference resource = mock(ResourceReference.class);
        ResourceReference newResource = mock(ResourceReference.class);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);
        Node node = mock(Node.class);

        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(resource);
        when(request.getRenderContext().getCurrent(ValueContext.class)).thenReturn(mock(ValueContext.class));
        when(environment.getResourceEnvironment().getResourceService().resolve(resource, path)).thenReturn(newResource);
        when(environment.getParser().parse(environment, newResource)).thenReturn(node);
        when(environment.getResourceEnvironment().getResourceService().loadMetadata(newResource)).thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(true);
        when(newResource.getPath()).thenReturn("path");

        Renderable result = underTest.render(request, importNode);

        assertSame(EmptyRenderable.instance(), result);
    }

    @Test
    public void renderMacroAliasesContextStarted() throws Exception {
        String identifier = "identifier";
        String path = "path";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ImportNode importNode = mock(ImportNode.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        ResourceReference resource = mock(ResourceReference.class);
        ResourceReference newResource = mock(ResourceReference.class);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);
        Node node = mock(Node.class);
        String pathValue = "path";

        when(importNode.getAliasIdentifier().getIdentifier()).thenReturn(identifier);
        when(importNode.getImportExpression()).thenReturn(expression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, expression))
                .thenReturn(pathValue);
        when(environment.getValueEnvironment().getStringConverter().convert(pathValue)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(resource);
        when(request.getRenderContext().getCurrent(ValueContext.class)).thenReturn(mock(ValueContext.class));
        when(environment.getResourceEnvironment().getResourceService().resolve(resource, path)).thenReturn(newResource);
        when(environment.getParser().parse(environment, newResource)).thenReturn(node);
        when(environment.getResourceEnvironment().getResourceService().loadMetadata(newResource)).thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(true);
        when(newResource.getPath()).thenReturn("path");

        Renderable result = underTest.render(request, importNode);

        assertSame(EmptyRenderable.instance(), result);
    }
}