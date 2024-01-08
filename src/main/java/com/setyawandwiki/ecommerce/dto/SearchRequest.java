package com.setyawandwiki.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    private String name;
    private String category;
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
    private Sort.Direction sort;
    private String column;
}
