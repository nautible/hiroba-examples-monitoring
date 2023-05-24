package jp.co.ogis_ri.example.springboot3;

import io.micrometer.core.instrument.*;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exemplars.DefaultExemplarSampler;
import io.prometheus.client.exemplars.tracer.otel_agent.OpenTelemetryAgentSpanContextSupplier;
import io.opentelemetry.api.trace.Span;

import org.springframework.context.annotation.*;

@Configuration
public class PrometheusConfiguration {

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistryWithExemplar
    (PrometheusConfig prometheusConfig, CollectorRegistry collectorRegistry, 
    Clock clock) {
        return new PrometheusMeterRegistry(prometheusConfig, collectorRegistry, 
        clock, new DefaultExemplarSampler(new OpenTelemetryAgentSpanContextSupplier() {

                    @Override
                    public String getTraceId() {
                        if (!Span.current().getSpanContext().isSampled()) {
                            return null;
                        }
                        return super.getTraceId();
                    }
                })
        );
    }
}