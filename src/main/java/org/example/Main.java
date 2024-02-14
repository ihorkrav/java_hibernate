package org.example;

import org.example.models.Category;
import org.example.models.Product;
import org.example.models.ProductPhoto;
import org.hibernate.Session;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        getListCategories();
       // addProductPhoto();
        getListCategories();
    }
    private static void addCategory(){
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            Category category = new Category();
            System.out.println("Вказіть назву категорії");
            String temp = scanner.nextLine();
            category.setName(temp);
            System.out.println("Вкажіть фото");
            temp = scanner.nextLine();
            category.setImage(temp);
            category.setCateCreated(calendar.getTime());
            context.save(category);
            context.getTransaction().commit();
        }
    }

    private static void updateCategory(){
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter category id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Category category1 = context.get(Category.class, id);
            if (category1 != null) {
                System.out.println("Вказіть назву категорії");
                String temp = scanner.nextLine();
                category1.setName(temp);
                System.out.println("Вкажіть фото");
                temp = scanner.nextLine();
                category1.setImage(temp);
                category1.setCateCreated(calendar.getTime());
                context.update(category1);
            }
            context.getTransaction().commit();
        }
    }

    private static void deleteCategory(){
        Scanner scanner = new Scanner(System.in);
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter category id: ");
            int id = scanner.nextInt();
            Category category1 = context.get(Category.class, id);
            if (category1 != null) {
                context.remove(category1);
            }
            context.getTransaction().commit();
        }
    }


    private static void getListCategories(){
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()){
            context.beginTransaction();

            List<Category> list = context.createQuery("from Category", Category.class).getResultList();

            for(Category category: list){
                System.out.println("Category: " + category);
            }

            context.getTransaction().commit();

        }
    }

    private static void addProducts(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter category id: ");
        int cat_id = scanner.nextInt();
        scanner.nextLine();
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            Category category1 = context.get(Category.class, cat_id);
            if (category1 != null) {
                Product product = new Product();
                System.out.println("Enter product name: ");
                String name = scanner.nextLine();
                product.setName(name);

                System.out.println("Enter description: ");
                String description = scanner.nextLine();
                product.setDescription(description);

                System.out.println("Enter price: ");
                int price = scanner.nextInt();
                product.setPrice(price);
                product.setCategory(category1);

                category1.getProducts().add(product);
                context.save(product);
                context.update(category1);
            }
            context.getTransaction().commit();
        }

    }
    private static void deleteProduct(){
        Scanner scanner = new Scanner(System.in);
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter product id: ");
            int id = scanner.nextInt();
            Product product = context.get(Product.class, id);
            if (product != null) {
                Category category1 = context.get(Category.class, product.getCategory().getId());
                category1.getProducts().remove(product);
                context.remove(product);
                context.update(category1);
            }
            context.getTransaction().commit();
        }
    }

    private static void updateProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        var sf = org.example.util.HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            Product product = context.get(Product.class, id);

            if (product != null) {
                Category category1 = context.get(Category.class, product.getCategory().getId());

                int index = category1.getProducts().indexOf(product);
                    System.out.println("Enter product name: ");
                    String name = scanner.nextLine();
                    product.setName(name);

                    System.out.println("Enter description: ");
                    String description = scanner.nextLine();
                    product.setDescription(description);

                    System.out.println("Enter price: ");
                    int price = scanner.nextInt();
                    product.setPrice(price);
                    product.setCategory(category1);

                    for (var products: category1.getProducts()){
                        if(products.getId()== product.getId()){
                            category1.getProducts().set(index, product);
                            break;
                        }
                    }
                    context.update(product);
                    context.update(category1);
                }
            context.getTransaction().commit();
            }


        }

        private static void addProductPhoto(){
        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter product id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter image path: ");
            Path path = Paths.get(scanner.nextLine());
            String filename = String.valueOf(path.getFileName());


            Path to = Paths.get("E:\\STEP\\JAVA\\hibenate\\hibernator\\images");
            try  {
                Files.copy(path, to);
                var sf = org.example.util.HibernateUtil.getSessionFactory();
                try (Session context = sf.openSession()) {
                    context.beginTransaction();
                    Product product = context.get(Product.class, id);
                    if(product!=null){
                        ProductPhoto photo = new ProductPhoto();
                        photo.setName(filename);
                        photo.setProduct(product);
                        product.getPhotos().add(photo);

                        context.save(photo);
                        context.update(product);
                    }
                 context.getTransaction().commit();
                }
            }
            catch (Exception ex){
                System.out.println("Error "+ex.getMessage());
            }
        }

    }


