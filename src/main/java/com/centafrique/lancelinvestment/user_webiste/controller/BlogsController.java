package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.user_webiste.entity.Blogs;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import com.centafrique.lancelinvestment.user_webiste.entity.Products;
import com.centafrique.lancelinvestment.user_webiste.helper_class.BlogRes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.DynamicFullRes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.DynamicRes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.ProductDataDetails;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.BlogsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
public class BlogsController {

    @Autowired
    private BlogsServiceImpl blogsService;

    @Autowired
    private ProductController productController;

    @RequestMapping(value = "/api/v1/blogs/add-blog", method = RequestMethod.POST)
    public ResponseEntity addBlog(
            @RequestParam("blog") String blog,
            @RequestParam("file") MultipartFile file,
            @RequestParam("blogName") String blogName
    ) throws IOException {

        String fileUrl = productController.uploadSingleFile(file);
        Blogs blogs = new Blogs(blogName, blog, fileUrl);

        Blogs addedBlogs = blogsService.addBlogs(blogs);
        if (addedBlogs != null){
            return new ResponseEntity(addedBlogs, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Blog could not be added. Please try again."));
        }

    }
    @RequestMapping(value = "/api/v1/blogs/get-blogs", method = RequestMethod.GET)
    public ResponseEntity getBlog(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "9") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "DESC") String sortDirection){

        BlogRes dynamicFullRes = blogsService.findPaginatedBlogs(pageNo, pageSize, sortField, sortDirection);
        if (dynamicFullRes != null){
            return new ResponseEntity(dynamicFullRes, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
        }

    }

    @RequestMapping(value = "/api/v1/blogs/blog-details/{blogId}", method = RequestMethod.GET)
    public ResponseEntity getBlogDetails(@PathVariable("blogId") String blogId){

        Blogs blogDetails = blogsService.blogDetails(blogId);
        if (blogDetails != null){
            return new ResponseEntity(blogDetails, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Blog detail could not be found. Please try again."));
        }

    }

    @RequestMapping(value = "/api/v1/blogs/update-blog-details/", method = RequestMethod.PUT)
    public ResponseEntity updateBlogDetails(
            @RequestParam("blog") String blog,
            @RequestParam("file") MultipartFile file,
            @RequestParam("blogName") String blogName,
            @RequestParam("blogId") String blogId
    ) throws IOException {

        String fileName = "";
        String oldFile = file.getOriginalFilename();

        boolean isFile = blogsService.isFile(getStringUrl(oldFile));
        if (!isFile){
            fileName = productController.uploadSingleFile(file);
        }else {
            fileName = blogsService.blogDetails(blogId).getFeaturedImage();
        }
        Blogs blogs = new Blogs(blogName, blog, fileName);
        Blogs blogDetails = blogsService.updateBlog(blogId,blogs);

        if (blogDetails != null){
            return new ResponseEntity(blogDetails, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Blog detail could not be found. Please try again."));
        }

    }

    private String getStringUrl(String file){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(file)
                .toUriString();
    }


}
