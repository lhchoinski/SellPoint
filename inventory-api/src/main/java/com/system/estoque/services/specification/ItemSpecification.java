package com.system.estoque.services.specification;

import com.system.estoque.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    public static Specification<Product> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Product> hasNameContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
        };
    }
}
