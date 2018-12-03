package com.example.finance.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.finance.model.Fee;
import com.example.finance.repository.FeeRepository;

/**
 * 
 * 収益関連の画面コントローラです。
 * @author Akira Abe
 *
 */
@Controller
@RequestMapping("finance")
public class FinanceController {
	
	@Autowired
	FeeRepository repository;

	@ModelAttribute
	FinanceForm setUpForm() {
		return new FinanceForm();
	}
	
	@RequestMapping(method = RequestMethod.GET)
    String list(Model model) {

        List<Fee> fees = repository.findAll();

        if (fees != null) fees.forEach(System.out::println);
        model.addAttribute("fees", fees);
        return "finance/list";
    }

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String showInputView() {
		return "/finance/input";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	String create(@Validated FinanceForm form, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return "/finance/input";
		}

		// System.out.println(form);

		// TODO 本来は、計算種類毎に、委譲先のドメインオブジェクトを切り替える
		Map<String, Object> param = new HashMap<>();
		param.put("principal", Long.parseLong(form.getPrincipal()));
		param.put("term", form.getTerm());
		param.put("rate", Double.parseDouble(form.getRate()));
		
		Fee fee = new Fee(param, "2");
		
		repository.save(fee);

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("form", fee);
		modelMap.addAttribute("list", fee.getCashflowDetails());

		fee.getCashflowDetails().forEach(System.out::println);

		attributes.addFlashAttribute("model", modelMap);

		return "redirect:/finance/show";

	}

	@RequestMapping("/show")
	public String showResult(@ModelAttribute("model") ModelMap modelMap, Model model) {

		Fee form = (Fee) modelMap.get("form");
		// System.out.println("showResult# " + form);
		model.addAttribute("form", form);
		model.addAttribute("list", modelMap.get("list"));

		return "/finance/showresult";
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {

        model.addAttribute("form", repository.getOne(id));
        model.addAttribute("list", repository.getOne(id).getCashflowDetails());

		return "/finance/showresult";
    }
}
