
package com.samuel.api.model;

import java.math.BigDecimal;

public record Despesa(
        String descricao,
        BigDecimal valor
) {}
