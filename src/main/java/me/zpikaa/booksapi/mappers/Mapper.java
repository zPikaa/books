package me.zpikaa.booksapi.mappers;

public interface Mapper<A, B> {

    B mapTo(A a);
    A mapFrom(B a);

}
