package com.vmware.gemfire.multi.cluster.controller.repository;

import com.vmware.gemfire.multi.cluster.controller.domain.Claim;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Claim data repository
 * @author gregory green
 */
@Repository
public interface ClaimRepository extends CrudRepository<Claim,String> {
}
