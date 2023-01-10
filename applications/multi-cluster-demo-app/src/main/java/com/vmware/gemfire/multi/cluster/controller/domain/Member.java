package com.vmware.gemfire.multi.cluster.controller.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Member Data
 * @author gregory green
 */
@Data
@NoArgsConstructor
public class Member {
    private String id;
    private String email;
}
