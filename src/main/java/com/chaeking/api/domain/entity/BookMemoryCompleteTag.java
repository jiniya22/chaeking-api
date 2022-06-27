package com.chaeking.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_memory_complete_tag")
@Entity
public class BookMemoryCompleteTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK__BOOK_MEMORY_COMPLETE_TAG__BOOK_MEMORY_COMPLETE"))
    private BookMemoryComplete bookMemoryComplete;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK__BOOK_MEMORY_COMPLETE_TAG__TAG"))
    private Tag tag;

    @Builder
    public BookMemoryCompleteTag(Tag tag) {
        this.tag = tag;
    }
}
