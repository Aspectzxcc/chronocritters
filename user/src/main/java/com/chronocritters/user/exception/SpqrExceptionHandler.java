package com.chronocritters.user.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class SpqrExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters params) {
        Throwable exception = params.getException();
        DataFetchingEnvironment env = params.getDataFetchingEnvironment();

        if (exception instanceof ConstraintViolationException cve) {
            List<GraphQLError> errors = cve.getConstraintViolations().stream()
                    .map(violation -> GraphqlErrorBuilder.newError(env)
                            .message(violation.getMessage())
                            .extensions(java.util.Map.of("invalidField", violation.getPropertyPath().toString()))
                            .build())
                    .collect(Collectors.toList());
            
            DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult().errors(errors).build();
            return CompletableFuture.completedFuture(result);
        }

        GraphQLError error = GraphqlErrorBuilder.newError(env)
                .message(exception.getMessage())
                // add more details like error type if needed
                // .errorType(ErrorType.BAD_REQUEST)
                .build();
        
        DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult().error(error).build();
        return CompletableFuture.completedFuture(result);
    }
}