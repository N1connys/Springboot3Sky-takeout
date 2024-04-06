package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> listShopCart(ShoppingCart shoppingCart);

    @Update("update sky_take_out.shopping_cart set number=#{number} where id=#{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    @Insert("insert into sky_take_out.shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "VALUES (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);
    @Delete("DELETE FROM sky_take_out.shopping_cart WHERE user_id=#{userId}")
    void cleanAllCarts(Long userId);
    @Delete("delete from sky_take_out.shopping_cart where id=#{id}")
    void delete(Long id);

    void insertBrach(List<ShoppingCart> list);
}
