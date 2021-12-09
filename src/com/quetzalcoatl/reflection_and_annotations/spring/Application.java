package com.quetzalcoatl.reflection_and_annotations.spring;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IllegalAccessException {
        ApplicationContext context = new ApplicationContext(AppConfig.class);
        ProductService service = context.getBean(ProductService.class);

        List<Product> items = new ArrayList<>();
        items.add(new Product("Tea", 14));
        items.add(new Product("Coffee", 10));
        items.add(new Product("Juice", 12));

        List<Product> withFinalPrice = service.getFinalPrice(items);
        for (Product product : withFinalPrice) {
            System.out.println(product.getName() + " @"+ product.getDiscount()+ " % discount :"+ product.getPrice()+"$." );
        }
    }
}
