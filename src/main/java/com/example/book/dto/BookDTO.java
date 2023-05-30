package com.example.book.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class BookDTO {

    private Long id;
    private String bookName;
    private String bookAuthor;
    private int bookPrice;




}
