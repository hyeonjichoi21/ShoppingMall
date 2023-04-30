package com.example.ShoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResponseDTO<T> { // <T> 원소타입. 어떤 원소 타입의 리스트도 반환할 수 있도록 함
    private String error;
    private List<T> data;
}

// new ResponseDTO<String>
// new ResponseDTO<TodoDTO>
