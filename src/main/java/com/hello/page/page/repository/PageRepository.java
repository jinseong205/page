package com.hello.page.page.repository;

import com.hello.page.page.entity.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long>{
    public Optional<Page> findById(Long id);
}
