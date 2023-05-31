package com.example.book.service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository ;
    public Long save(BookDTO bookDTO) {
//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setBookName(bookDTO.getBookName());
//        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
//        bookEntity.setBookPrice(bookDTO.getBookPrice());
//        BookEntity bookEntity = toSaveEntity(bookDTO);
        BookEntity bookEntity = BookEntity.toSaveEntity(bookDTO);
        // 저장처리를 하기전에 BookEntity(id=null이 콘솔에뜸)
//        System.out.println("bookEntity = " + bookEntity);
//        BookEntity savedEntity = bookRepository.save(bookEntity);
        // 저장 처리를 하고 나서 BookEnttiy(id값이 있다고 콘솔에뜸)
//        System.out.println("savedEntity = " + savedEntity);

        // 저장 처리 후 저장한 데이터의 id값을 리턴하겠다
        return bookRepository.save(bookEntity).getId();
    }


    public List<BookDTO> findAll() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        //List<BookEntity> ->List<BookDTO>로 옮겨 담아야된다
        List<BookDTO> bookDTOList = new ArrayList<>();

        // 엔티티 리스트로 담긴 객체 하나하나를 DTO로 변환 한후 bookDTIList에 담을거다
        for(BookEntity bookEntity : bookEntityList){
            /*
            * 1. entity를 DTO로 변환하기 위한 메서드 호출 (그 메소드는 BookDTO에 정의한다)
            * 2. 호출결과는 DTO객체로 받음
            * 3. DTO객테를 DTO리스트에 추가
            * 4. 반복문 종료 후 DTO 리스트를 리턴
            *  */

            //BookDTO에서 static으로 메소드를 줘서 클래스 이름으로 메소드를 호출해야된다
            BookDTO bookDTO = BookDTO.toDTO(bookEntity);
            bookDTOList.add(bookDTO);

            //위에 두줄을 한줄로 쓰면..
            //bookDTOList.add( BookDTO.toDTO(bookEntity));

        }
        return bookDTOList;

    }

    public BookDTO findById(Long id) {
    // 좌변 자동완선 ;alt + enter
        // 좌변을 자동완성 해보니 Optional이라고 떴다 optional은 자바에서 가지고있는 클래스이다
        // 옵셔널이라는 완충제를 추가해서 널포인트를 없애려고 자바에서 추가를 해준거임,,
        // 옵셔널이라는 클래스는 객체를 한번더 감싸줘서 무조건 널포인트가 터지는걸 방제해주려고 한것
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);

        // 옵셔널엔티티의 값이 있으면..그안에 있는 처리를해라! // 널포인트를 안뜨게 하기위해서 자바에서 기본장치를 해논것
        if(optionalBookEntity.isPresent()){
            System.out.println("있다");

            // optional 객체에서 꺼내기(get이라는 메소드를 사용해야만 옵셔널이 벗겨지고 그안에있는 entity객체를 꺼낼수있다)
            BookEntity bookEntity = optionalBookEntity.get();

            //BookEntity->BookDTO로 변환해야됨
            BookDTO bookDTO = BookDTO.toDTO(bookEntity);
            return  bookDTO;

            //위에 세줄을 한줄로 변환한다면
//            return BookDTO.toDTO(optionalBookEntity.get());

        }else{
            System.out.println("없다");
            return null;
        }

    }

    // delete는 내가 삭제하고자한 id값만 넘겨주면 끝!
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public void update(BookDTO bookDTO) {
        //insert할때 썼던 toSaveEntity를 재활용할수없다(id값을 다루고있지 않아서)
//        BookEntity bookEntity = BookEntity.toSaveEntity(bookDTO);
        BookEntity bookEntity = BookEntity.toupdateEntity(bookDTO);

        //jpa는 update메소드가 없고 save를 사용한다
        //세이브를 할떄 id값이 잇으면 기존에 DB에 존재하는 애라면 JPA는 update 쿼리를 날려준다
        //id값이 없고 기존 DB에 존재하지 않는 애라면 JPA는 insert 쿼리를 날려준다
        //update를 할때 전체 덮어써지기 때문에 굳이 주지않는 값도 다 DB에 넣어줘야된다 그렇지 않으면 null처리가 될수도있다
        bookRepository.save(bookEntity);
    }
}
