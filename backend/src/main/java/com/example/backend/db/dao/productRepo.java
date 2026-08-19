package com.example.backend.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.db.DB;
import com.example.backend.models.Message;
import com.example.backend.models.Product;

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
                product.setPrint_id(rs.getString("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                product.setImage(rs.getString("image"));

                products.add(product);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findProducts(String param) {
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
                product.setPrint_id(rs.getString("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                product.setImage(rs.getString("image"));

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
                product.setPrint_id(rs.getString("printer_id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setSubcategory(rs.getString("subcategory_id"));
                product.setPrice(rs.getDouble("unit_price"));
                product.setStock(rs.getInt("stock_quantity"));
                product.setLike_cnt(rs.getInt("like_count"));
                product.setDislike_cnt(rs.getInt("dislike_count"));
                product.setImage(rs.getString("image"));

                products.add(product);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
}
