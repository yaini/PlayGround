package com.yaini.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Transactional
public class CustomJPARepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements CustomJPARepository<T, ID> {

    private static final String DELETED = "deleted";
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public CustomJPARepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
    }

    private static <T> Specification<T> notDeleted() {
        return Specification.where(new DeletedIsNull<T>()).or(new DeletedIsFalse<>());
    }

    @Override
    public List<T> findAll() {
        return super.findAll(notDeleted());
    }

    @Override
    public List<T> findAll(Sort sort) {
        return super.findAll(notDeleted(), sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return super.findAll(notDeleted(), pageable);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        if (!ids.iterator().hasNext()) {
            return Collections.emptyList();
        }

        if (entityInformation.hasCompositeId()) {
            List<T> results = new ArrayList<>();

            for (ID id : ids) {
                findById(id).ifPresent(results::add);
            }

            return results;
        }

        Collection<ID> idCollection = Streamable.of(ids).toList();

        ByIds<T> specification = new ByIds<>(entityInformation);
        TypedQuery<T> query = getQuery(Specification.where(specification).and(notDeleted()), Sort.unsorted());

        return query.setParameter(specification.parameter, idCollection).getResultList();
    }

    @Override
    public long count() {
        return super.count(notDeleted());
    }

    @Override
    public void deleteById(ID id) {
        T entity = this.findById(id).orElseThrow();
        this.delete(entity);
    }

    @Override
    public void delete(T entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Class<T> domainType = getDomainClass();
        CriteriaUpdate<T> update = cb.createCriteriaUpdate(domainType);

        Root<T> root = update.from(domainType);
        update.set(DELETED, Boolean.TRUE);

        final List<Predicate> predicates = new ArrayList<>();

        if (entityInformation.hasCompositeId()) {
            for (String s : entityInformation.getIdAttributeNames())
                predicates.add(cb.equal(root.<ID>get(s),
                        entityInformation.getCompositeIdAttributeValue(Objects.requireNonNull(entityInformation.getId(entity)), s)));
            update.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        } else
            update.where(cb.equal(root.<ID>get(Objects.requireNonNull(
                            entityInformation.getIdAttribute()).getName()),
                    entityInformation.getId(entity)));

        em.createQuery(update).executeUpdate();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            this.delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        for (T entity : this.findAll()) {
            this.delete(entity);
        }
    }

    @Override
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return super.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return super.findOne(
                Specification.where(new ById<T, ID>(entityInformation, id)).and(notDeleted())
        );
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public void flush() {
        super.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return super.saveAndFlush(entity);
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return super.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> ids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public T getById(ID id) {
        return this.findById(id).orElseThrow();
    }

    private static final class ById<T, ID extends Serializable> implements Specification<T> {

        private final JpaEntityInformation<T, ?> entityInformation;
        private final ID id;

        public ById(JpaEntityInformation<T, ?> entityInformation, ID id) {
            this.entityInformation = entityInformation;
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            final List<Predicate> predicates = new ArrayList<>();
            if (entityInformation.hasCompositeId()) {
                for (String s : entityInformation.getIdAttributeNames()) {
                    predicates.add(cb.equal(root.<ID>get(s), entityInformation.getCompositeIdAttributeValue(id, s)));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return cb.equal(root.<ID>get(Objects.requireNonNull(entityInformation.getIdAttribute()).getName()), id);
        }
    }

    @SuppressWarnings("rawtypes")
    private static final class ByIds<T> implements Specification<T> {

        private static final long serialVersionUID = 1L;

        private final JpaEntityInformation<T, ?> entityInformation;

        @Nullable
        ParameterExpression<Collection<?>> parameter;

        ByIds(JpaEntityInformation<T, ?> entityInformation) {
            this.entityInformation = entityInformation;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

            Path<?> path = root.get(entityInformation.getIdAttribute());
            parameter = (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
            return path.in(parameter);
        }
    }

    private static final class DeletedIsFalse<T> implements Specification<T> {
        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.<Boolean>get(DELETED), Boolean.FALSE);
        }
    }

    private static final class DeletedIsNull<T> implements Specification<T> {
        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.isNull(root.<Boolean>get(DELETED));
        }
    }

}
