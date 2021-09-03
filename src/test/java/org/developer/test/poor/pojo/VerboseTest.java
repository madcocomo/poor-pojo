package org.developer.test.poor.pojo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VerboseTest {
    private AutoCloseable mocks;

    @Mock
    BookRepository bookRepository;

    @Mock
    HotSaleExternalService hotSaleExternalService;

    @InjectMocks
    BookService service;

    @Test
    void test_get_books_according_to_repo_and_top_sales() {
        //given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setCategory(BookCategory.Comic);
        book1.setPrice(BigDecimal.valueOf(100));
        book1.setTitle("SpiderMan");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setCategory(BookCategory.History);
        book2.setPrice(BigDecimal.valueOf(104));
        book2.setTitle("Roma");

        Book book3 = new Book();
        book3.setId(3L);
        book3.setCategory(BookCategory.History);
        book3.setPrice(BigDecimal.valueOf(134));
        book3.setTitle("WWII");

        Book book4 = new Book();
        book4.setId(4L);
        book4.setCategory(BookCategory.History);
        book4.setPrice(BigDecimal.valueOf(20));
        book4.setTitle("Egypt");

        List<Book> allBooks = Arrays.asList(book1, book2, book3, book4);
        when(bookRepository.findAll()).thenReturn(allBooks);

        Book hotBook1 = new Book();
        hotBook1.setId(3L);
        hotBook1.setCategory(BookCategory.History);
        hotBook1.setTitle("WWII");

        Book hotBook2 = new Book();
        hotBook2.setId(5L);
        hotBook2.setCategory(BookCategory.Art);
        hotBook2.setTitle("painting");

        Book hotBook3 = new Book();
        hotBook3.setId(4L);
        hotBook3.setCategory(BookCategory.History);
        hotBook3.setTitle("Egypt");

        Book hotBook4 = new Book();
        hotBook4.setId(1L);
        hotBook4.setCategory(BookCategory.Comic);
        hotBook4.setTitle("SpiderMan");

        List<Book> hotBooks = Arrays.asList(hotBook1, hotBook2, hotBook3, hotBook4);

        when(hotSaleExternalService.hotSaleItems(Book.class, 10)).thenReturn(hotBooks);

        //when
        List<Book> actual = service.popularBooks(BookCategory.History);
        //then
        assertEquals(2, actual.size());
        assertEquals(3, actual.get(0).getId().longValue());
        assertEquals(134, actual.get(0).getPrice().longValue());

    }

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}