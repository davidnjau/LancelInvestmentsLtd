package com.centafrique.lancelinvestment.user_webiste.service_data.service_impl;

import com.centafrique.lancelinvestment.user_webiste.entity.Blogs;
import com.centafrique.lancelinvestment.user_webiste.entity.Products;
import com.centafrique.lancelinvestment.user_webiste.helper_class.BlogRes;
import com.centafrique.lancelinvestment.user_webiste.repository.BlogsRepository;
import com.centafrique.lancelinvestment.user_webiste.service_data.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
public class BlogsServiceImpl implements BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    @Override
    public Blogs addBlogs(Blogs blogs) {

        return blogsRepository.save(blogs);
    }

    @Override
    public List<Blogs> blogList(int pageNo, int pageSize, String sortField, String sortDirection) {

        String sortPageField = "";
        String sortPageDirection = "";

        if (sortField.equals("")){sortPageField = "createdAt";}else {sortPageField = sortField;}
        if (sortDirection.equals("")){sortPageDirection = "DESC";}else {sortPageDirection = sortField;}

        Sort sort = sortPageDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPageField).ascending() :
                Sort.by(sortPageField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Blogs> blogs = blogsRepository.findAll(pageable);
        return blogs.getContent();

    }

    @Override
    public Blogs blogDetails(String id) {
        return blogsRepository.getById(id);
    }

    public Blogs updateBlog(String blogId, Blogs blogs){

        return blogsRepository.findById(blogId)
                .map(oldBlog -> {

                    oldBlog.setBlogTitle(blogs.getBlogTitle());
                    oldBlog.setBlogDetails(blogs.getBlogDetails());
                    oldBlog.setFeaturedImage(blogs.getFeaturedImage());
                    return blogsRepository.save(oldBlog);
                }).orElse(null);
    }

    public boolean isFile(String fileName){
        return blogsRepository.existsByFeaturedImage(fileName);
    }

    public BlogRes findPaginatedBlogs(int pageNo, int pageSize, String sortField, String sortDirection){

        List<Blogs> blogList = blogList(pageNo, pageSize, sortField, sortDirection);

        return new BlogRes(
                blogList.size(),
                null,
                null,
                blogList);

    }

}
