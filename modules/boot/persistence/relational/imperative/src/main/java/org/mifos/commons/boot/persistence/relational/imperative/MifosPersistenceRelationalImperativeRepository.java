///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.persistence.relational.imperative;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.FluentQuery;

@NoRepositoryBean
public interface MifosPersistenceRelationalImperativeRepository<T> {
    List<T> findAll();

    List<T> findAll(Predicate predicate);

    List<T> findAll(Predicate predicate, Sort sort);

    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers);

    List<T> findAll(OrderSpecifier<?>... orderSpecifiers);

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    Optional<T> findById(UUID id);

    boolean existsById(UUID id);

    Iterable<T> findAllById(Iterable<UUID> ids);

    long count();

    void deleteById(UUID id);

    void delete(T entity);

    void deleteAllById(Iterable<UUID> ids);

    void deleteAll(Iterable<T> entities);

    void deleteAll();

    Optional<T> findOne(Predicate predicate);

    Page<T> findAll(Predicate predicate, Pageable pageable);

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

    <R extends @Nullable Object> R findBy(
            Predicate predicate, Function<FluentQuery.FetchableFluentQuery<T>, R> queryFunction);
}
