package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.*;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.CartItemsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @Autowired
    private CartItemsServiceImpl cartItemsServiceImpl;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

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

//    @RequestMapping(value = "/cart")
//    public ModelAndView getCart(){
//        ModelAndView modelAndView = new ModelAndView("cart");
//
//        UserDetails userDetails = userDetailsServiceImpl.getLoggedInUser();
//        if (userDetails != null) {
//
//            String userId = userDetails.getUserId();
//            DynamicAnyRes dynamicAnyRes = cartItemsServiceImpl.getMyCartItems(userId);
//            List<CartDetails> cartDetailsList = dynamicAnyRes.getResults();
//            modelAndView.addObject("cartDetailsList", cartDetailsList);
//
//
//        }else {
//            DynamicAnyRes dynamicAnyRes = cartItemsServiceImpl.getAllCartItems();
//            List<CartDetails> cartDetailsList = dynamicAnyRes.getResults();
//            modelAndView.addObject("cartDetailsList", cartDetailsList);
//            modelAndView.addObject("totalPrice", cartDetailsList);
//        }
//
//
//        return modelAndView;
//    }

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

    @RequestMapping(value = "/admin/dashboard")
    public String viewAdminDashboard(){
        return "admin/dashboard";
    }

    @RequestMapping(value = "admin/view-product-details/{productId}", method = RequestMethod.GET)
    public ModelAndView getAdminProductDetail(@PathVariable("productId") String productId){

        ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);
        ModelAndView modelAndView = new ModelAndView("admin/view_product_details");
        if (productDetails != null){

            modelAndView.addObject("productDetails", productDetails);

        }else {

            //            modelAndView.addObject("productDetails", productDetails);

        }
        return modelAndView;


    }



    @RequestMapping(value = "/admin/add-product")
    public String addProducts(){
        return "admin/add_products";
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


        ModelAndView modelAndView = new ModelAndView("admin/view_products");
        modelAndView.addObject("productDetailsList", productDisplayList);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);

        return modelAndView;
    }
    @RequestMapping(value = "/admin/view-orders")
    public String viewAdminOrders(){
        return "admin/view_orders";
    }
    @RequestMapping(value = "/admin/view-users")
    public String viewAdminUsers(){
        return "admin/view_users";
    }

//    @RequestMapping(value = "/admin/view-product-details")
//    public String viewAdminProductDetails(){
//        return "admin/view_product_details";
//    }

    @RequestMapping(value = "/admin/view-order-details")
    public String viewAdminOrderDetails(){
        return "admin/view_orders_details";
    }

}
