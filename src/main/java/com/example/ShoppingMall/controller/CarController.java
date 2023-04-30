package com.example.ShoppingMall.controller;

import com.example.ShoppingMall.dto.ResponseDTO;
import com.example.ShoppingMall.dto.CarDTO;
import com.example.ShoppingMall.model.CarEntity;
import com.example.ShoppingMall.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController // 내부에 @Component 어노테이션을 가지고 있다. 따라서 @Service도 @Restcontroller도 모두 자바빈이고 스프링이 관리한다.
@RequestMapping("car")
public class CarController {

    @Autowired // 알아서 빈을 찾아서 그 빈을 이 인스턴스 멤버 변수에 연결하라
    // (-> Carcontroller를 초기화할 때 스프링은 알아서 CarService를 초기화 또는 검색해 CarController에 주입해준다.)
    private CarService service;

    //이 부분은 테스트하는 용으로 시험해본 거라 굳이 포함하지 않아도 괜찮아요.

    // 1. 추가
    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody CarDTO dto) {
        try {
            // String temporaryUserId = "HyeonJichoi"; // temporary user id.

            // (1) CarEntity로 변환한다.
            CarEntity entity = CarDTO.toEntity(dto);

            // (2) id를 null로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

            // (3) 임시 유저 아이디를 설정 해 준다. 이 부분은 4장 인증과 인가에서 수정 할 예정이다. 지금은 인증과 인가 기능이 없으므로 한 유저(temporary-user)만 로그인 없이 사용 가능한 어플리케이션인 셈이다
            //entity.setUserId(dto.getUserid());

            // (4) 서비스를 이용해 Car엔티티를 생성한다.
            List<CarEntity> entities = service.create(entity);

            // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 CarDTO리스트로 변환한다.

            List<CarDTO> dtos = entities.stream().map(CarDTO::new).collect(Collectors.toList());

            // (6) 변환된 CarDTO리스트를 이용해ResponseDTO를 초기화한다.
            ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().data(dtos).build();

            // (7) ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // (8) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.

            String error = e.getMessage();
            ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 2. 검색
    @GetMapping
    public ResponseEntity<?> retrieveCarList(@RequestBody CarDTO dto) {
        // String temporaryTitle= CarEn.; // temporary user id.
        //  temporaryTitle = CarEntity.get

        // (1) 서비스 메서드의 retrieve메서드를 사용해 Car리스트를 가져온다
        List<CarEntity> entities = service.retrieve(dto.getTitle());

        // (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 CarDTO리스트로 변환한다.
        List<CarDTO> dtos = entities.stream().map(CarDTO::new).collect(Collectors.toList());

        // (6) 변환된 CarDTO리스트를 이용해ResponseDTO를 초기화한다.
        ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().data(dtos).build();

        // (7) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }


    // 3. 수정
    @PutMapping
    public ResponseEntity<?> updateCar(@RequestBody CarDTO dto) {
        //String temporaryUserId = "HyeonJichoi"; // temporary user id.

        // (1) dto를 entity로 변환한다.
        CarEntity entity = CarDTO.toEntity(dto);

        // (2) id를 temporaryUserId로 초기화 한다. 여기는 4장 인증과 인가에서 수정 할 예정이다.
        // entity.setUserId(entity.getUserId());

        // (3) 서비스를 이용해 entity를 업데이트 한다.
        List<CarEntity> entities = service.update(entity);

        // (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 CarDTO리스트로 변환한다.
        List<CarDTO> dtos = entities.stream().map(CarDTO::new).collect(Collectors.toList());

        // (5) 변환된 CarDTO리스트를 이용해ResponseDTO를 초기화한다.
        ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().data(dtos).build();

        // (6) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    // 4. 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteCar(@RequestBody CarDTO dto) {
        try {
            //String temporaryUserId = "HyeonJichoi"; // temporary user id.

            // (1) CarEntity로 변환한다.
            CarEntity entity = CarDTO.toEntity(dto);

            // (2) 임시 유저 아이디를 설정 해 준다. 이 부분은 4장 인증과 인가에서 수정 할 예정이다. 지금은 인증과 인가 기능이 없으므로 한 유저(temporary-user)만 로그인 없이 사용 가능한 어플리케이션인 셈이다
            // entity.setUserId(dto.getUserid());

            // (3) 서비스를 이용해 entity를 삭제 한다.
            List<CarEntity> entities = service.delete(entity);

            // (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 CarDTO리스트로 변환한다.
            List<CarDTO> dtos = entities.stream().map(CarDTO::new).collect(Collectors.toList());

            // (5) 변환된 CarDTO리스트를 이용해ResponseDTO를 초기화한다.
            ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().data(dtos).build();

            // (6) ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // (8) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<CarDTO> response = ResponseDTO.<CarDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

