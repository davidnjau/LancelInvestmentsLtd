package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl;
import com.centafrique.lancelinvestment.storage.FileStorageService;
import com.centafrique.lancelinvestment.user_webiste.entity.FileData;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductImages;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import com.centafrique.lancelinvestment.user_webiste.entity.Products;
import com.centafrique.lancelinvestment.user_webiste.helper_class.*;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.CartItemsServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.FileUploadUtil;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.FilesStorageServiceImpl;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @Autowired
    private CartItemsServiceImpl cartItemsServiceImpl;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private FilesStorageServiceImpl filesStorageServiceImpl;

    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/api/v1/products/add_products_details", method = RequestMethod.POST)
    public ResponseEntity addProductDetails(@RequestBody ProductDataDetails productDataDetails){

        Products respProductDetails = productsServiceImpl.addProductDataDetails(productDataDetails);
        if (respProductDetails != null){
            return new ResponseEntity(respProductDetails, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Product could not be added. Please try again."));
        }

    }

    @RequestMapping(value = "/api/v1/products/add_product_images", method = RequestMethod.POST)
    public ResponseEntity addProductImages(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam("file3") MultipartFile file3,
            @RequestParam("productId") String productId){

        String message = "";

        try {

//            filesStorageServiceImpl.save(file1);
//            String url1 = MvcUriComponentsBuilder
//                    .fromMethodName(ProductController.class,
//                            "getFile", file1.getOriginalFilename()).build().toString();
//
//            filesStorageServiceImpl.save(file2);
//            String url2 = MvcUriComponentsBuilder
//                    .fromMethodName(ProductController.class,
//                            "getFile", file2.getOriginalFilename()).build().toString();
//
//            filesStorageServiceImpl.save(file3);
//            String url3 = MvcUriComponentsBuilder
//                    .fromMethodName(ProductController.class,
//                            "getFile", file3.getOriginalFilename()).build().toString();

            String url1 = uploadSingleFile(file1);
            String url2 = uploadSingleFile(file2);
            String url3 = uploadSingleFile(file3);


            List<String> imageIdList = new ArrayList<>(Arrays.asList(url1, url2, url3));
            List<ProductImages> productImagesList = new ArrayList<>();


            for (String imageId : imageIdList) {

                ProductImages productImages = new ProductImages(imageId, productId);
                ProductImages savedImages = productsServiceImpl.addProductImages(productImages);
                productImagesList.add(savedImages);
            }

            message = "Uploaded the file successfully: " + file1.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);

        }catch (Exception e){

            message = "Could not upload one of the images!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UploadResponseMessage(message));
        }


    }

    public String uploadSingleFile(MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        return fileDownloadUri;
    }


    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileDB fileDB = fileStorageServiceImpl.getFile(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//                .body(fileDB.getData());
//    }

    @RequestMapping(value = "/api/v1/products/add_product_sizes", method = RequestMethod.POST)
    public ResponseEntity addProductSize(@RequestBody ProductSizes productSizes){

        ProductSizes respProductDetails = productsServiceImpl.addProductSizes(productSizes);
        if (respProductDetails != null){
            return new ResponseEntity(respProductDetails, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Product sizes could not be added. Please try again."));
        }

    }

    @RequestMapping(value = "/api/v1/products/view_sizes/{productId}", method = RequestMethod.GET)
    public ResponseEntity getProductSizes(@PathVariable("productId") String productId){

        List<ProductSizes> productSizes = productsServiceImpl.getProductSizes(productId);
        if (productSizes != null){
            return new ResponseEntity(productSizes, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Product sizes could not be found. Please try again."));
        }

    }

//    @RequestMapping(value = "/api/v1/products/add_products", method = RequestMethod.POST)
//    public ResponseEntity addProducts(@RequestBody UploadProduct uploadProduct){
//
//        String message = "";
//
//        try {
//
//            List<ProductImages> productImagesList = new ArrayList<>();
//
//            //Upload Files
//            List<FileImages> fileImagesList = uploadProduct.getFileList();
//            for (int i = 0; i < fileImagesList.size(); i++){
//
//                MultipartFile file = fileImagesList.get(i).getImage1();
//                FileDB savedFile = fileStorageServiceImpl.store(file);
//                String imageId = savedFile.getId();
//                ProductImages productImages = new ProductImages(imageId, null);
//                productImagesList.add(productImages);
//
//            }
//
//            ProductDetails productDetails = new ProductDetails(
//                    uploadProduct.getProductId(),
//                    uploadProduct.getProductName(),
//                    uploadProduct.getProductDescription(),
//                    uploadProduct.getProductIngredients(),
//                    uploadProduct.getProductSizes(),
//                    productImagesList);
//
//            ProductDetails respProductDetails = productsServiceImpl.addProductDetails(productDetails);
//            if (respProductDetails != null){
//                return new ResponseEntity(respProductDetails, HttpStatus.OK);
//            }else {
//                return ResponseEntity.badRequest().body(new DynamicRes("Product could not be added. Please try again."));
//            }
//
//
//        }catch (Exception e){
//
//            message = "Could not upload one of the images!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UploadResponseMessage(message));
//        }
//
//    }

    @RequestMapping(value = "/api/v1/products/get_products", method = RequestMethod.GET)
    public ResponseEntity getProducts(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "9") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "DESC") String sortDirection){

        DynamicFullRes dynamicFullRes = productsServiceImpl.getPaginatedProducts(pageNo, pageSize, sortField, sortDirection);
        if (dynamicFullRes != null){
            return new ResponseEntity(dynamicFullRes, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
        }

    }

    @RequestMapping(value = "/api/v1/user/products/add-to-cart", method = RequestMethod.POST)
    public ResponseEntity addProductCart(@RequestBody AddToCart addToCart){

        UserDetails userDetails = userDetailsServiceImpl.getLoggedInUser();
        if (userDetails != null){

            String userId = userDetails.getUserId();

            ResponseData response = productsServiceImpl.addToCart(addToCart, userId);
            int statusCode = response.getStatusCode();
            String message = response.getMessage();

            if (statusCode == 200){
                return new ResponseEntity(new DynamicRes(message), HttpStatus.OK);
            }else if (statusCode == 400){
                return ResponseEntity.badRequest().body(new DynamicRes(message));
            }else {
                return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
            }

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DynamicRes("User is not logged in."));
        }



    }

    @RequestMapping(value = "/api/v1/user/products/view-my-cart", method = RequestMethod.GET)
    public ResponseEntity getMyCartDetails(){

        UserDetails userDetails = userDetailsServiceImpl.getLoggedInUser();
        if (userDetails != null) {

            String userId = userDetails.getUserId();
            DynamicAnyRes dynamicAnyRes = cartItemsServiceImpl.getMyCartItems(userId);
            return new ResponseEntity(dynamicAnyRes, HttpStatus.OK);

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DynamicRes("User is not logged in."));
        }


    }


}
