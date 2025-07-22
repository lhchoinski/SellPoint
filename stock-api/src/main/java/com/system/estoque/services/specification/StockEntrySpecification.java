package com.system.estoque.services.specification;

import com.system.estoque.entities.Item;
import com.system.estoque.entities.StockEntry;
import org.springframework.data.jpa.domain.Specification;

public class StockEntrySpecification {

    public static Specification<StockEntry> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<StockEntry> hasNameContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("supplier")), "%" + search.toLowerCase() + "%");
        };
    }
}
