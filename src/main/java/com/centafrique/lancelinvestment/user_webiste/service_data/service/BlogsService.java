package com.centafrique.lancelinvestment.user_webiste.service_data.service;

import com.centafrique.lancelinvestment.user_webiste.entity.Blogs;

import java.util.List;

public interface BlogsService {

    Blogs addBlogs(Blogs blogs);
    List<Blogs> blogList(int pageNo, int pageSize, String sortField, String sortDirection);
    Blogs blogDetails(String id);


}
