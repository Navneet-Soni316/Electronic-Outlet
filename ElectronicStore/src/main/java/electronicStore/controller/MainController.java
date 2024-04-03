package electronicStore.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import electronicStore.dao.ProductDao;
import electronicStore.model.Product;
@Controller
public class MainController {
	@Autowired
	private ProductDao productDao;

	@RequestMapping("/")
	public String home(Model m) {
		List<Product> allProducts = productDao.getProducts();
		m.addAttribute("products",allProducts);
		return "index";
	}

	//show add product page
	@RequestMapping("add-product")
	public String addProduct(Model m) {
		m.addAttribute("title","Add Product");
		return "add_product_form";
	}

	//add product handler
	@RequestMapping(value = "/handle-product", method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product,HttpServletRequest request) {
		System.out.println(product);
		this.productDao.createProduct(product);
		RedirectView redirectView= new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		return redirectView;
	}

	//delete handler
	@RequestMapping("/delete/{productId}")
	public RedirectView deleteProduct(@PathVariable("productId") int prodId,
		HttpServletRequest request
	) {
		this.productDao.deleteProduct(prodId);
		RedirectView redirectView= new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		return redirectView;
	}

	//update Handler
	@RequestMapping("/update/{productId}")
	public String updateForm(@PathVariable("productId") int prodId, Model m) {
		Product prod = this.productDao.getSingleProduct(prodId);
		m.addAttribute("product", prod);
		return "update_form";
	}

}
