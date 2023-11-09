package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Mix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MixRepository extends JpaRepository<Mix, Long> {
}
