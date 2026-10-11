
package com.samuel.api.service;

import com.samuel.api.model.Despesa;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrcamentoService {

    private final List<Despesa> despesas = new ArrayList<>();

    public Despesa adicionar(Despesa despesa) {
        if (despesa.valor() == null ||
                despesa.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero."
            );
        }

        despesas.add(despesa);
        return despesa;
    }

    public List<Despesa> listar() {
        return List.copyOf(despesas);
    }

    public BigDecimal total() {
        return despesas.stream()
                .map(Despesa::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
