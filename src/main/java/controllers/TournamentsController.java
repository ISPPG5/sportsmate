package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MatchService;
import services.TeamService;
import services.TournamentService;
import services.UserService;
import domain.Tournament;

@Controller
@RequestMapping("/tournament")
public class TournamentsController extends AbstractController {
	// Services----------------------------------------------------------
	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private MatchService matchService;

	@Autowired
	private UserService userService;

	// List--------------------------------------------------------------
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Boolean showJoin = true;
		Collection<Tournament> all;
		Collection<Tournament> userTournaments;
		Date now = new Date(System.currentTimeMillis());
		Collection<Tournament> tournaments = new HashSet<Tournament>();

		all = tournamentService.findAll();

		for (Tournament a : all) {
			if (a.getStartMoment().after(now)) {
				tournaments.add(a);
			}
		}
		if (userService.findByPrincipal() != null) {
			userTournaments = tournamentService.findAllTournamentByPrincipal();
		} else {
			userTournaments = tournaments;
		}
		result = new ModelAndView("tournament/listAll");
		result.addObject("tournaments", tournaments);
		result.addObject("showJoin", showJoin);
		result.addObject("userTournaments", userTournaments);
		result.addObject("requestURI", "tournament/listAll.do");

		return result;
	}
}
