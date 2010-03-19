package com.mp.domain

class ProductImage {

    Long productId
    byte[] image

    static constraints = {
        image(maxSize: 6 * 1024 * 1024)
    }
}
