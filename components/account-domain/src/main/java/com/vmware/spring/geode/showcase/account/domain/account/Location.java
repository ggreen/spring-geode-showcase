package com.vmware.spring.geode.showcase.account.domain.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Location
{
    private String id;
    private String address;
    private String city;
    private String stateCode;
    private String zipCode;
}
