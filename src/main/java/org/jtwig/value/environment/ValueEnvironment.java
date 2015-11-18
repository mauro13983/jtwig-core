package org.jtwig.value.environment;

import org.jtwig.value.JtwigType;
import org.jtwig.value.converter.Converter;
import org.jtwig.value.extract.Extractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.relational.RelationalComparator;

import java.math.MathContext;

public class ValueEnvironment {
    private final MathContext mathContext;
    private final RelationalComparator equalComparator;
    private final RelationalComparator identicalComparator;
    private final RelationalComparator lowerComparator;
    private final RelationalComparator greaterComparator;
    private final Extractor<JtwigType> typeExtractor;
    private final MapSelectionExtractor mapSelectionExtractor;
    private final Converter converter;

    public ValueEnvironment(MathContext mathContext, RelationalComparator equalComparator,
                            RelationalComparator identicalComparator, RelationalComparator lowerComparator,
                            RelationalComparator greaterComparator, Extractor<JtwigType> typeExtractor,
                            MapSelectionExtractor mapSelectionExtractor, Converter converter) {
        this.mathContext = mathContext;
        this.equalComparator = equalComparator;
        this.identicalComparator = identicalComparator;
        this.lowerComparator = lowerComparator;
        this.greaterComparator = greaterComparator;
        this.typeExtractor = typeExtractor;
        this.mapSelectionExtractor = mapSelectionExtractor;
        this.converter = converter;
    }

    public MathContext getMathContext() {
        return mathContext;
    }

    public RelationalComparator getEqualComparator() {
        return equalComparator;
    }

    public RelationalComparator getIdenticalComparator() {
        return identicalComparator;
    }

    public RelationalComparator getLowerComparator() {
        return lowerComparator;
    }

    public RelationalComparator getGreaterComparator() {
        return greaterComparator;
    }

    public Extractor<JtwigType> getTypeExtractor() {
        return typeExtractor;
    }

    public MapSelectionExtractor getMapSelectionExtractor() {
        return mapSelectionExtractor;
    }

    public Converter getConverter() {
        return converter;
    }
}