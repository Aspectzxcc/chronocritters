package com.chronocritters.user.logger;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class SpqrLoggingInterceptor implements graphql.execution.instrumentation.Instrumentation {

    private static final Logger logger = LoggerFactory.getLogger(SpqrLoggingInterceptor.class);

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        long startTime = System.currentTimeMillis();
        String operationName = parameters.getExecutionInput().getOperationName();
        Map<String, Object> variables = parameters.getVariables();

        // Sanitize password from variables before logging
        String safeVariables = variables.entrySet().stream()
                .map(entry -> {
                    if (entry.getKey().toLowerCase().contains("password")) {
                        return entry.getKey() + "=[PROTECTED]";
                    }
                    return entry.getKey() + "=" + entry.getValue();
                })
                .collect(Collectors.joining(", ", "{", "}"));

        logger.info("GraphQL Request Started: operationName='{}', variables={}", operationName, safeVariables);

        // return context to log completion
        return new InstrumentationContext<>() {
            @Override
            public void onDispatched(CompletableFuture<ExecutionResult> result) {
            }

            @Override
            public void onCompleted(ExecutionResult result, Throwable t) {
                long duration = System.currentTimeMillis() - startTime;
                
                if (t != null) {
                    logger.error("GraphQL Request Failed: operationName='{}', error='{}', duration={}ms",
                            operationName, t.getMessage(), duration, t);
                } else if (!CollectionUtils.isEmpty(result.getErrors())) {
                    logger.warn("GraphQL Request Completed with Errors: operationName='{}', errors={}, duration={}ms",
                            operationName, result.getErrors(), duration);
                } else {
                    logger.info("GraphQL Request Succeeded: operationName='{}', duration={}ms",
                            operationName, duration);
                }
            }
        };
    }
}