package com.Modelo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public class ConsultaHttp {

    public Map<String, Double>fetchData(){
        String apiKey;
        Properties propiedades = new Properties();
        try (FileInputStream input = new FileInputStream("src/Config.properties")){
            propiedades.load(input);
            apiKey = propiedades.getProperty("APIKEY");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri( URI.create("https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/USD"))
                    .build();
            // Puedes cambiar a POST si es necesario .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // Parsear el JSON en un objeto JsonElement
            JsonElement jsonElement = JsonParser.parseString(json);
            // Convertir el JsonElement en un JsonObject
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            // Extraer el objeto de conversion_rates
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
            String conversionRatesJson = conversionRates.toString();

            List<String> monedasEspecificas = Arrays.asList("EUR", "JPY", "MXN", "ARS", "BOB", "BRL", "CLP", "COP", "USD");

            // Filtrar y almacenar en un Map
            Map<String, Double> tasasSeleccionadas = conversionRates.entrySet().stream()
                    .filter(entry -> monedasEspecificas.contains(entry.getKey())) // Filtro por las monedas específicas
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,  // Llave: nombre de la moneda
                            entry -> entry.getValue().getAsDouble() // Valor: tasa de cambio
                    ));

            return tasasSeleccionadas;
        }
        catch (Exception e) {
            return (Map<String, Double>) Collections.singletonList(e.getMessage());
        }
    }
}
