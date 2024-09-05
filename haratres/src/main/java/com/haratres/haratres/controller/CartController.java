package com.haratres.haratres.controller;

import com.haratres.haratres.dto.CartDto;
import com.haratres.haratres.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCurrentCart(@PathVariable Long userId) {
        try {
            CartDto cart = cartService.getCurrentCart(userId);
            return ResponseEntity.ok(cart);
        } catch (AccessDeniedException e) {
            logger.error("Kullanıcı sadece kendi sepetine erişebilir: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Bu sepete erişim yetkiniz yok.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            logger.error("Kullanıcı için sepet alınırken hata oluştu: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Sepet alınırken bir hata oluştu.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@RequestParam Long userId,
                                                    @RequestParam Long sizeVariantProductId,
                                                    @RequestParam Integer quantity) {
        try {
            CartDto cart = cartService.addProductToCart(userId, sizeVariantProductId, quantity);
            return ResponseEntity.ok(cart);
        } catch (AccessDeniedException e) {
            logger.error("Kullanıcı sadece kendi sepetine ürün ekleyebilir: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Bu sepete ürün ekleme yetkiniz yok.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            logger.error("Kullanıcı için sepete ürün eklenirken hata oluştu: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Sepete ürün eklenirken bir hata oluştu.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/update/{cartEntryId}")
    public ResponseEntity<CartDto> updateCartEntry(@PathVariable Long cartEntryId,
                                                   @RequestParam Integer quantity) {
        try {
            CartDto updatedCart = cartService.updateCartEntry(cartEntryId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (AccessDeniedException e) {
            logger.error("Yetkisiz işlem: " + cartEntryId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Bu sepetteki ürünü güncelleme yetkiniz yok.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            logger.error("Sepet girdisi güncellenirken hata oluştu: " + cartEntryId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Sepet girdisi güncellenirken bir hata oluştu.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<CartDto> cleanCart(@PathVariable Long userId) {
        try {
            CartDto cart = cartService.cleanCart(userId);
            return ResponseEntity.ok(cart);
        } catch (AccessDeniedException e) {
            logger.error("Kullanıcı sadece kendi sepetini temizleyebilir: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Bu sepeti temizleme yetkiniz yok.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            logger.error("Kullanıcı için sepet temizlenirken hata oluştu: " + userId, e);
            CartDto errorResponse = new CartDto();
            errorResponse.setErrorMessage("Sepet temizlenirken bir hata oluştu.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
