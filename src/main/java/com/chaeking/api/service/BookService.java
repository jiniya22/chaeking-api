package com.chaeking.api.service;

import com.chaeking.api.config.exception.InvalidInputException;
import com.chaeking.api.domain.value.BookValue;
import com.chaeking.api.domain.entity.Book;
import com.chaeking.api.domain.value.naver.NaverBookValue;
import com.chaeking.api.repository.BookRepository;
import com.chaeking.api.util.DateTimeUtils;
import com.chaeking.api.util.resttemplate.NaverApiRestTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final NaverApiRestTemplate naverApiRestTemplate;

    public Book select(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new InvalidInputException("조회되는 book 정보가 없습니다(book_id Error)"));
    }

    @Transactional
    public BookValue.Res.Detail insert(BookValue.Req.Creation req) {
        if(StringUtils.isBlank(req.name()))
            throw new InvalidInputException("name 은 필수 입력값입니다.");

        Book book = Book.builder()
                .name(req.name())
                .isbn(req.isbn())
                .price(req.price())
                .author(req.author())
                .imageUrl(req.imageUrl())
                .publisher(req.publisher())
                .detailInfo(req.detailInfo())
                .publicationDate(Optional.ofNullable(req.publicationDate())
                        .map(m -> LocalDate.parse(m, DateTimeUtils.FORMATTER_DATE)).orElse(null)).build();
        bookRepository.save(book);

        return new BookValue.Res.Detail(book);
    }

    public BookValue.Res.Detail book(long bookId) {
        return new BookValue.Res.Detail(select(bookId));
    }

    public NaverBookValue.Res.BookBasic searchNaverBasic(String name, String sort) {
        String query = "?query=" + name +"&sort=" + sort;
        ResponseEntity<NaverBookValue.Res.BookBasic> responseEntity = naverApiRestTemplate.get("/v1/search/book.json" + query, null, NaverBookValue.Res.BookBasic.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        }   // FIXME
        return null;
    }
}