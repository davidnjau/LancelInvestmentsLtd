package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.user_webiste.helper_class.DynamicFullRes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.ProductDetails;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getBlog(){
        return "blog";
    }

    @RequestMapping(value ="/blog-details")
    public String getBlogDetails(){
        return "blog_details";
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
        if (productDetails != null){

            ModelAndView modelAndView = new ModelAndView("shop_single");
            modelAndView.addObject("productDetails", productDetails);
            return modelAndView;

        }else {

            ModelAndView modelAndView = new ModelAndView("shop_single");
//            modelAndView.addObject("productDetails", productDetails);
            return modelAndView;

        }


    }

    @RequestMapping(value = "/shop-details")
    public String getShopDetail(){
        return "shop_single";
    }

    @RequestMapping(value = "/gallery")
    public String getGallery(){
        return "gallery";
    }

    @RequestMapping(value = "/admin/dashboard")
    public String viewAdminDashboard(){
        return "admin/dashboard";
    }

    @RequestMapping(value = "/admin/view-products")
    public String viewProducts(){
        return "admin/products";
    }

    @RequestMapping(value = "/admin/add-product")
    public String addProducts(){
        return "admin/add_products";
    }
}
