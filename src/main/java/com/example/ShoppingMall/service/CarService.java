package com.example.ShoppingMall.service;

import com.example.ShoppingMall.model.CarEntity;
import com.example.ShoppingMall.persistence.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j // 가장 많이 쓰이는 로깅 라이브러리 (로깅 : info, debug, warn, error)
@Service // 내부에 @Componenet 어노테이션을 포함하고 있으며 비즈니스 로직을 수행하는 서비스 레이어임을  알려주는 역할을하는 어노테이션
public class CarService {

    @Autowired // CarRepository 인터페이스를 구현한 클래스를 스프링 데이터 JPA가 자동으로 만들고, 이 클래스의 객체가 자동으로 주입됨
    private CarRepository repository;

    //컨트롤러와 마찬가지로 이 함수는 실제 프로젝트에서 사용하지 않아서
    //텀프로젝트 작성시 포함하지 않아도 괜찮아요.

    // 1. 추가
    public List<CarEntity> create(final CarEntity entity) {
        // 1. Validations(검증): 넘어온 엔티티가 유효한지 검사하는 로직
        validate(entity);

        repository.save(entity); // 2. save(): 엔티티를 데이터베이스에 저장한다. 로그를 남긴다.
        log.info("Entity Id : {} is saved.", entity.getId());
        return repository.findAll(); //저장된 모든 아이템 리스트를 불러와야 하기 때문에 all하는 게 좋아요
    }

    private void validate(final CarEntity entity) {
        // 1. Validations(검증): 넘어온 엔티티가 유효한지 검사하는 로직
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    // 2. 검색
    public List<CarEntity> retrieve(final String title) {
        return repository.findByTitle(title);
    }

    // 3. 수정
    public List<CarEntity> update(final CarEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다. 이 메서드는 2.3.1 Create Car에서 구현했다.
        validate(entity);

        // (2) 넘겨받은 엔티티 id를 이용해 CarEntity를 가져온다. 존재하지 않는 엔티티는 업데이트 할 수 없기 때문이다.
        final Optional<CarEntity> original = repository.findById(entity.getId());

        original.ifPresent(Car -> {
            // (3) 반환된 CarEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
            Car.setTitle(entity.getTitle());
            Car.setDone(entity.isDone());
            Car.setUserId(entity.getUserId()); //userId도 클라이언트에서 받아오니까~ 이대로 두면 userId는 수정이 안돼요!
            // (4) 데이터베이스에 새 값을 저장한다.
            repository.save(Car);
        });

        // 2.3.2 Retrieve Car에서 만든 메서드를 이용해 유저의 모든 Car 리스트를 리턴한다.
        return retrieve(entity.getTitle());
    }


    // 4. 삭제
    public List<CarEntity> delete(final CarEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다. 이 메서드는 2.3.1 Create Car에서 구현했다.
        validate(entity);

        try {
            // (2) 엔티티를 삭제한다.
            repository.delete(entity);
        } catch(Exception e) {
            // (3) exception 발생시 id와 exception을 로깅한다.
            log.error("error deleting entity ", entity.getId(), e);

            // (4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        // (5) 새 Car리스트를 가져와 리턴한다.
        return repository.findAll();
    }


}
