package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;		

import services.TeamService;
import services.TournamentService;
import services.UserService;
import domain.Match;
import domain.Team;
import domain.Tournament;
import domain.User;



@Controller
@RequestMapping("/tournament/user/rounds")
public class TournamentUserRoundsController 
{
		
		
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private TournamentService tournamentService;
		
		@Autowired 
		private services.MatchService matchService;
		
		@Autowired
		private TeamService teamService;
	
	
	
	@RequestMapping("/addTeamTest")
	public ModelAndView addTeamTest(){
		
		List<Tournament> tournaments = (List<Tournament>) tournamentService.findAllTournamentsCreatedByUserId();
		
		Tournament tournament=tournaments.get(0);
		
		
		Team team=new Team();
		team.setCaptain(userService.findByPrincipal());
		team.setName("5");
		
		Collection<User> users=new ArrayList<User>();
		users.add(userService.findByPrincipal());
		team.setUsers(users);
		
		Collection<domain.Match> matchs=new ArrayList<domain.Match>();
		
		team.setMatchs(matchs);
		team.setMaxNumber(3);
		
		team.setTournaments(tournaments);
		
		
		
		
		Collection<Team> teams = tournament.getTeams();
		teams.add(team);
		
		tournament.setTeams(teams);
		teamService.save(team);
		tournamentService.save(tournament);
		
		return null;
	}
	
	@RequestMapping("/createFirstRounds")
	public ModelAndView createRounds(@RequestParam int id)
	{
		Tournament tournament=tournamentService.findOne(id);
		
		/* Creamos las rondas segun los teams asociados */
		
		/* regla de negocio, debemos de tener en cuenta si hay un numero impar de equipos */
		
		if((tournament.getTeams().size() % 2) == 0){
			
			Collection<Match> matchs= tournament.getMatches();
			for(int i=0 ; i < tournament.getTeams().size() ; i+=2){
			
			
				
				domain.Match match=new Match();
				match.setCreationMoment(new Date());
				match.setDescription(".");
				match.setFinishMoment(new Date(new Date().getTime()+604800000));
				match.setStartMoment(new Date());
				match.setTitle("Match");
				match.setTournament(tournament);
				List<Team> teams=(List<Team>) tournament.getTeams();
				Collection<Team> teams1=new ArrayList<Team>();
				Team team1=teams.get(i);
				
				Team team2=teams.get(i+1);
				Collection<Match> matchsTeam1=team1.getMatchs();
				Collection<Match> matchsTeam2=team2.getMatchs();
				matchsTeam1.add(match);
				matchsTeam2.add(match);
				team1.setMatchs(matchsTeam1);
				team2.setMatchs(matchsTeam2);
				teams1.add(team1);
				
				teams1.add(team2);
				match.setTeams(teams1);
				match.setDescription(".");
				match.setPlayed(false);
				matchs.add(match);
				
			
				
				
				matchService.save(match);
				tournament.setMatches(matchs);
				tournamentService.save(tournament);
				teamService.save(team1);
				teamService.save(team2);
			
				
				
			}
			
			
			
			
			
			
		}else {
			
			Collection<Match> matchs= tournament.getMatches();
			for(int i=0 ; i < (tournament.getTeams().size()-1) ; i+=2){
				
				
				domain.Match match=new Match();
				match.setCreationMoment(new Date());
				match.setDescription(".");
				match.setFinishMoment(new Date(new Date().getTime()+604800000));
				match.setStartMoment(new Date());
				match.setTitle("Match");
				match.setTournament(tournament);
				List<Team> teams=(List<Team>) tournament.getTeams();
				Collection<Team> teams1=new ArrayList<Team>();
				Team team1=teams.get(i);
				
				Team team2=teams.get(i+1);
				Collection<Match> matchsTeam1=team1.getMatchs();
				Collection<Match> matchsTeam2=team2.getMatchs();
				matchsTeam1.add(match);
				matchsTeam2.add(match);
				team1.setMatchs(matchsTeam1);
				team2.setMatchs(matchsTeam2);
				teams1.add(team1);
				
				teams1.add(team2);
				match.setTeams(teams1);
				match.setDescription(".");
				match.setPlayed(false);
				matchs.add(match);
				
				
				
				
				matchService.save(match);
				tournament.setMatches(matchs);
				tournamentService.save(tournament);
				teamService.save(team1);
				teamService.save(team2);
				

				
					
			}
			
			
			
			
			
		}
		
		
		return manageTournament(id);
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		
		Collection<Tournament> tournaments=tournamentService.findAllTournamentsCreatedByUserId();
		
		if(tournaments== null)
			new Throwable("no tournaments for this user");
		
		ModelAndView result=new ModelAndView("tournament/user/rounds/list");
		
		result.addObject("tournaments", tournaments);
		
		
		return result;
		
		
	}
	
	@RequestMapping("/manageTournament")
	public ModelAndView manageTournament(@RequestParam int id)
	{
		
		Tournament tournament=tournamentService.findOne(id);
		
		ModelAndView result = new ModelAndView("tournament/user/rounds/manageTournament");
		
		result.addObject("tournament",tournament);
		result.addObject("matches", tournament.getMatches());
		
		
		return result;
		
	}
	
	@RequestMapping("/declareWinnerOfMatch.do")
	public ModelAndView declareWinnerofMatch(@RequestParam int id)
	{
		Match match =matchService.findOne(id);
			
		ModelAndView result = null;
		
		
	    result = new ModelAndView("tournament/user/rounds/declareWinnerOfMatch");
	    result.addObject("teams", match.getTeams());
	    result.addObject("match", match);
		
		return result;
		
		
	}
	
	@RequestMapping("/declareWinnerOfMatchId.do")
	public ModelAndView declareWinnerofMatch(@RequestParam int idTeam, @RequestParam int idMatch)
	{
		Match match = matchService.findOne(idMatch);
		Team team = teamService.findOne(idTeam);
		
		
		Team team2 = null;
		
		for (Team t : match.getTeams()){
			
			if(t.getId() !=  team.getId()){
				team2 = t;
			}
		}
		
		
		match.setWinner(team);
		match.setDefeat(team2);
		match.setPlayed(true);
		
		Collection<Match> winners = team.getWinners();
		winners.add(match);
		team.setWinners(winners);
		
		Collection<Match> defeats = team2.getDefeats();
		defeats.add(match);
		team2.setDefeats(defeats);
		
		
		
		
		teamService.save(team);
		teamService.save(team2);
		//matchService.save(match);
		
		
		
		return list();
		
		
	}
	
}
	




