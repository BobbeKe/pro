package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1")Integer num, HttpServletRequest request, HttpServletResponse response) {
        //首先判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            //如果用户登录
            //保存到服务端
            cartService.addCart(user.getId(),itemId,num);
            return "cartSuccess";
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = this.getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (TbItem item:cartList) {
            if (item.getId() == itemId.longValue()) {//==比较内存地址
                item.setNum(item.getNum()+num);
                //如果存在,数量相加
                //跳出循环
                flag = true;
                break;
            }
        }
        //如果不存在，根据商品id查询到商品信息。得到Tbitem对象
        if (!flag) {
            TbItem tbItem = itemService.getItemById(itemId);
            //设置商品数量
            tbItem.setNum(num);
            //取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                tbItem.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            cartList.add(tbItem);
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);
        //返回成功页面
        return "cartSuccess";

    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取出购物车列表
        List<TbItem> cartList = this.getCartListFromCookie(request);
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            //如果是登录状态
            //如果cookie中的购物车不为空，把cookie中的商品列表和服务端的购物车列表合并 -- 引用服务
            cartService.mergeCart(user.getId(),cartList);
            //删除cookie中的购物车
            CookieUtils.deleteCookie(request,response,"cart");
            //从服务端取购物车列表  ---引用服务
            cartList = cartService.getCartList(user.getId());
        }
        //未登录状态
        //把列表传递给页面
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 更新购物车商品数量
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num
            , HttpServletRequest request , HttpServletResponse response) {

        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return E3Result.ok();
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历商品列表找到对应的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId().longValue() == itemId) {
                //更新数量
                tbItem.setNum(num);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回成功
        return E3Result.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        //遍历列表找到要删除的商品
        List<TbItem> cartListFromCookie = this.getCartListFromCookie(request);
        for (TbItem tbItem:cartListFromCookie) {
            if (tbItem.getId().longValue() == itemId) {
                cartListFromCookie.remove(tbItem);
                //删除商品
                break;
            }
        }
        //把列表写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartListFromCookie),COOKIE_CART_EXPIRE,true);
        //返回
        return "redirect:/cart/cart.html";
    }

    //从cookie中取数据
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String cartjson = CookieUtils.getCookieValue(request, "cart", true);
        //判断是否为空
        if (StringUtils.isBlank(cartjson)) {
            return new ArrayList<>();
        }
        //把json转换成商品列表
        List<TbItem> list = JsonUtils.jsonToList(cartjson, TbItem.class);
        return list;
    }

}
