
package com.samuel.api.controller;

import com.samuel.api.model.Despesa;
import com.samuel.api.service.OrcamentoService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrcamentoController {

    private final OrcamentoService service;
    private final ChatClient chatClient;

    public OrcamentoController(
            OrcamentoService service,
            ChatClient.Builder builder) {
        this.service = service;
        this.chatClient = builder.build();
    }

    @PostMapping("/despesas")
    public Despesa adicionar(@RequestBody Despesa despesa) {
        return service.adicionar(despesa);
    }

    @GetMapping("/despesas")
    public List<Despesa> listar() {
        return service.listar();
    }

    @GetMapping("/orcamento")
    public Map<String, Object> consultar() {
        return Map.of(
                "despesas", service.listar(),
                "total", service.total()
        );
    }

    @PostMapping("/assistente")
    public Map<String, String> conversar(
            @RequestBody Map<String, String> entrada) {

        String pergunta = entrada.get("mensagem");

        if (pergunta == null || pergunta.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe uma mensagem."
            );
        }

        String resposta = chatClient.prompt()
                .system("""
                    Você é um assistente financeiro.
                    Ajude a pessoa a organizar despesas e orçamento.
                    Não invente dados financeiros.
                    """)
                .user(pergunta)
                .call()
                .content();

        return Map.of("resposta", resposta);
    }
}
