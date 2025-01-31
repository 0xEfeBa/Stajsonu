package com.project.postly.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.postly.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
              
	List<Like> findByProfileIdAndEntryId(Long userId, Long postId);

	List<Like> findByProfileId(Long userId);

	List<Like> findByEntryId(Long postId);

	@Query(value = 	"select 'liked', l.post_id, u.avatar, u.user_name from "
			+ "p_like l left join user u on u.id = l.user_id "
			+ "where l.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserLikesByEntryId(@Param("postIds") List<Long> postIds);
}
