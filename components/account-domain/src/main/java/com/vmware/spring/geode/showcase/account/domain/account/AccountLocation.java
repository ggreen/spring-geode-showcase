package com.vmware.spring.geode.showcase.account.domain.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountLocation
{
    private Account account;
    private Location location;

}
