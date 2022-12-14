package com.vmare.gemfire.multi.cluster.demo.repository;

import com.vmare.gemfire.multi.cluster.demo.domain.Claim;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends CrudRepository<Claim,String> {
}
