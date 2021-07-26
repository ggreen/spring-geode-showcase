package com.vmware.spring.geode.showcase.repostories;

import com.vmware.spring.geode.showcase.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location,String>
{
}
