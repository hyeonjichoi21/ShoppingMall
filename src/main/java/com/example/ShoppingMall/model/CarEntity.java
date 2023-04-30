package com.example.ShoppingMall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 엔티티 클래스 정의 시 주의할 점
@Builder
@NoArgsConstructor // 매개변수 없는 생성자가 필요함
@AllArgsConstructor
@Data // Getter/Setter가 필요함
@Entity
@Table(name = "Car") // 이 엔티티 클래스와 연결된 테이블 이름을 지정함

public class CarEntity {
    @Id // 기본 키 지정
    @GeneratedValue(generator = "system-uuid") // ID를 자동으로 생성하겠다는 뜻
    @GenericGenerator(name = "system-uuid", strategy = "uuid") //매개변수인 generator를 어떻게 ID로 생성할 지 지정할 수 있다.

    private String id;
    private String userId;
    private String title;
    private boolean done;
}
