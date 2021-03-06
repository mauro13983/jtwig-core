package org.jtwig.functions.impl.logical;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.FunctionValueUtils;

import java.math.BigDecimal;

public class EvenFunction extends SimpleJtwigFunction {
    public static final BigDecimal TWO = new BigDecimal(2);

    @Override
    public String name() {
        return "even";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        BigDecimal conversionResult = FunctionValueUtils.getNumber(request, 0);
        return BigDecimal.ZERO.equals(conversionResult.remainder(TWO));
    }
}
