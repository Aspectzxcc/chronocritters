package com.chronocritters.lib.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public abstract class BaseWebClient {

    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected BaseWebClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder
            .baseUrl(baseUrl)
            .build();
    }

    protected <T> Mono<T> get(String uri, Class<T> responseType, Object... uriVariables) {
        return executeRequest(webClient.get().uri(uri, uriVariables), responseType);
    }

    protected <T, R> Mono<R> post(String uri, T body, Class<R> responseType, Object... uriVariables) {
        return executeRequest(webClient.post().uri(uri, uriVariables).bodyValue(body), responseType);
    }

    protected <T> Mono<T> post(String uri, Class<T> responseType, Object... uriVariables) {
        return executeRequest(webClient.post().uri(uri, uriVariables), responseType);
    }

    private <T> Mono<T> executeRequest(WebClient.RequestHeadersSpec<?> request, Class<T> responseType) {
        return request.retrieve()
            .bodyToMono(responseType)
            .retryWhen(defaultRetrySpec())
            .doOnError(error -> {
                if (error instanceof WebClientResponseException ex) {
                    logger.error("Request failed with status {} and body '{}' for URI: {}",
                        ex.getStatusCode(), ex.getResponseBodyAsString(), request.toString());
                } else if (error instanceof WebClientRequestException ex) {
                     logger.error("Request failed due to a network issue for URI: {}. Reason: {}",
                        ex.getUri(), ex.getMessage());
                } else {
                    logger.error("An unexpected error occurred during request for URI: {}. Reason: {}",
                        request.toString(), error.getMessage());
                }
            })
            .onErrorResume(throwable -> Mono.empty());
    }

    private Retry defaultRetrySpec() {
        return Retry.backoff(3, Duration.ofMillis(500))
            .filter(this::isRetryableException)
            .doBeforeRetry(retrySignal -> logger.warn("Retrying request... Attempt #{}. Cause: {}",
                retrySignal.totalRetries() + 1,
                retrySignal.failure().getMessage()));
    }

    private boolean isRetryableException(Throwable throwable) {
        return throwable instanceof WebClientRequestException ||
               (throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is5xxServerError());
    }
}