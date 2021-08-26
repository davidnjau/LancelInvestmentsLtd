package com.centafrique.lancelinvestment.controller;

import com.centafrique.lancelinvestment.helper_class.DynamicFullRes;
import com.centafrique.lancelinvestment.helper_class.DynamicRes;
import com.centafrique.lancelinvestment.helper_class.ProductDetails;
import com.centafrique.lancelinvestment.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @RequestMapping(value ="/")
    public String getHome(){
        return "/users/home";
    }

    @RequestMapping(value ="/blog")
    public String getBlog(){
        return "/users/blog";
    }

    @RequestMapping(value ="/blog-details")
    public String getBlogDetails(){
        return "/users/blog_details";
    }

    @RequestMapping(value = "/shop")
    public ModelAndView getShop(){

        int pageNo = 1;
        int pageSize = 9;
        String sortField = "createdAt";
        String sortDescription = "DESC";

        DynamicFullRes dynamicRes = productsServiceImpl.getPaginatedProducts(pageNo, pageSize, sortField, sortDescription);

        List<ProductDetails> productDetailsList = dynamicRes.getResults();

        ModelAndView modelAndView = new ModelAndView("/users/shop");
        modelAndView.addObject("productDetailsList", productDetailsList);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }

    @RequestMapping(value = "shop-details/{productId}", method = RequestMethod.GET)
    public ModelAndView getProductDetail(@PathVariable("productId") String productId){

        ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);
        if (productDetails != null){

            ModelAndView modelAndView = new ModelAndView("/users/shop_single");
            modelAndView.addObject("productDetails", productDetails);
            return modelAndView;

        }else {

            ModelAndView modelAndView = new ModelAndView("/users/shop_single");
//            modelAndView.addObject("productDetails", productDetails);
            return modelAndView;

        }


    }

    @RequestMapping(value = "/shop-details")
    public String getShopDetail(){
        return "/users/shop_single";
    }

    @RequestMapping(value = "/gallery")
    public String getGallery(){
        return "/users/gallery";
    }

    @RequestMapping(value = "/admin/dashboard")
    public String viewAdminDashboard(){
        return "/admin/dashboard";
    }

    @RequestMapping(value = "/admin/view-products")
    public String viewProducts(){
        return "/admin/products";
    }

    @RequestMapping(value = "/admin/add-product")
    public String addProducts(){
        return "/admin/add_products";
    }
}
