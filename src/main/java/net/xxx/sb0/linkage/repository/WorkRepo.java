package net.xxx.sb0.linkage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.xxx.sb0.linkage.entity.Work;

public interface WorkRepo extends JpaRepository<Work, Long> {
	@Query(value = "SELECT code, SUM(hours) hours FROM work GROUP BY code", nativeQuery = true)
	public List<?> findGroupHours();
}
