package com.vmare.gemfire.multi.cluster.demo.repository;

import com.vmare.gemfire.multi.cluster.demo.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,String> {
}
