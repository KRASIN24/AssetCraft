package com.spring.asset_craft.service.impl;

import com.spring.asset_craft.exception.ValidationException;
import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.ProductUser.ProductUserStatus;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.repository.ProductImageRepository;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.repository.ReviewRepository;
import com.spring.asset_craft.search.ProductSpecification;
import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.*;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private ProductUserRepository productUserRepository;
    private ReviewRepository reviewRepository;
    private ProductImageRepository productImageRepository;
    private UserService userService;
    private AppDAO appDAO;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductUserRepository productUserRepository, ReviewRepository reviewRepository,
                              UserService userService, ProductImageRepository productImageRepository, AppDAO appDAO) {
        this.productRepository = productRepository;
        this.productUserRepository = productUserRepository;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.productImageRepository = productImageRepository;
        this.appDAO = appDAO;
    }

    @Override
    public String getOwnerUsername(Long productId) {
        return productUserRepository.getOwnerUsername(productId)
                .orElse(null);
    }

    @Override
    public Long getOwnerId(Long productId) {
        return productUserRepository.getOwnerId(productId);
    }

    @Override
    public ProductDTO convertToSmallProductDTO(Product product) {

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                getProductRating(product.getId()),
                product.getCategory(),
                getImages(product.getId()),
                getOwnerUsername(product.getId()),
                false,
                false,
                false
        );
    }

    @Override
    public List<ProductDTO> populateSmallProductDTOS(List<Product> products) {
        return products.stream()
                .map(this::convertToSmallProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findProductById(id)
                .orElse(null);
    }

    @Override
    public ProductDTO getBigProductDTO(Long productId) {

        Product product = getProductById(productId);

        return new ProductDTO(
                productId,
                product.getName(),
                product.getPrice(),
                getProductRating(productId),
                getOwnerUsername(productId),
                product.getDescription(),
                product.getCategory(),
                getReviewsDTO(productId),
                product.getProductImages()
        );
    }

    @Override
    public List<ProductDTO> getAllSmallProductsDTO(){
        return populateSmallProductDTOS(productRepository.findAll());
    }

    @Override
    public List<ReviewDTO> getReviewsDTO(Long productId){
        return productRepository.findProductReviews(productId);
    }

    @Override
    public List<ProductDTO>  searchProducts(String name, Float minPrice, Float maxPrice, Double rating, List<String> categories) {
        Specification<Product> spec = Specification.where(null);  // Start with an empty specification

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));  // Add name filter
        }
        if (minPrice != null || maxPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));  // Add price range filter
        }
        if (rating != null) {
            spec = spec.and(ProductSpecification.hasRating(rating));  // Add name filter
        }
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategory(categories));  // Add category filter
        }

        return populateSmallProductDTOS(productRepository.findAll(spec));
    }

    @Override
    public float biggestPrice(List<ProductDTO> productDTOs) {

        return productDTOs.stream()
                .map(ProductDTO::getPrice)
                .max(Float::compare)
                .orElse(0.0f);
    }

    @Override
    public float smallestPrice(List<ProductDTO> ProductDTOs) {
        return ProductDTOs.stream()
                .map(ProductDTO::getPrice)
                .min(Float::compare)
                .orElse(0.0f);
    }


    @Override
    public List<ProductDTO> getProductsWithStatus(String username, ProductUserStatus status) {
        List<Product> products = productUserRepository.findProductsWithStatusByUser(username, status);
        return populateSmallProductDTOS(products);
    }

    @Override
    public void addToCart(Long productId, Long userId) {

        if(!hasStatus(productId, userId, CART) && !hasStatus(productId, userId, OWNER) && !hasStatus(productId, userId, BUYER))
            productUserRepository.save(new ProductUser(getProductById(productId), userService.getUserById(userId), CART));

    }

    public boolean hasStatus(Long productId, Long userId, ProductUserStatus status) {
        return switch (status) {
            case CART -> productUserRepository.hasStatus(productId, userId, CART);
            case BUYER -> productUserRepository.hasStatus(productId, userId, BUYER);
            case OWNER -> getOwnerId(productId).equals(userId);
            default -> false;
        };
    }


    @Override
    public void deleteFromCart(Long productId, String username) {
        productUserRepository.findProductInCartByProductId(productId, username)
                .ifPresent(productUser -> productUserRepository.delete(productUser));
    }

    @Override
    public void addReview(Long productId, String review, Float rating, String username) {

        reviewRepository.save(new Review(review, rating, getProductById(productId), userService.getUserByUsername(username)));
    }

    @Override
    public Double getProductRating(Long productId) {
        return reviewRepository.findProductRatingById(productId);
    }

    @Override
    public void addProduct(List<String> paths, FormProductDTO productForm, Principal principal) {
        Product product = new Product(
                productForm.getName(),
                productForm.getPrice().floatValue(),
                productForm.getCategory(),
                productForm.getDescription()
        );
        Product savedProduct = productRepository.save(product);

        for (String path : paths) {
            ProductImage productImage = new ProductImage(
                    path,
                    savedProduct
            );
            System.out.println(path);
            productImageRepository.save(productImage);
        }

        ProductUser productUser = new ProductUser(
                savedProduct,
                userService.getUserByUsername(principal.getName()),
                OWNER
        );
        productUserRepository.save(productUser);
    }

    @Override
    public void userBought(List<Long> productsIds){
        productUserRepository.updateProductStatus(productsIds);
    }

    @Override
    public List<ProductImage> getImages(Long productId) {
        return productImageRepository.findImagesByProductId(productId);
    }

    @Override
    public void updateProduct(Long productId, FormProductDTO productForm, List<String> paths) {
        Product product = getProductById(productId);
        for (String path : paths) {
            ProductImage productImage = new ProductImage(
                    path,
                    product
            );
            System.out.println(path);
            productImageRepository.save(productImage);
        }
        productRepository.updateProduct(productForm.getId(),productForm.getName(),productForm.getCategory(),productForm.getDescription(),productForm.getPrice());
    }

    @Override
    public FormProductDTO getFormProductById(Long id) {
        Product product = getProductById(id);
        Double price = (double) product.getPrice();
        FormProductDTO formProductDTO = new FormProductDTO();
        formProductDTO.setId(id);
        formProductDTO.setName(product.getName());
        formProductDTO.setCategory(product.getCategory());
        formProductDTO.setDescription(product.getDescription());
        formProductDTO.setPrice(price);
        formProductDTO.setImages(getImages(id));
        return formProductDTO;
    }


    @Override
    public void deleteImage(Long imageId) {
        ProductImage productImage = productImageRepository.findImagesById(imageId);
        //System.out.println(productImage.toString());
        String upload = "./src/main/resources/static/";
        String pathS = upload + productImage.getPath();
        Path path = Paths.get(pathS);
        System.out.println(path);
        try {
            Files.deleteIfExists(path);
            productImageRepository.delete(productImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handleProduct(Long productId, FormProductDTO productForm, Principal principal) throws ValidationException{

        List<MultipartFile> files = productForm.getFiles();
        List<String> dbPaths = getDbPathsAndSaveFiesToDirectory(files);

        try {
            if (productId == null)
                addProduct(dbPaths,productForm,principal);
            else
                updateProduct(productId, productForm, dbPaths);
        } catch (Exception e) {
            //bindingResult.rejectValue("files", "error.files", "Failed to upload files.");
            throw new ValidationException("Failed to upload files.");
        }

    }

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    @Override
    public void validateFiles(List<MultipartFile> files) throws ValidationException {

//        FIXME: Even if files are empty "Only JPEG or PNG images are allowed." is thrown
        if (files.isEmpty()) {
            throw new ValidationException("At least one file is required.");
        }
        else {
            for (MultipartFile file : files) {
                if (file.getSize() > MAX_FILE_SIZE) {
                    throw new ValidationException("File size exceeds the 5MB limit.");
                }
                else if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
                    throw new ValidationException("Only JPEG or PNG images are allowed.");
                }
            }
        }

    }

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public List<String> getDbPathsAndSaveFiesToDirectory(List<MultipartFile> files) {
        List<String> dbPaths = new ArrayList<>();

        for (MultipartFile file : files) {
            try {

                String fileName = file.getOriginalFilename();

                Path uploadPath = Paths.get(uploadDir + "products/");
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


                dbPaths.add("/images/products/" + fileName);
            }catch (IOException e){
                System.out.println("ERRRRRORRRRRR");
            }
        }
        return dbPaths;
    }
}
