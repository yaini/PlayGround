package com.yaini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomJPARepository<T, ID extends Serializable> extends JpaRepository<T, ID> {


}
