package io.pivotal.web.controller;

import io.pivotal.web.domain.Order;
import io.pivotal.web.domain.Search;
import io.pivotal.web.service.MarketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PortfolioController {
	private static final Logger logger = LoggerFactory
			.getLogger(PortfolioController.class);
	
	@Autowired
	private MarketService marketService;
	@RequestMapping(value = "/portfolio", method = RequestMethod.GET)
	public String portfolio(Model model) {
		logger.debug("/portfolio");
		model.addAttribute("marketSummary", marketService.getMarketSummary());
		
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("portfolio: User logged in: " + currentUserName);
		    
		    //TODO: add account summary.
		    model.addAttribute("portfolio",marketService.getPortfolio(currentUserName));
		    model.addAttribute("order", new Order());
		}
		
		return "portfolio";
	}
}