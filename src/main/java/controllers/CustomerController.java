package controllers;



import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;







import services.CustomerService;
import services.InvoiceService;
import domain.Customer;
import domain.Invoice;


@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController 
{
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	InvoiceService invoiceService;
	

	@RequestMapping("/register")
	public ModelAndView register() {

	ModelAndView result;

	Customer customer = customerService.create();
	
	result = new ModelAndView("register/registerCustomer");
	result.addObject("customer", customer);

return result;
}

@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
public ModelAndView save(@Valid Customer customer, BindingResult binding) {

	ModelAndView result = new ModelAndView();

	if (binding.hasErrors()) {
		result = createEditModelAndView(customer);
		} else {
			customerService.save(customer);
			result = new ModelAndView("redirect:/security/login.do");			
		}		
return result;
}

@RequestMapping("/registerFailure")
public ModelAndView failure() {
	ModelAndView result;

	result = new ModelAndView("redirect:register.do?showError=true");

return result;
}

protected ModelAndView createEditModelAndView(Customer customer) 
{
	ModelAndView result;
	result = createEditModelAndView(customer, null);
return result;
}

protected ModelAndView createEditModelAndView(Customer customer, String message) 
{
	ModelAndView result;

	result = new ModelAndView("register/registerCustomer");
	result.addObject("customer", customer);
	result.addObject("message", message);
return result;
}


@RequestMapping("/seeInvoices")
public ModelAndView seeInvoices()
{
	ModelAndView result;
	
	Collection<Invoice> invoices=customerService.getAllInvoices();
	
	result=new ModelAndView("customer/seeInvoices");
	result.addObject("invoices", invoices);
	return result;
	
}

@RequestMapping("/invoiceDetails")
public ModelAndView invoiceDetails(@RequestParam int id)
{
	ModelAndView result;
	
	Invoice invoice=invoiceService.findOne(id);
	
	result=new ModelAndView("customer/invoiceDetails");
	result.addObject("invoice", invoice);
	return result;
	
}



}