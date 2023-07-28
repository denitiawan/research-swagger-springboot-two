package com.deni.app.module.product;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DenPaden on 18/08/2022.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Double sellPrice;


    public List<ProductDTO> listOf(List<Product> list) {
        List<ProductDTO> result = new ArrayList<>();
        for (Product entity : list) {
            result.add(of(entity));
        }
        return result;
    }

    public ProductDTO of(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellPrice(entity.getSellPrice())
                .build();
    }


}
