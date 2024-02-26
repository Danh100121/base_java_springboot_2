package com.demo.base_java.repository;


import com.demo.base_java.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestModelRepository extends JpaRepository<TestModel, Integer> {
}
