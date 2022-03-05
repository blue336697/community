package com.lhjitem.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther lhj
 * @date 2022/1/26 14:29
 * 来满足分页功能所需的数据（页码等等）传输
 */
@Data
public class PageDTO<T> {
    //问题的各种信息
    private List<T> data;
    //前进一页按钮
    private boolean showPrevious;
    //前进到第一页
    private boolean showFirstPage;
    //下一页按钮
    private boolean showNext;
    //到最后一页
    private boolean showEndPage;
    //当前的页码
    private Integer currentPage;
    //当前展示的全部页码列表（约定最多为7个）
    private List<Integer> pages = new ArrayList<>();
    //全部页数
    private Integer totalPage;

    /**
     *
     * @param totalCount
     * @param page      当前页
     */
    public void setPagination(Integer totalCount, Integer page) {

        this.totalPage = totalCount;

        currentPage = page;

        pages.add(page);
        //向前展示三个页码，向后展示三个页码
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0)
                pages.add(0, page - i);
            if (page + i <= totalPage)
                pages.add(page + i);
        }

            //展示前一页按钮
        if(page == 1)
            showPrevious = false;      //这里利用了Boolean本来默认值就是true
        else
            showPrevious = true;

        //展示后一页按钮
        if(page == totalPage)
            showNext = false;
        else
            showNext = true;


        //展示到第一页和最后一页的按钮，约定当第一页或最后一页消失在分页条中就出现各自的按钮
        if(pages.contains(1))
            showFirstPage = false;
        else
            showFirstPage = true;

        if(pages.contains(totalPage))
            showEndPage = false;
        else
            showEndPage = true;

        //这里因为当查出的page为0是，什么按钮都不要显示就好
        if(page == 0){
            showPrevious = false;
            showFirstPage = false;
        }
    }
}
