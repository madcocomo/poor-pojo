package org.developer.test.poor.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookService {
    public static final int POPULAR_ITEM_COUNT = 10;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private HotSaleExternalService hotSaleService;

    public List<Book> popularBooks(BookCategory category) {
        Set<Long> hotBooks = hotSaleService.hotSaleItems(Book.class, POPULAR_ITEM_COUNT).
                stream().map(Book::getId).collect(Collectors.toSet());
        return bookRepository.findAll().stream()
                .filter(book -> book.getCategory() == category)
                .filter(book -> hotBooks.contains(book.getId()))
                .collect(Collectors.toList());
    }
}
