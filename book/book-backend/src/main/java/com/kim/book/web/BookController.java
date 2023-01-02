package com.kim.book.web;

import com.kim.book.domain.Book;
import com.kim.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;


    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book) {
        // 200
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }


    @GetMapping("/book")
    public ResponseEntity<?> findAll() {
        // 200
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        // 200
        return new ResponseEntity<>(bookService.findOne(id), HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        // 200
        return new ResponseEntity<>(bookService.update(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        // 200
        return new ResponseEntity<>(bookService.delete(id), HttpStatus.OK);
    }
}
