package com.project.postly.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.postly.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByProfileIdAndEntryId(Long userId, Long postId);

	List<Feedback> findByProfileId(Long userId);

	List<Feedback> findByEntityId(Long postId);
	
	@Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
			+ "feedback c left join user u on u.id = c.user_id "
			+ "where c.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserCommentsByEntryId(@Param("postIds") List<Long> postIds);

}
