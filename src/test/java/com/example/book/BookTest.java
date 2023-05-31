package com.example.book;

import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

// Assertions클래스가 가지고있는 모든 static메소드를 가져오겠다.

import static org.assertj.core.api.Assertions.*;


//테스트를 위한 클래스라고 어노테이션 지정한것
@SpringBootTest
public class BookTest { //클래스 옆에있는 실행버튼을 누르면 클래스 내에있는 모든 테스트들이 실행이된다
// 서비스클래스의 메소드를 테스트하려고한다
    @Autowired
    private BookService bookService;

    // 도서 등록 테스트
    /** 테스트 흐름
    * 1. 신규 도서 데이터 생성(화면에서 입력하는 절차)
    * 2. save 메서드 호출해서 저장 처리
    * 3. 저장한 데이터의 id값을 가져오고
    * 4. 해당 id로 DB에서 조회를 한 뒤
    * 5. 1번에서 만든 객체의 책제목 값과 4번에서 조회한 객테의 책 제목 값이
    *       일치하는지를 판단하여
    * 6. 일치하면 테스트 성공, 일치하지 않으면 테스트 실패
    */

    private BookDTO newBook(){
        BookDTO bookDTO  =  new BookDTO();
        bookDTO.setBookName("test book");
        bookDTO.setBookAuthor("test author");
        bookDTO.setBookPrice(10000);
        return bookDTO;
    }

    //
    @Test
    @Transactional
    @Rollback(value = true) //테스트한 insert를 추가한후 롤백으로 인해 DB에 저장되지 않는다
    public void bookSaveTest(){
        BookDTO bookDTO = newBook(); //테스트용 데이터 준비(바로 위에 만든 메소드 호출)
        Long saveId = bookService.save(bookDTO);// 저장을 위해 메서드 호출 후 id값 가져옴
        //id조회
        BookDTO findDTO = bookService.findById(saveId);
        //테스트용 데이터의 제목과 조회한 데이터의 제목이 일치하는지 확인(같지않으면 저장처리가 안됬다고 판단함)

        assertThat(bookDTO.getBookName()).isEqualTo(findDTO.getBookName());

    }

    //삭제 테스트
    @Test
    @Transactional
    @Rollback
    @DisplayName("삭제테스트")
    public void bookDeleteTest(){
        /**
         * 1. 새로운 데이터 저장
         * 2. 저장된 데이터의 id를 가져옴
         * 3. 해당 id로 삭제처리
         * 4. 해당  id로 조회했을 때 null이면 삭제 테스트 성공
         * */
        BookDTO bookDTO = newBook();
        Long savedId = bookService.save(bookDTO);
        bookService.delete(savedId);
        assertThat(bookService.findById(savedId)).isNull();


    }

    // 수정 테스트
    @Test
    @Transactional
    @Rollback
    @DisplayName("수정테스트")
    public void bookUpdateTest(){
        /**
         * 1. 새로운 데이터 저장
         * 2. 수정용 데이터 준비 및 처리(제목만 변경)
         * 3. 데이터 조회
         * 4. 2번에서 수정한 제목과 3번에서 조회한 제목이 일치하면 수정 성공
         * */
        BookDTO bookDTO = newBook();
        Long savedId = bookService.save(bookDTO);

        // 2.
        bookDTO.setId(savedId);
        bookDTO.setBookName("수정 제목이다");
        bookService.update(bookDTO);
        BookDTO dto = bookService.findById(savedId);
        // 4.
        assertThat(dto.getBookName()).isEqualTo(bookDTO.getBookName());
    }

}
