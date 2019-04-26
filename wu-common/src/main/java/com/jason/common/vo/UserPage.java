package com.jason.common.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserPage<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    private String searchName;
    private Integer searchUserStatus;
    private Integer referrerUserId;

    private long searchCurrent;
    private long pages;

    public  UserPage(){
        super();
    }
    public UserPage(long current, long size) {
        super(current, size);
    }
}
