package com.example.finance.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 
 * 収益関連の画面コントローラです。
 * @author Akira Abe
 *
 */
@Controller
@RequestMapping("finance")
public class FinanceController {

	@ModelAttribute
	FinanceForm setUpForm() {
		return new FinanceForm();
	}
	
	@RequestMapping(method = RequestMethod.GET)
    String list(Model model) {

        List<Fee> fees = createSomeFees();

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
		Fee fee = createSomeFees().get(0);

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

        model.addAttribute("form", createSomeFees().get(0));
        model.addAttribute("list", createSomeFees().get(0).getCashflowDetails());

		return "/finance/showresult";
    }
	
	
	//TODO このメソッドは将来不要になります。
	private List<Fee> createSomeFees() {
		
		// テキトーなデータを生成し返します。
		Map<String, Object> param1 = new HashMap<>();
		param1.put("principal", 10_000L);
		param1.put("term", 12);
		param1.put("rate", 4.5);
		param1.put("amount", 1_000L);
		Fee fee1 =new Fee(param1, "1");

		Map<String, Object> param2 = new HashMap<>();
		param2.put("principal", 20_000L);
		param2.put("term", 24);
		param2.put("rate", 9.0);
		param2.put("amount", 2_000L);
		Fee fee2 =new Fee(param2, "2");
		
		List<Fee> fees = new ArrayList<>();
		fees.add(fee1);
		fees.add(fee2);
		
		return fees;

	}
}
