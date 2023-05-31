package com.example.book.repository;

import com.example.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<BookEntity, Long> {

    //JpaRepository<BookEntity, Long>
    //첫번째 쓰는거 이 레퍼지를 가지고 어떤 엔티티에 CRUD를 가질거냐
    //두번쨰 쓰는거는 해당 엔티티클래스에 정의한 pk타입

    //JpaRepository를 상속받아서 정의를 했기 때문에 자연스럼게 스프링객체로 등록됬기 때문에 @Repository를 안써도됨
}
