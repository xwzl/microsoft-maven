package com.cloud.common.dtos;

import lombok.Data;
import lombok.ToString;

/**
 * @author xuweizhi
 * @date 2019/08/06 20:41
 */
@Data
@ToString
public class UserDTO {

    private String username;

    private String password;

    private Integer age;

    private Long id;
}
