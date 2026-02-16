package com.pizza.api.service;

import com.pizza.api.exception.ExternalApiException;
import com.pizza.api.model.PizzaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DominosApiService {

    private final WebClient webClient;

    @Value("${dominos.api.url:https://api.dominos.com/pizzas}")
    private String dominosApiUrl;

    @Autowired
    public DominosApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches pizza orders from external Domino's API,
     * filters by pepperoni topping, and sorts alphabetically by crust
     */
    public List<PizzaOrder> getExtraPizzas() {
        try {
            // Call external API using WebClient
            List<PizzaOrder> allPizzas = webClient.get()
                    .uri(dominosApiUrl)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PizzaOrder>>() {
                    })
                    .block(); // Block to make it synchronous for this use case

            if (allPizzas == null) {
                throw new ExternalApiException("Domino's API returned null response");
            }

            // Filter by pepperoni topping and sort alphabetically by crust
            return allPizzas.stream()
                    .filter(pizza -> "pepperoni".equalsIgnoreCase(pizza.getTopping()))
                    .sorted(Comparator.comparing(PizzaOrder::getCrust, String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList());

        } catch (WebClientResponseException e) {
            throw new ExternalApiException("Failed to fetch pizzas from Domino's API: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new ExternalApiException("Failed to fetch pizzas from Domino's API", e);
        }
    }
}
