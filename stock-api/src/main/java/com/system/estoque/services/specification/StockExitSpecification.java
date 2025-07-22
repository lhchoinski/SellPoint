package com.system.estoque.services.specification;

import com.system.estoque.entities.StockExit;
import org.springframework.data.jpa.domain.Specification;

public class StockExitSpecification {

    public static Specification<StockExit> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<StockExit> hasNameContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("item")), "%" + search.toLowerCase() + "%");
        };
    }
}
