package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class RegisterCustomerController extends AbstractController {
	// Services ---------------------------------------------------------------
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public RegisterCustomerController() {
		super();
	}

	// Display-----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result;
		Customer customer;

		customer = customerService.findByPrincipal();

		result = new ModelAndView("customer/display");

		result.addObject("customer", customer);
		result.addObject("rating", customer.getRating());

		return result;

	}

	// Create------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CustomerForm customerForm;

		customerForm = customerService.construct();
		result = createEditModelAndView(customerForm);

		return result;
	}

	// Edition---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int customerId) {
		ModelAndView result;
		Customer customer;
		CustomerForm customerForm;

		customer = customerService.findOneCustomerToEdit(customerId);
		customerForm = customerService.construct(customer);

		customerForm.setUsername(customer.getUserAccount().getUsername());
		customerForm.setPassword(customer.getUserAccount().getPassword());
		customerForm.setPassword2(customer.getUserAccount().getPassword());
		customerForm.setTerms(true);

		result = createEditModelAndView(customerForm);

		return result;
	}

	// Save-----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CustomerForm customerForm,
			BindingResult bindingResult) {
		ModelAndView result;
		Customer customer;

		if (bindingResult.hasErrors() ||customerForm.getUsername().length() <= 3) {
			if (customerForm.getUsername().length() <= 3) {
				result = createEditModelAndView(customerForm,
						"register.commit.penemuychico");
			}
			result = createEditModelAndView(customerForm);

		} else {
			try {

				customer = customerService.reconstruct(customerForm);

				customerService.save(customer);

				result = new ModelAndView("redirect:../welcome/index.do");

			} catch (Throwable oops) {
				if (oops instanceof DataIntegrityViolationException) {

					result = createEditModelAndView(customerForm,
							"customer.duplicated.username");
				}
				if (customerForm.getUsername().length() <= 3) {
					result = createEditModelAndView(customerForm,
							"register.commit.penemuychico");
				} else {
					result = createEditModelAndView(customerForm,
							"customer.commit.error");
				}
			}
		}

		return result;
	}

	// Ancillary methods ----------------------------------------------

	protected ModelAndView createEditModelAndView(CustomerForm customerForm) {
		ModelAndView result;

		result = createEditModelAndView(customerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(CustomerForm customerForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("customer/edit");

		result.addObject("customerForm", customerForm);
		result.addObject("message", message);

		return result;
	}
}
