package com.centafrique.lancelinvestment.service_data.service_impl;

import com.centafrique.lancelinvestment.entity.ProductImages;
import com.centafrique.lancelinvestment.entity.ProductSizes;
import com.centafrique.lancelinvestment.entity.Products;
import com.centafrique.lancelinvestment.helper_class.DynamicFullRes;
import com.centafrique.lancelinvestment.helper_class.Formatter;
import com.centafrique.lancelinvestment.helper_class.ProductDetails;
import com.centafrique.lancelinvestment.repository.ProductImagesRepository;
import com.centafrique.lancelinvestment.repository.ProductSizesRepository;
import com.centafrique.lancelinvestment.repository.ProductsRepository;
import com.centafrique.lancelinvestment.service_data.service.ProductImagesServices;
import com.centafrique.lancelinvestment.service_data.service.ProductSizesInfo;
import com.centafrique.lancelinvestment.service_data.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService, ProductSizesInfo, ProductImagesServices {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ProductSizesRepository productSizesRepository;
    @Autowired
    private ProductImagesRepository productImagesRepository;


    @Override
    public Products addProduct(Products products) {
        return productsRepository.save(products);
    }

    @Override
    public Products getProduct(String productId) {
        Optional<Products> optionalProducts = productsRepository.findById(productId);
        return optionalProducts.orElse(null);
    }

    @Override
    public List<Products> getProductsList(int pageNo, int pageSize, String sortField, String sortDirection) {

        return findPaginatedProducts(pageNo, pageSize, sortField, sortDirection);
    }

    /**
     * Product Sizes
     */

    @Override
    public ProductSizes addProductSizes(ProductSizes productSizes) {
        return productSizesRepository.save(productSizes);
    }

    @Override
    public List<ProductSizes> getProductSizes(String productId) {
        return productSizesRepository.findAll();
    }

    /**
     *Product Images
     */

    @Override
    public ProductImages addProductImages(ProductImages productImages) {
        return productImagesRepository.save(productImages);
    }

    @Override
    public List<ProductImages> getAllProductImages(String productId) {
        return productImagesRepository.findAll();
    }

    /**
     * Functionality
     */

    public ProductDetails addProductDetails(ProductDetails productDetails){

        String productName = productDetails.getProductName();
        String productDescription = productDetails.getProductDescription();
        String productIngredients = productDetails.getProductIngredients();

        List<ProductSizes> productSizesList = productDetails.getProductSizes();
        List<ProductImages> productImagesList = productDetails.getProductImages();

        Products products = new Products(productName, productDescription, productIngredients);

        Formatter formatter = new Formatter();
        Products productsDataDetails = formatter.productDetails(productsRepository, products);
//        Products productsDataDetails = addProduct(products);
        if (productsDataDetails != null){

            String productId = productsDataDetails.getId();

            List<ProductSizes> resProductSizesList = new ArrayList<>();
            for (ProductSizes sizes : productSizesList) {

                double sizeAmount = sizes.getSizeAmount();
                String sizeUnit = sizes.getSizeUnit();

                double newPrice = sizes.getNewPrice();
                double oldPrice = sizes.getOldPrice();
                double stockAmount = sizes.getStockNumber();

                ProductSizes productSizes = new ProductSizes(productId, sizeAmount, sizeUnit, newPrice, oldPrice, stockAmount);
                ProductSizes resProductSizes = addProductSizes(productSizes);
                resProductSizesList.add(resProductSizes);

            }

            List<ProductImages> resProductImagesList = new ArrayList<>();
            for (ProductImages productImages : productImagesList){

                String imagePath = productImages.getImagePath();

                ProductImages images = new ProductImages(imagePath, productId);
                ProductImages resProdImages = addProductImages(images);
                resProductImagesList.add(resProdImages);
            }

            return new ProductDetails(productId,productName, productDescription, productIngredients, resProductSizesList, resProductImagesList);

        }else {
            return null;
        }

    }

    public List<Products> findPaginatedProducts(int pageNo, int pageSize, String sortField, String sortDirection){

        String sortPageField = "";
        String sortPageDirection = "";

        if (sortField.equals("")){sortPageField = "createdAt";}else {sortPageField = sortField;}
        if (sortDirection.equals("")){sortPageDirection = "DESC";}else {sortPageDirection = sortField;}

        Sort sort = sortPageDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPageField).ascending() :
                Sort.by(sortPageField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Products> productsPage = productsRepository.findAll(pageable);
        return productsPage.getContent();

    }

    public DynamicFullRes getPaginatedProducts(int pageNo, int pageSize, String sortField, String sortDirection){

        List<ProductDetails> productDetailsList = new ArrayList<>();

        List<Products> productsList = getProductsList(pageNo, pageSize, sortField, sortDirection);
        for (Products products : productsList){

            String productName = products.getProductName();
            String productDescription = products.getProductDescription();
            String productIngredients = products.getIngredients();
            String productId = products.getId();

            List<ProductSizes> productSizesList = getProductSizes(productId);
            List<ProductImages> productImagesList = getAllProductImages(productId);

            ProductDetails productDetails = new ProductDetails(productId,productName, productDescription,productIngredients, productSizesList, productImagesList);
            productDetailsList.add(productDetails);
        }


        return new DynamicFullRes(
                productDetailsList.size(),
                null,
                null,
                productDetailsList);

    }

    public ProductDetails getProductDetails(String productId){
        Products products = getProduct(productId);
        if (products != null){

            String productName = products.getProductName();
            String productDescription = products.getProductDescription();
            String productIngredients = products.getIngredients();

            List<ProductSizes> productSizesList = getProductSizes(productId);
            List<ProductImages> productImagesList = getAllProductImages(productId);

            return new ProductDetails(productId,productName, productDescription,productIngredients, productSizesList, productImagesList);
        }else {
            return null;
        }
    }
}
