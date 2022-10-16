package com.yaini.adapter.out.persistence.repository;

import com.yaini.adapter.out.persistence.entity.StudentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  Optional<StudentEntity> findByStudentNumber(final String studentNumber);
}
