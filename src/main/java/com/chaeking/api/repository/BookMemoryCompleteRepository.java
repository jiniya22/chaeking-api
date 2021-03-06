package com.chaeking.api.repository;

import com.chaeking.api.domain.entity.Book;
import com.chaeking.api.domain.entity.BookMemoryComplete;
import com.chaeking.api.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookMemoryCompleteRepository extends JpaRepository<BookMemoryComplete, Long> {
    Optional<BookMemoryComplete> findByBookAndUser(Book book, User user);
    Optional<BookMemoryComplete> findWithUserById(Long bookMemoryCompleteId);
    List<BookMemoryComplete> findAllByUser(User user, Pageable pageable);
    List<BookMemoryComplete> findAllByUserAndCreatedAtBetween(User user, LocalDateTime createdAt1, LocalDateTime createdAt2);
    Page<BookMemoryComplete> findAllByUserAndCreatedAtBetween(User user, LocalDateTime createdAt1, LocalDateTime createdAt2, Pageable pageable);
    boolean existsByUser(User user);

    @Modifying
    @Query(value = "delete from BookMemoryComplete b where b.book = ?1 and b.user = ?2")
    void deleteByBookAndUser(Book book, User user);
}
