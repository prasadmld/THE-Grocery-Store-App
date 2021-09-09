package com.ecommerce.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dao.CartDao;
import com.ecommerce.dao.UserDao;
import com.ecommerce.modal.Cart;
import com.ecommerce.modal.User;

import org.apache.log4j.Logger;
import com.ecommerce.service.CartService;
@Service
@Transactional
@Component

public class CartServiceImpl implements CartService {
	
	private static Logger log = Logger.getLogger(CartServiceImpl.class);

	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public Cart addCartToUser(Cart cart, long idUser) {
		
		log.info("entering into AddCarttoUser file");
		User user = userDao.findById(idUser).orElse(null);
		user.addCartToUser(cart);
		return cartDao.save(cart);
	}

	@Override
	public void deleteCart(long id) {
		cartDao.deleteById(id);
		
		log.info("deleting cart items");
	}

	@Override
	public List<Cart> findCartsForUser(long idUser) {
		User user = userDao.findById(idUser).orElse(null);
		
		log.info("Finding the cart of a user");
		return user.getCarts();
	}

	@Override
	public Cart findCartById(long id) {
		return cartDao.findById(id).orElse(null);
	}

	@Override
	public void removeFromCart(long idCart, long idUser) {
		User user = userDao.findById(idUser).orElse(null);
		Cart cart = cartDao.findById(idCart).orElse(null);
		user.removeFromCart(cart);
		
		log.info(" Removing the items form the cart");
		cartDao.delete(cart);
	}

	@Override
	public Cart findByCartName(String name) {
		Optional<Cart> carts = cartDao.findByName(name);
		if (carts.isPresent()) {
			Cart cart = carts.get();
			return cart;
		}
		return null;
	}

}
