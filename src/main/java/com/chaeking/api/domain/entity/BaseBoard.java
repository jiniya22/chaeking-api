package com.chaeking.api.domain.entity;

import com.chaeking.api.domain.value.BoardValue;
import com.chaeking.api.util.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static BoardValue.Res.Simple createSimple(BaseBoard b) {
        return new BoardValue.Res.Simple(b.getId(), b.getTitle(), DateTimeUtils.toString(b.getCreatedAt()));
    }

    public static BoardValue.Res.Detail createDetail(BaseBoard b) {
        return new BoardValue.Res.Detail(b.getId(), b.getTitle(), DateTimeUtils.toString(b.getCreatedAt()), b.getContent());
    }
}