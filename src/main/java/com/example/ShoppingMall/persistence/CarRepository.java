package com.example.ShoppingMall.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ShoppingMall.model.CarEntity;

import java.util.List;

@Repository // 퍼시스턴스 레이어에 속함을 나타냄. @Component 어노테이션을 포함함
public interface CarRepository extends JpaRepository<CarEntity, String>{ // JpaRepository<T, ID> 인터페이스 (T: 테이블에 매핑될 엔티티 클래스, ID: 이 엔티티의 기본 키의 타입)
    List<CarEntity> findByUserId(String userId);
    List<CarEntity> findByTitle(String title);
}