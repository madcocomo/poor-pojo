package org.developer.test.poor.pojo;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestData {

    @Builder(builderMethodName = "")
    private static Book _newBook(long id, String title, String subTitle, double price, LocalDate publishDate, BookCategory category) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setSubTitle(subTitle);
        book.setPrice(BigDecimal.valueOf(price));
        book.setPublishDate(publishDate);
        book.setCategory(category);
        return book;
    }

    public static BookBuilder bookBuilder() {
        return new BookBuilder().publishDate(LocalDate.of(2020, 1, 1));
    }

}
