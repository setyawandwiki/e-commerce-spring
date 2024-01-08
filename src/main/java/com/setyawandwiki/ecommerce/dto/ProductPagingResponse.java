package com.setyawandwiki.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPagingResponse {
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
    private Sort.Direction sort;
    private String sortByColumn;
}
