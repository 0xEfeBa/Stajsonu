package com.project.postly.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.postly.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
	
}
