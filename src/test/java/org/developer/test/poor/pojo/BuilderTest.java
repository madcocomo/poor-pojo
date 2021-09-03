package org.developer.test.poor.pojo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.developer.test.poor.pojo.TestData.bookBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BuilderTest {
    private AutoCloseable mocks;

    @Mock
    BookRepository bookRepository;

    @Mock
    HotSaleExternalService hotSaleExternalService;

    @InjectMocks
    BookService service;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void test_get_books_according_to_repo_and_top_sales() {
        //given
        List<Book> allBooks = Arrays.asList(
            bookBuilder().id(1).category(BookCategory.Comic).price(100).title("SpiderMan").build(),
            bookBuilder().id(2).category(BookCategory.History).price(104).title("Roma").build(),
            bookBuilder().id(3).category(BookCategory.History).price(134).title("WWII").build(),
            bookBuilder().id(4).category(BookCategory.History).price(20).title("Egypt").build()
        );
        when(bookRepository.findAll()).thenReturn(allBooks);

        List<Book> hotBooks = Arrays.asList(
            bookBuilder().id(3).category(BookCategory.History).title("WWII").build(),
            bookBuilder().id(5).category(BookCategory.Art).title("painting").build(),
            bookBuilder().id(4).category(BookCategory.History).title("Egypt").build(),
            bookBuilder().id(1).category(BookCategory.Comic).title("SpiderMan").build()
        );
        when(hotSaleExternalService.hotSaleItems(Book.class, 10)).thenReturn(hotBooks);

        //when
        List<Book> actual = service.popularBooks(BookCategory.History);
        //then
        assertEquals(2, actual.size());
        assertEquals(3, actual.get(0).getId().longValue());
        assertEquals(134, actual.get(0).getPrice().longValue());
    }
}
