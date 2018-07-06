package net.xxx.sb0.linkage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.xxx.sb0.linkage.entity.PjCode;

@Repository
public interface PjCodeRepo extends JpaRepository<PjCode, Long> {

	List<PjCode> findByCode(String code);
}
