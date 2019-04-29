package com.jason.web;

import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.util.PageUtil;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;
import com.jason.web.service.TaskReplaceOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReplaceOrderServiceTest {

    @Autowired
    private TaskReplaceOrderService taskReplaceOrderService;

    @Test
    public void findReplaceOrderList(){
        TaskPage<TaskReplaceOrder> pages = new TaskPage<>();
        //pages.setSearchStatus(1);
        //pages.setSearchName("j4");
        //pages.setSearchEndDateStr("2019-04-28");
        //pages.setSearchStartDateStr("2019-04-26");
        pages.setSearchUserId(1);
        TaskPage<TaskReplaceOrderVO> pagesVO = taskReplaceOrderService.handleList(pages);

        System.out.println("当前页数 ------> " + pagesVO.getCurrent());
        System.out.println("总条数 ------> " + pagesVO.getTotal());

        System.out.println("总页数 ------> " + PageUtil.getPages(pagesVO.getTotal(), pagesVO.getSize()));
        System.out.println("当前每页显示数 ------> " + pagesVO.getSize());
        print(pagesVO.getRecords());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
