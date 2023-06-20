package com.lel.bookmingle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 7858268648377603737L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publisher;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "proposedBook")
    private List<BookExchange> proposedBooks = new ArrayList<>();

    @OneToMany(mappedBy = "requestedBook")
    private List<BookExchange> requestedBooks = new ArrayList<>();
}
