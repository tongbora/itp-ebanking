package com.tongbora.pipelineservice.controller.product;

import com.tongbora.pipelineservice.dto.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public List<ProductResponse> getProducts() {
        return List.of(
                new ProductResponse(1L, "iPhone 16 Pro", "IPHONE", "A17 Pro chip. Camera Control.", BigDecimal.valueOf(999), 4.8, 2400, "https://img.freepik.com/premium-vector/apple-iphone-16-white-isolated-white-background_570051-851.jpg?semt=ais_user_personalization&w=740&q=80", true),
                new ProductResponse(2L, "iPhone 16", "IPHONE", "Powerful and colorful.", BigDecimal.valueOf(799), 4.6, 1800, "https://img.freepik.com/premium-vector/apple-iphone-16-white-isolated-white-background_570051-851.jpg?semt=ais_user_personalization&w=740&q=80", true),
                new ProductResponse(3L, "MacBook Pro M3", "MAC", "M3 chip. Supercharged.", BigDecimal.valueOf(1999), 4.9, 3200, "https://m.media-amazon.com/images/I/618d5bS2lUL.jpg", false),
                new ProductResponse(4L, "MacBook Air M3", "MAC", "Light. Fast. Portable.", BigDecimal.valueOf(1299), 4.7, 2100, "https://m.media-amazon.com/images/I/618d5bS2lUL.jpg", false),
                new ProductResponse(5L, "iPad Pro M4", "IPAD", "Ultra Retina XDR display.", BigDecimal.valueOf(1099), 4.8, 1500, "https://powermaccenter.com/cdn/shop/files/iPad_Pro_11_M4_WiFi_Silver_PDP_Image_Position_1b__en-US.jpg?v=1716466784&width=1445", true),
                new ProductResponse(6L, "iPad Air", "IPAD", "Thin and powerful.", BigDecimal.valueOf(599), 4.5, 1200, "https://powermaccenter.com/cdn/shop/files/iPad_Pro_11_M4_WiFi_Silver_PDP_Image_Position_1b__en-US.jpg?v=1716466784&width=1445", false),
                new ProductResponse(7L, "Apple Watch Ultra 2", "WATCH", "Adventure ready.", BigDecimal.valueOf(799), 4.9, 900, "https://cdsassets.apple.com/live/7WUAS350/images/tech-specs/111832-watch-ultra-2.png", true),
                new ProductResponse(8L, "Apple Watch Series 9", "WATCH", "Smarter. Brighter.", BigDecimal.valueOf(399), 4.6, 1700, "https://cdsassets.apple.com/live/7WUAS350/images/tech-specs/111832-watch-ultra-2.png", false),
                new ProductResponse(9L, "AirPods Pro 2", "AUDIO", "Active Noise Cancellation.", BigDecimal.valueOf(249), 4.8, 5000, "https://m.media-amazon.com/images/I/61SUj2aKoEL.jpg", false),
                new ProductResponse(10L, "AirPods 3", "AUDIO", "Spatial Audio.", BigDecimal.valueOf(179), 4.5, 3500, "https://m.media-amazon.com/images/I/61SUj2aKoEL.jpg", false),

                new ProductResponse(11L, "HomePod Mini", "AUDIO", "Smart speaker.", BigDecimal.valueOf(99), 4.4, 800, "https://i0.wp.com/ufostorekh.com/wp-content/uploads/2023/02/37bd3082-7c2e-4c61-a605-49458164feb9_1000x1000.jpg", false),
                new ProductResponse(12L, "Apple TV 4K", "TV", "Cinematic experience.", BigDecimal.valueOf(129), 4.6, 600, "https://www.apple.com/newsroom/images/product/tv/standard/Apple-TV-4K-hero-221018_big.jpg.large.jpg", false),
                new ProductResponse(13L, "Magic Keyboard", "ACCESSORY", "Comfort typing.", BigDecimal.valueOf(179), 4.3, 400, "https://sm.pcmag.com/pcmag_me/review/a/apple-magi/apple-magic-keyboard_umed.jpg", false),
                new ProductResponse(14L, "Magic Mouse", "ACCESSORY", "Smooth control.", BigDecimal.valueOf(99), 4.2, 500, "https://upload.wikimedia.org/wikipedia/commons/c/c8/Magic_Mouse.jpg", false),
                new ProductResponse(15L, "Studio Display", "DISPLAY", "27-inch Retina 5K.", BigDecimal.valueOf(1599), 4.7, 300, "https://external-preview.redd.it/apple-studio-display-2-code-hints-at-120hz-promotion-hdr-v0-wivcj18WAp7CkimlFspuvwexFxxORH-reKpqA6l4mXA.jpeg?auto=webp&s=8b6900dda9faf7527cb26f208504d20ff057a0f6", false),
                new ProductResponse(16L, "Mac Studio", "MAC", "Extreme performance.", BigDecimal.valueOf(3999), 4.9, 200, "https://upload.wikimedia.org/wikipedia/commons/3/32/Mac_Studio_%282022%29_front.jpg", false),
                new ProductResponse(17L, "Mac Mini M2", "MAC", "Small but powerful.", BigDecimal.valueOf(599), 4.5, 900, "https://s3.ap-southeast-1.amazonaws.com/uploads-store/uploads/all/uGT10fK3l88PVn5apaSB0J8BBJ5vRBuEnqRbibm1.png", false),
                new ProductResponse(18L, "Apple Pencil Pro", "ACCESSORY", "Next-gen stylus.", BigDecimal.valueOf(129), 4.8, 1100, "https://arystorephone.com/wp-content/uploads/2024/05/Apple-Pencil-Pro-1.jpg", true),
                new ProductResponse(19L, "Vision Pro", "XR", "Spatial computing.", BigDecimal.valueOf(3499), 4.6, 700, "https://techcrunch.com/wp-content/uploads/2023/06/wwdc-2023-vision-pro-price.jpg", true),
                new ProductResponse(20L, "AirTag", "ACCESSORY", "Track your stuff.", BigDecimal.valueOf(29), 4.7, 10000, "https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/airtag-unselect-202601_FMT_WHH?wid=2000&hei=2000&fmt=jpeg&qlt=90&.v=N3ZsUUU4YjZJQUNibU40dGJ4Z3QyeUdEQy9YN2FWbjUvbFhUcnpJVG5ocmg0MlEwS3JJOUc0eGsrZHphUUpjVmJGcXNRQnFCV0w3WVRjTExvdm1ic1pjV1JkckRLckdkVEVtZGh0Qmt6WDhVRk5BYjU4dkMrYjBKSjFqVXFOVGg", false)
        );
    }
}
