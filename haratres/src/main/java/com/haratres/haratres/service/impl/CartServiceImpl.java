package com.haratres.haratres.service.impl;

import com.haratres.haratres.dto.CartDto;
import com.haratres.haratres.dto.CartEntryDto;
import com.haratres.haratres.model.Cart;
import com.haratres.haratres.model.CartEntry;
import com.haratres.haratres.model.SizeVariantProduct;
import com.haratres.haratres.model.User;
import com.haratres.haratres.repository.CartEntryRepository;
import com.haratres.haratres.repository.CartRepository;
import com.haratres.haratres.repository.SizeVariantProductRepository;
import com.haratres.haratres.repository.UserRepository;
import com.haratres.haratres.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final UserRepository userRepository;
    private final SizeVariantProductRepository sizeVariantProductRepository;

    public CartServiceImpl(CartRepository cartRepository, CartEntryRepository cartEntryRepository, UserRepository userRepository, SizeVariantProductRepository sizeVariantProductRepository) {
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.userRepository = userRepository;
        this.sizeVariantProductRepository = sizeVariantProductRepository;
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    private Cart createNewCart(Long userId) {
        Cart cart = new Cart();
        Optional<User> userOpt = userRepository.findById(Math.toIntExact(userId));
        userOpt.ifPresent(cart::setUser);
        cartRepository.save(cart);
        return cart;
    }

    private CartDto convertToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getId());
        cartDto.setTotalPrice(cart.getTotalPrice());

        cartDto.setCartEntries(cart.getCartEntries().stream()
                .map(this::convertToCartEntryDto)
                .collect(Collectors.toList()));

        return cartDto;
    }

    private CartEntryDto convertToCartEntryDto(CartEntry cartEntry) {
        CartEntryDto entryDto = new CartEntryDto();
        entryDto.setId(cartEntry.getId());
        entryDto.setSizeVariantProductId(cartEntry.getSizeVariantProduct().getId());
        entryDto.setQuantity(cartEntry.getQuantity());
        entryDto.setTotalPrice(cartEntry.getTotalPrice());

        return entryDto;
    }

    public double calculateTotalPrice(List<CartEntry> cartEntries) {
        return  cartEntries.stream()
                .mapToDouble(CartEntry::getTotalPrice)
                .sum();
    }

    private Cart getCurrentCartEntity(Long userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        return cartOpt.orElseGet(() -> createNewCart(userId));
    }

    @Override
    public CartDto getCurrentCart(Long userId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("Kullanıcı sadece kendi sepetine erişebilir.");
        }
        Cart cart = getCurrentCartEntity(userId);
        return convertToCartDto(cart);
    }

    @Override
    public CartDto addProductToCart(Long userId, Long sizeVariantProduct, Integer quantity) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("Kullanıcı sadece kendi sepetine ürün ekleyebilir.");
        }
        Cart cart = getCurrentCartEntity(userId);

        CartEntry cartEntry = new CartEntry();
        Optional<SizeVariantProduct> sizeVariantProductOpt = sizeVariantProductRepository.findById(sizeVariantProduct);
        cartEntry.setCart(cart);
        cartEntry.setSizeVariantProduct(sizeVariantProductOpt.get());
        cartEntry.setQuantity(quantity);
        cartEntry.calculateTotalPrice();
        cartEntryRepository.save(cartEntry);

        if(CollectionUtils.isEmpty(cart.getCartEntries())){
            List<CartEntry> cartEntryList = new ArrayList<>();
            cartEntryList.add(cartEntry);
            cart.setCartEntries(cartEntryList);
        } else {
            cart.getCartEntries().add(cartEntry);
        }

        cart.setTotalPrice(calculateTotalPrice(cart.getCartEntries()));
        cartRepository.save(cart);

        return convertToCartDto(cart);
    }


    @Override
    public CartDto updateCartEntry(Long cartEntryId, Integer quantity) {
        Optional<CartEntry> cartEntryOpt = cartEntryRepository.findById(cartEntryId);
        if (cartEntryOpt.isPresent()) {
            CartEntry cartEntry = cartEntryOpt.get();
            Cart cart = cartEntry.getCart();
            Long currentUserId = getCurrentUserId();

            if (!currentUserId.equals(cart.getUser().getId())) {
                throw new AccessDeniedException("Kullanıcı sadece kendi sepetindeki ürünleri güncelleyebilir.");
            }

            cartEntry.setQuantity(quantity);
            cartEntry.calculateTotalPrice();
            cartEntryRepository.save(cartEntry);

            cart.setTotalPrice(calculateTotalPrice(cart.getCartEntries()));
            cartRepository.save(cart);

            return convertToCartDto(cart);
        }
        else {
            throw new IllegalArgumentException("Geçersiz CartEntry ID: " + cartEntryId);
        }
    }

    @Override
    public CartDto cleanCart(Long userId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(userId)) {
            throw new AccessDeniedException("Kullanıcı sadece kendi sepetini temizleyebilir.");
        }
        Cart cart = getCurrentCartEntity(userId);
        cart.getCartEntries().clear();
        cart.setTotalPrice(0d);
        cartRepository.save(cart);

        return convertToCartDto(cart);
    }
}

