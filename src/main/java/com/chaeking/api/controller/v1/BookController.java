package com.chaeking.api.controller.v1;

import com.chaeking.api.domain.value.BookValue;
import com.chaeking.api.domain.value.response.DataResponse;
import com.chaeking.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RequiredArgsConstructor
@Tag(name = "book", description = "책")
@RestController
@RequestMapping("/v1/books")
public final class BookController {

    private final BookService bookService;

    @Operation(summary = "책 등록")
    @PostMapping("")
    public DataResponse<BookValue.Res.Detail> insert(@RequestBody BookValue.Req.Creation req) {
        BookValue.Res.Detail data = bookService.insert(req);
        return DataResponse.of(data);
    }

    @Operation(summary = "책 상세조회")
    @GetMapping("/{book_id}")
    public DataResponse<BookValue.Res.Detail> selectAll(
            @Parameter(description = "책 id") @PathVariable(name = "book_id") long bookId) {
        BookValue.Res.Detail data = bookService.book(bookId);
        Calendar cal = Calendar.getInstance();
        return DataResponse.of(data);
    }
}
