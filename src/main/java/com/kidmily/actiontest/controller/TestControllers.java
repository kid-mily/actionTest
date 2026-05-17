package com.kidmily.actiontest.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestControllers {

    // ==========================================
    // 1. User Domain
    // ==========================================
    public record UserDto(Long id, String username, String email) {}

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return List.of(new UserDto(1L, "user1", "user1@example.com"));
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody UserDto dto) {
        return dto;
    }

    // ==========================================
    // 2. Post Domain
    // ==========================================
    public record PostDto(Long postId, String title, String content) {}

    @GetMapping("/posts")
    public List<PostDto> getPosts() {
        return List.of(new PostDto(100L, "First Post", "Hello World!"));
    }

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody PostDto dto) {
        return dto;
    }

    // ==========================================
    // 3. Product Domain
    // ==========================================
    public record ProductDto(Long productId, String name, int price) {}

    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        return List.of(new ProductDto(500L, "Mechanical Keyboard", 150000));
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        return dto;
    }

    // ==========================================
    // 4. Order Domain
    // ==========================================
    public record OrderDto(Long orderId, Long userId, Long productId, int quantity) {}

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return List.of(new OrderDto(999L, 1L, 500L, 2));
    }

    @PostMapping("/orders")
    public OrderDto createOrder(@RequestBody OrderDto dto) {
        return dto;
    }

    // ==========================================
    // 5. Comment Domain
    // ==========================================
    public record CommentDto(Long commentId, Long postId, String text) {}

    @GetMapping("/comments")
    public List<CommentDto> getComments() {
        return List.of(new CommentDto(77L, 100L, "Nice post!"));
    }

    @PostMapping("/comments")
    public CommentDto createComment(@RequestBody CommentDto dto) {
        return dto;
    }
}