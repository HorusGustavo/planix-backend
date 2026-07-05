package com.planix.planix.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ClaudeService {

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String API_KEY = "SUA_CHAVE_AQUI";
    private static final String MODEL = "claude-opus-4-6";

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String extrairDadosDaImagem(String base64Image) {
        try {
            String jsonBody = """
                {
                    "model": "%s",
                    "max_tokens": 1024,
                    "messages": [
                        {
                            "role": "user",
                            "content": [
                                {
                                    "type": "image",
                                    "source": {
                                        "type": "base64",
                                        "media_type": "image/jpeg",
                                        "data": "%s"
                                    }
                                },
                                {
                                    "type": "text",
                                    "text": "Extraia todas as faixas etárias e seus respectivos valores desta tabela de plano de saúde. Retorne em formato JSON com a estrutura: [{idadeMin, idadeMax, valorOriginal, valorComDesconto, desconto}]"
                                }
                            ]
                        }
                    ]
                }
                """.formatted(MODEL, base64Image);

            RequestBody body = RequestBody.create(
                jsonBody, MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("x-api-key", API_KEY)
                .addHeader("anthropic-version", "2023-06-01")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

            try (Response response = httpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                JsonNode json = objectMapper.readTree(responseBody);
                return json.get("content").get(0).get("text").asText();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar API Claude: " + e.getMessage());
        }
    }
}