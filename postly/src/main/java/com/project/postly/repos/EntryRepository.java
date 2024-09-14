package com.project.postly.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.postly.entities.Entry; // Post yerine Entry olarak güncellendi

public interface EntryRepository extends JpaRepository<Entry, Long> { // Post yerine Entry olarak güncellendi

	List<Entry> findByEntryId(Long userId); // Post yerine Entry olarak güncellendi

	@Query(value = "select id from entry where user_id = :userId order by create_date desc limit 5", // post yerine entry olarak güncellendi
			nativeQuery = true)
	List<Long> findTopByEntryId(@Param("userId") Long userId);
}
