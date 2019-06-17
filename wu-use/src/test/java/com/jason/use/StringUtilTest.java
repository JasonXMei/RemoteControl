package com.jason.use;

import com.jason.use.util.StringUtil;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void isNumeric(){
        System.out.println(StringUtil.isNumeric("jason"));
        System.out.println(StringUtil.isNumeric("11"));
        System.out.println(StringUtil.isNumeric("你好"));
        System.out.println(StringUtil.isNumeric("-1"));
        System.out.println(StringUtil.isNumeric("1.1"));
    }

}
