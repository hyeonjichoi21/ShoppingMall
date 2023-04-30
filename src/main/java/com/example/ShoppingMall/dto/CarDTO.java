package com.example.ShoppingMall.dto;


import com.example.ShoppingMall.model.CarEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CarDTO {

    // 제품의 속성은 4가지로 할 것 (단, title과 userId 라는 속성은 반드시 가질 것)

    private String id;
    private String title;
    private boolean done;

    private String userId;
    public CarDTO(final CarEntity entity){ // CarEntity를 받아서 생성됨
        this.id = entity.getId(); // userId는 숨김
        this.title = entity.getTitle();
        this.done = entity.isDone();
        this.userId = entity.getUserId();
    }

    public static CarEntity toEntity(final CarDTO dto) {
        return CarEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .userId(dto.getUserId())
                .build();
    }
}
