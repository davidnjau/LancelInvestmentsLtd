package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.authentication.entity.Role;
import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.entity.Blogs;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.*;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.BlogsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.CartItemsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.ProductsServiceImpl;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @Autowired
    private CartItemsServiceImpl cartItemsServiceImpl;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private BlogsServiceImpl blogsService;

    private final OkHttpClient httpClient = new OkHttpClient();


    @RequestMapping(value ="/")
    public String getHome(){
        return "home";
    }
    @RequestMapping(value ="/login")
    public String getLogin(){
        return "login";
    }


    @RequestMapping(value ="/about_us")
    public String getAboutUs(){
        return "about_us";
    }
    @RequestMapping(value ="/services")
    public String getServices(){
        return "services";
    }

    @RequestMapping(value ="/blog")
    public ModelAndView getBlog(){

        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDirection = "DESC";

        BlogRes dynamicFullRes = blogsService.findPaginatedBlogs(pageNo, pageSize, sortField, sortDirection);

        List<Blogs> blogDetailsList = dynamicFullRes.getResults();

        List<BlogDetails> blogDetailsListData = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd");

        for (int i = 0; i < blogDetailsList.size(); i++){

            String title = blogDetailsList.get(i).getBlogTitle();
            String details = blogDetailsList.get(i).getBlogDetails();

            String detailsData = "";
            if (details.length() > 500){
                detailsData = details.substring(0, 500);
            }else {
                detailsData = details.substring(0, details.length());
            }

            Date createdAt = blogDetailsList.get(i).getCreatedAt();
            String id = blogDetailsList.get(i).getId();
            String featuredImage = blogDetailsList.get(i).getFeaturedImage();

            String dateCreated = sdf.format(createdAt);

            BlogDetails blogDetails = new BlogDetails(""+i+1, title, detailsData+"... ", dateCreated, id, featuredImage);
            blogDetailsListData.add(blogDetails);

        }


        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("blogDetailsList", blogDetailsListData);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }

    @RequestMapping(value ="/blog-details/{blogId}")
    public ModelAndView getUserBlogDetails(@PathVariable("blogId") String blogId){

        Blogs blogDetails = blogsService.blogDetails(blogId);

        String id = blogDetails.getId();
        String blogTitle = blogDetails.getBlogTitle();
        String blogDesc = blogDetails.getBlogDetails();
        String featuredImage = blogDetails.getFeaturedImage();


        ModelAndView modelAndView = new ModelAndView("blog_details");
        modelAndView.addObject("id", id);
        modelAndView.addObject("blogTitle", blogTitle);
        modelAndView.addObject("blogDesc", blogDesc);
        modelAndView.addObject("featuredImage", featuredImage);

        return modelAndView;
    }

    @RequestMapping(value ="/checkout")
    public String getCheckout(){
        return "check_out";
    }

    @RequestMapping(value = "/shop")
    public ModelAndView getShop(){

        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDescription = "DESC";

        DynamicFullRes dynamicRes = productsServiceImpl.getPaginatedProducts(pageNo, pageSize, sortField, sortDescription);

        List<ProductDetails> productDetailsList = dynamicRes.getResults();

        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("productDetailsList", productDetailsList);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }

    @RequestMapping(value = "shop-details/{productId}", method = RequestMethod.GET)
    public ModelAndView getProductDetail(@PathVariable("productId") String productId){

        ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);
        ModelAndView modelAndView = new ModelAndView("shop_single");
        if (productDetails != null){

            modelAndView.addObject("productDetails", productDetails);

        }else {

            //            modelAndView.addObject("productDetails", productDetails);

        }
        return modelAndView;


    }

    @RequestMapping(value = "/cart")
    public String getCart(){
        return "cart";
    }

    @RequestMapping(value = "/shop-details")
    public String getShopDetail(){
        return "shop_single";
    }

    @RequestMapping(value = "/gallery")
    public String getGallery(){
        return "gallery";
    }

    @RequestMapping(value = "/admin/dashboard/{userId}" )
    public String viewAdminDashboard(@PathVariable("userId") String userId) {


        UserDetails user = userDetailsServiceImpl.getUserDetails(userId);
        if (user != null){

            List<String> roleList = user.getRolesCollection().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            if (!roleList.isEmpty() && roleList.contains("ROLE_ADMIN")){
                return "dashboard";
            }else {
                return "/login";
            }

        }else {
            return getLogin();
        }




    }

    @RequestMapping(value = "/api/v1/admin/home", method = RequestMethod.GET)
    public ModelAndView getAdminDashBoard(@RequestHeader("Authorization") String Authorization) {
        return new ModelAndView("dashboard");
    }

    @RequestMapping(value = "admin/view-product-details/{productId}", method = RequestMethod.GET)
    public ModelAndView getAdminProductDetail(@PathVariable("productId") String productId){

        ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);
        ModelAndView modelAndView = new ModelAndView("view_product_details");
        if (productDetails != null){

            modelAndView.addObject("productDetails", productDetails);

        }else {

            //            modelAndView.addObject("productDetails", productDetails);

        }
        return modelAndView;


    }



    @RequestMapping(value = "/admin/add-product")
    public String addProducts(){
        return "add_products";
    }

    @RequestMapping(value = "/admin/add_blog")
    public String addBlog(){
        return "add_blog";
    }


    @RequestMapping(value = "/admin/view-products")
    public ModelAndView viewAdminProducts(){
        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDescription = "DESC";

        DynamicFullRes dynamicRes = productsServiceImpl.getPaginatedProducts(pageNo, pageSize, sortField, sortDescription);

        List<ProductDetails> productDetailsList = dynamicRes.getResults();
        List<ProductDisplay> productDisplayList = new ArrayList<>();

        for (int i = 0; i< productDetailsList.size(); i++){

            String productId = productDetailsList.get(i).getProductId();
            String productName = productDetailsList.get(i).getProductName();

            List<Double> sizeAmountList = new ArrayList<>();
            List<Double> priceList = new ArrayList<>();
            List<String> sizeUnitList = new ArrayList<>();

            List<ProductSizes> sizeList = productDetailsList.get(i).getProductSizes();
            for (int j = 0; j < sizeList.size(); j++){

                double sizeAmount = sizeList.get(j).getSizeAmount();
                double price = sizeList.get(j).getNewPrice();
                String sizeUnit = sizeList.get(j).getSizeUnit();

                sizeAmountList.add(sizeAmount);
                priceList.add(price);
                sizeUnitList.add(sizeUnit);

            }

            double sizeAmount = 0.0;
            double price = 0.0;
            String sizeUnit = "";

            if (!sizeAmountList.isEmpty()){

                int minIndex = sizeAmountList.indexOf(Collections.min(sizeAmountList));

                sizeAmount = sizeAmountList.get(minIndex);
                price = priceList.get(minIndex);
                sizeUnit = sizeUnitList.get(minIndex);
            }
            int indexPos = i + 1;

            ProductDisplay productDisplay = new ProductDisplay(""+indexPos, productId, productName,
                    sizeAmount+" " + sizeUnit, "Ksh "+ price);
            productDisplayList.add(productDisplay);
        }


        ModelAndView modelAndView = new ModelAndView("view_products");
        modelAndView.addObject("productDetailsList", productDisplayList);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }
    @RequestMapping(value = "/admin/view-orders")
    public String viewAdminOrders(){
        return "view_orders";
    }
    @RequestMapping(value = "/admin/view-users")
    public ModelAndView viewAdminUsers(){

        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDescription = "DESC";

        List<UserDetails> userList = userDetailsServiceImpl.getPaginatedUsers(pageNo, pageSize, sortField, sortDescription);
        ModelAndView modelAndView = new ModelAndView("view_users");
        modelAndView.addObject("userList", userList);

        return modelAndView;
    }

    @RequestMapping(value = "/admin/view-order-details")
    public String viewAdminOrderDetails(){
        return "view_orders_details";
    }

    @RequestMapping(value = "/admin/view_blogs/{blogId}")
    public ModelAndView getBlogDetails(@PathVariable("blogId") String blogId){

        Blogs blogDetails = blogsService.blogDetails(blogId);

        String id = blogDetails.getId();
        String blogTitle = blogDetails.getBlogTitle();
        String blogDesc = blogDetails.getBlogDetails();
        String featuredImage = blogDetails.getFeaturedImage();


        ModelAndView modelAndView = new ModelAndView("view_blog_details");
        modelAndView.addObject("id", id);
        modelAndView.addObject("blogTitle", blogTitle);
        modelAndView.addObject("blogDesc", blogDesc);
        modelAndView.addObject("featuredImage", featuredImage);

        return modelAndView;
    }

    @RequestMapping(value = "/admin/view_blogs")
    public ModelAndView getBlogList(){

        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDirection = "DESC";

        BlogRes dynamicFullRes = blogsService.findPaginatedBlogs(pageNo, pageSize, sortField, sortDirection);

        List<Blogs> blogDetailsList = dynamicFullRes.getResults();

        ModelAndView modelAndView = new ModelAndView("view_blogs");
        modelAndView.addObject("blogDetailsList", blogDetailsList);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }

}
