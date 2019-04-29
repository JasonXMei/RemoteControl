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
public class TaskPage<T> extends Page<T> {
    private String searchName = "";
    private Integer searchStatus = -1;

    private String searchStartDateStr = "";
    private String searchEndDateStr = "";
    private Long searchStartDate;
    private Long searchEndDate;
    private Integer searchUserId;

    private long searchCurrent = 1;
    private long pages;

    public TaskPage(){
        super();
    }
    public TaskPage(long current, long size) {
        super(current, size);
    }
}
