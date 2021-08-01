package com.example.testtask.configuration;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    private final String SQL_TEMPLATE = "to_tsvector(text) @@ plainto_tsquery(?1)";
    private final String FUNCTION_NAME = "fts";

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {

        metadataBuilder.applySqlFunction(
                FUNCTION_NAME,
                new SQLFunctionTemplate(BooleanType.INSTANCE, SQL_TEMPLATE));
    }
}
