package com.example.book.entity;

import com.example.book.dto.BookDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "book_table")
@ToString //이번에만 쓰고 원래는 안씀
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(length=50)
    private String bookName;
    @Column(length=20)
    private String bookAuthor;
    @Column
    private int bookPrice;

    // 접근 제한자를 public로 두고 static으로둠(static의 특징은 bookentity객체를 만들지 않고 직접 클래스로 접근해서 사용할 수있는 메소드)
    //toSaveEntity만 다른 클래스에 오픈해놓음
    //insert용 entity변환 메소드
    public static  BookEntity toSaveEntity(BookDTO bookDTO){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        return bookEntity;
    }

    // id를 고려한 update용 entity변환 메소드
    //update를 할때 전체 덮어써지기 때문에 굳이 주지않는 값도 다 DB에 넣어줘야된다 그렇지 않으면 null처리가 될수도있다
    //bookEntity.setBookName(bookDTO.getBookName()); 이런걸 모든 컬럼을 써줘야한다
    public static BookEntity toupdateEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        return bookEntity;
    }
}
