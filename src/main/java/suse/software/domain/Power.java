package suse.software.domain;

import lombok.Data;

/**
 * 权限控制相关
 */
@Data
public class Power {
    private String powername;
    private Integer score;
}
