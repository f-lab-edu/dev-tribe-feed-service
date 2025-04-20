package com.devtribe.devtribe_feed_service.global.config.jpa;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
            .registerPattern(
                "match_against",
                "MATCH(?1, ?2) AGAINST(?3 IN NATURAL LANGUAGE MODE)",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry()
                    .resolve(StandardBasicTypes.DOUBLE)
            );
    }
}
