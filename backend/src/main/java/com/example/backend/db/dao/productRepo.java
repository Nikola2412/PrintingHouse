package com.example.backend.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.db.DB;
import com.example.backend.models.Message;
import com.example.backend.models.Product;
import com.example.backend.models.ProductDitails;

public class productRepo implements productInterface {

    @Override
    public Message getProductCnt() {
        Message msg = new Message("0");
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS cnt FROM products where active = true");
        ){
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                msg.setMsg(String.valueOf(rs.getInt("cnt")));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public List<Product> getTopProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products where active = true order by like_count desc limit 5");
        ){
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setPrint_id(rs.getInt("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                //product.setImage(rs.getString("image"));

                products.add(product);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findProductByID(Long id) {
        Product p = new Product();
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("Select * from products where id = ?");
        ){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                p.setId(rs.getInt("id"));
                p.setPrint_id(rs.getInt("printer_id"));
                p.setCode(rs.getString("code"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setSubcategory(rs.getString("subcategory_id"));
                p.setPrice(rs.getDouble("unit_price"));
                p.setStock(rs.getInt("stock_quantity"));
                p.setLike_cnt(rs.getInt("like_count"));
                p.setDislike_cnt(rs.getInt("dislike_count"));
                //p.setImage(rs.getString("image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public ProductDitails getPorductDitails(Long id) {
        ProductDitails item = new ProductDitails();
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select products.id, products.name, institutions.name, institutions.city, products.like_count, products.dislike_count  \n" + //
                                "from products  join institutions on(products.printer_id = institutions.id) where active = true and products.id = ?");

            PreparedStatement stmt2 = conn.prepareStatement("select * from product_images where product_id = ?");
        ){
            stmt.setLong(1, id);
            stmt2.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                item.setId(rs.getInt(1));
                item.setName(rs.getString(2));
                item.setPrint(rs.getString(3));
                item.setCity(rs.getString(4));
                item.setLike_cnt(rs.getInt(5));
                item.setDislike_cnt(rs.getInt(6));
            }

            rs = stmt2.executeQuery();
            ArrayList<Integer> arr = new ArrayList<>();
            while (rs.next()) {
                arr.add(rs.getInt(1));
            }

            item.setImages(arr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<Product> findProductsBySearchParam(String param) {
        ArrayList<Product> products = new ArrayList<>();
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE name LIKE ? OR description LIKE ? and active = true order by like_count desc");
        ){
            stmt.setString(1, "%" + param + "%");
            stmt.setString(2, "%" + param + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setPrint_id(rs.getInt("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                //product.setImage(rs.getString("image"));

                products.add(product);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findProductsByCategory(String category, String searchParam) {
        ArrayList<Product> products = new ArrayList<>();
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE subcategory_id in (\n" + //
                                "\tSelect id from subcategories where category_id = (select id from categories where name = ?)\n" + //
                                ") AND (name LIKE ? OR description LIKE ?) and active = true order by like_count desc");
        ){
            stmt.setString(1, category);
            stmt.setString(2, "%" + searchParam + "%");
            stmt.setString(3, "%" + searchParam + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setPrint_id(rs.getInt("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                //product.setImage(rs.getString("image"));

                products.add(product);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public String getMainImage(Long id) {
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select file_name from product_images where id = (\n" + //
                                "\tselect min(id) from product_images where product_id = ?\n" + //
                                ")");
        ){
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getString(1);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }

    @Override
    public String getImage(Long id) {
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select file_name from product_images where id = ?");
        ){
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getString(1);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }
    
}
