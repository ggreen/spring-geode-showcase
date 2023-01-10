package com.vmware.gemfire.multi.cluster.controller.repository;

import com.vmware.gemfire.multi.cluster.controller.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Member data repository
 * @author gregory green
 */
@Repository
public interface MemberRepository extends CrudRepository<Member,String> {
}
