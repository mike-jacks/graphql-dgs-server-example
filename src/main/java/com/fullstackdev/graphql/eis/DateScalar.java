package com.fullstackdev.graphql.eis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;

@DgsComponent
public class DateScalar implements Coercing<LocalDate, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public String serialize(Object dataFetcherResult) {
        if (dataFetcherResult instanceof LocalDate) {
            return ((LocalDate) dataFetcherResult).format(formatter);
        }
        throw new CoercingSerializeException("Expected a LocalDate object.");
    }

    @Override
    public LocalDate parseValue(Object input) {
        if (input instanceof String) {
            return LocalDate.parse((String) input, formatter);
        }
        throw new CoercingParseValueException("Expected a String.");
    }

    @Override
    public LocalDate parseLiteral(Object input) {
        if (input instanceof StringValue) {
            return LocalDate.parse(((StringValue) input).getValue(), formatter);
        }
        throw new CoercingParseLiteralException("Expected a StringValue.");
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(GraphQLScalarType.newScalar()
                .name("Date")
                .description("A custom scalar that handles dates in MM/dd/yyyy format")
                .coercing(this)
                .build());
    }
}
