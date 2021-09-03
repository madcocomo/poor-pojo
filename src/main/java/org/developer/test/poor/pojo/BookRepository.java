package org.developer.test.poor.pojo;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface BookRepository extends Repository<Book, Long> {
    List<Book> findAll();
}
