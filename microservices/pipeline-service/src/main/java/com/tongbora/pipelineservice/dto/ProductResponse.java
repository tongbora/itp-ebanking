package com.tongbora.pipelineservice.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String category,
        String description,
        BigDecimal price,
        Double rating,
        Integer reviewCount,
        String imageUrl,
        Boolean isNew
) {}
