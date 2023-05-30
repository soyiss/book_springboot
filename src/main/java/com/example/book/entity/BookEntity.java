package com.example.book.entity;

import com.example.book.dto.BookDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "book_table")
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
    public static  BookEntity toSaveEntity(BookDTO bookDTO){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        return bookEntity;
    }

}
