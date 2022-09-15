package com.example.SpringDemo.service;

import com.example.SpringDemo.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    public List<Book> getBooks(){
        return new ArrayList<>();
    }

    public Long saveBook(Book book){
        return 0l;
    }

    public boolean isDeleted(Long id){
        return true;
    }

    public Book updateBook(Long id, Book book){
        return new Book();
    }

    public Book getBook(Long id){
        return new Book();
    }
}
