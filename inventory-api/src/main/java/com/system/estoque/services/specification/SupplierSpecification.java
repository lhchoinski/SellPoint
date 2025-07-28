package com.system.estoque.services.specification;

import com.system.estoque.entities.Supplier;
import org.springframework.data.jpa.domain.Specification;

public class SupplierSpecification {

    public static Specification<Supplier> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Supplier> hasNameContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
        };
    }
}
