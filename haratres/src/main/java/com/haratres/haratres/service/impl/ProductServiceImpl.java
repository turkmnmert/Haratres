package com.haratres.haratres.service.impl;

import com.haratres.haratres.dto.InboundProductRequestDto;
import com.haratres.haratres.dto.ProductDto;
import com.haratres.haratres.dto.ProductResponseDto;
import com.haratres.haratres.dto.SizeVariantProductDto;
import com.haratres.haratres.model.ColorVariantProduct;
import com.haratres.haratres.model.SizeVariantProduct;
import com.haratres.haratres.repository.ColorVariantProductRepository;
import com.haratres.haratres.repository.ProductRepository;
import com.haratres.haratres.repository.SizeVariantProductRepository;
import com.haratres.haratres.service.ProductService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ColorVariantProductRepository colorVariantProductRepository;
    private final SizeVariantProductRepository sizeVariantProductRepository;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ColorVariantProductRepository colorVariantProductRepository,
                              SizeVariantProductRepository sizeVariantProductRepository) {
        this.productRepository = productRepository;
        this.colorVariantProductRepository = colorVariantProductRepository;
        this.sizeVariantProductRepository = sizeVariantProductRepository;
    }

    @Override
    public void createProduct(InboundProductRequestDto productRequest) {
        productRequest.getProducts().forEach(productDto -> {
            ColorVariantProduct colorVariantProduct;

            Optional<ColorVariantProduct> existingColorVariant = colorVariantProductRepository.findByCode(productDto.getCode());

            if (existingColorVariant.isPresent()) {
                colorVariantProduct = existingColorVariant.get();

                if (productDto.getVariants() != null) {
                    for (SizeVariantProductDto sizeVariantProductDto : productDto.getVariants()) {
                        Optional<SizeVariantProduct> existingSizeVariant = sizeVariantProductRepository.findByCode(sizeVariantProductDto.getCode());

                        if (!existingSizeVariant.isPresent()) {
                            SizeVariantProduct sizeVariant = new SizeVariantProduct();
                            sizeVariant.setCode(sizeVariantProductDto.getCode());
                            sizeVariant.setStock(sizeVariantProductDto.getStock());
                            sizeVariant.setSize(sizeVariantProductDto.getSize());
                            sizeVariant.setPrice(sizeVariantProductDto.getPrice());
                            sizeVariant.setColorVariantProduct(colorVariantProduct);

                            sizeVariantProductRepository.save(sizeVariant);
                        }
                    }
                }
            }
            else {
                colorVariantProduct = new ColorVariantProduct();
                colorVariantProduct.setCode(productDto.getCode());
                colorVariantProduct.setName(productDto.getName());
                colorVariantProduct.setDescription(productDto.getDescription());
                colorVariantProduct.setColor(productDto.getColor());
                colorVariantProduct.setGender(productDto.getGender());

                colorVariantProductRepository.save(colorVariantProduct);
            }

            if (productDto.getVariants() != null) {
                for (SizeVariantProductDto sizeVariantProductDto : productDto.getVariants()) {
                    Optional<SizeVariantProduct> existingSizeVariant = sizeVariantProductRepository.findByCode(sizeVariantProductDto.getCode());

                    if (!existingSizeVariant.isPresent()) {
                        SizeVariantProduct sizeVariant = new SizeVariantProduct();
                        sizeVariant.setCode(sizeVariantProductDto.getCode());
                        sizeVariant.setStock(sizeVariantProductDto.getStock());
                        sizeVariant.setSize(sizeVariantProductDto.getSize());
                        sizeVariant.setPrice(sizeVariantProductDto.getPrice());
                        sizeVariant.setColorVariantProduct(colorVariantProduct);

                        sizeVariantProductRepository.save(sizeVariant);
                    }
                }
            }
        });
    }

    @Override
    public ProductResponseDto getProductByCode(String productCode) {
        ProductResponseDto response = new ProductResponseDto();
        try {
            Optional<SizeVariantProduct> sizeVariantProductOpt = sizeVariantProductRepository.findByCode(productCode);

            if (sizeVariantProductOpt.isPresent()) {
                SizeVariantProduct sizeVariantProduct = sizeVariantProductOpt.get();
                ColorVariantProduct colorVariantProduct = sizeVariantProduct.getColorVariantProduct();

                ProductDto productDto = new ProductDto();
                productDto.setPrice(sizeVariantProduct.getPrice());
                productDto.setSize(sizeVariantProduct.getSize());
                productDto.setStock(sizeVariantProduct.getStock());

                if (colorVariantProduct != null) {
                    productDto.setColor(colorVariantProduct.getColor());
                    productDto.setName(colorVariantProduct.getName());
                    productDto.setDescription(colorVariantProduct.getDescription());
                    productDto.setGender(colorVariantProduct.getGender());
                }
                response.setProduct(productDto);
            }
            else {
                response.setErrorMessage("Ürün bulunamadı");
            }
        }catch (Exception e){
            logger.error("Ürün bilgilerini elde etme sırasında hata oluştu", e);
            response.setErrorMessage("Ürün bilgilerini elde etme sırasında bir hata oluştu");
        }
        return response;
    }
}


