package com.jason.common.util;

public class PageUtil {

    public static long getPages(long total, long size){
        long pages = total/10;
        if(pages == 0){
            pages = 1;
        }else{
            long count = total%10;
            if(count != 0){
                pages += 1;
            }
        }
        return pages;
    }
}
