package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.EventService;
import services.UserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Event;
import domain.User;
import forms.EventForm;

@Controller
@RequestMapping("/event/user")
public class EventUserController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	public EventUserController() {

		super();

	}

	// Listing------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Event> events;
		User principal;
		boolean showdisjoin = true;
		boolean my = true;

		events = eventService.findAllEventsJoinUser();
		principal = userService.findByPrincipal();

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("my", my);
		result.addObject("showdisjoin", showdisjoin);
		result.addObject("principal", principal);
		result.addObject("requestURI", "event/user/list.do");

		return result;

	}

	@RequestMapping(value = "/listOtherEvents", method = RequestMethod.GET)
	public ModelAndView listOtherEvents() {

		ModelAndView result;
		Collection<Event> events;
		User principal;
		Date actualDate;
		Collection<Event> eventsToRemove;
		boolean showJoin = true;
		boolean remnant = true;

		actualDate = new Date();
		eventsToRemove = new ArrayList<Event>();
		events = eventService.findAllOtherEvents();
		principal = userService.findByPrincipal();
		
		for(Event e: events){
			if(e.getStartMoment().before(actualDate)){
				eventsToRemove.add(e);
			}
		}
		
		events.removeAll(eventsToRemove);

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("remnant", remnant);
		result.addObject("principal", principal);
		result.addObject("showJoin", showJoin);
		result.addObject("requestURI", "event/user/listOthersEvents.do");
		result.addObject("userEvents", principal.getEvents());

		return result;

	}
	
	@RequestMapping(value = "/listFootballEvents", method = RequestMethod.GET)
	public ModelAndView listFootballEvents() {

		ModelAndView result;
		Collection<Event> events;
		User principal;
		Date actualDate;
		Collection<Event> eventsToRemove;
		boolean showJoin = true;
		boolean football = true;

		actualDate = new Date();
		eventsToRemove = new ArrayList<Event>();
		events = eventService.findAllFootballEvents();
		principal = userService.findByPrincipal();
		
		for(Event e: events){
			if(e.getStartMoment().before(actualDate)){
				eventsToRemove.add(e);
			}
		}
		
		events.removeAll(eventsToRemove);

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("football", football);
		result.addObject("principal", principal);
		result.addObject("showJoin", showJoin);
		result.addObject("requestURI", "event/user/listFootballEvents.do");
		result.addObject("userEvents", principal.getEvents());

		return result;

	}
	
	@RequestMapping(value = "/listTennisEvents", method = RequestMethod.GET)
	public ModelAndView listTennisEvents() {

		ModelAndView result;
		Collection<Event> events;
		User principal;
		Date actualDate;
		Collection<Event> eventsToRemove;
		boolean showJoin = true;
		boolean tennis = true;
		
		actualDate = new Date();
		eventsToRemove = new ArrayList<Event>();
		events = eventService.findAllTennisEvents();
		principal = userService.findByPrincipal();
		
		for(Event e: events){
			if(e.getStartMoment().before(actualDate)){
				eventsToRemove.add(e);
			}
		}
		
		events.removeAll(eventsToRemove);

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("tennis", tennis);
		result.addObject("principal", principal);
		result.addObject("showJoin", showJoin);
		result.addObject("requestURI", "event/user/listTennisEvents.do");
		result.addObject("userEvents", principal.getEvents());

		return result;

	}
	
	@RequestMapping(value = "/listPaddleEvents", method = RequestMethod.GET)
	public ModelAndView listPaddleEvents() {

		ModelAndView result;
		Collection<Event> events;
		User principal;
		Date actualDate;
		Collection<Event> eventsToRemove;
		boolean showJoin = true;
		boolean paddle = true;

		actualDate = new Date();
		eventsToRemove = new ArrayList<Event>();
		events = eventService.findAllPaddleEvents();
		principal = userService.findByPrincipal();
		
		for(Event e: events){
			if(e.getStartMoment().before(actualDate)){
				eventsToRemove.add(e);
			}
		}
		
		events.removeAll(eventsToRemove);

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("paddle", paddle);
		result.addObject("principal", principal);
		result.addObject("showJoin", showJoin);
		result.addObject("requestURI", "event/user/listPaddleEvents.do");
		result.addObject("userEvents", principal.getEvents());

		return result;

	}

	// Display-----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int eventId) {

		ModelAndView result;
		Event event;
		EventForm eventForm;
		Collection<User> users;
		Actor actor;
		Boolean estoyApuntado = false;
		Date currentDate;

		event = eventService.findOne(eventId);
		eventForm = eventService.construct(event);
		users = userService.findAllUsersByEventId(eventId);
		actor = actorService.findByPrincipal();
		currentDate = new Date();

		if (event.getUsers().contains(actor)) {
			estoyApuntado = true;
		}
		result = new ModelAndView("event/display");

		result.addObject("eventForm", eventForm);
		result.addObject("users", users);
		result.addObject("creationMoment", event.getCreationMoment());
		result.addObject("estoyApuntado", estoyApuntado);
		Date today = new Date(System.currentTimeMillis());
		Date finish = eventForm.getFinishMoment();
		result.addObject("today", today);
		result.addObject("finish", finish);
		int miId = actor.getId();
		result.addObject("miId", miId);

		if (actor instanceof User) {
			User user = (User) actor;
			result.addObject("user", user);
		}
		
		result.addObject("currentDate", currentDate);

		return result;

	}

	// Creation---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Event event;
		EventForm eventForm;

		event = eventService.create();
		eventForm = eventService.construct(event);

		result = createEditModelAndView(eventForm);
		result.addObject("requestURI", "event/user/edit.do");

		return result;

	}

	// Edition---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int eventId) {
		ModelAndView result;
		Event event;
		EventForm eventForm;
		Collection<User> users;
		Date currentDate;

		event = eventService.findOneToEdit(eventId);
		users = userService.findAllUsersByEventId(eventId);
		eventForm = eventService.construct(event);
		currentDate = new Date();

		result = createEditModelAndView(eventForm);

		result.addObject("users", users);
		result.addObject("currentDate", currentDate);

		return result;
	}

	// Save-----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEU")
	public ModelAndView save(@Valid EventForm eventForm, BindingResult binding) {

		ModelAndView result;
		Event event;

		if (binding.hasErrors()) {
			result = createEditModelAndView(eventForm);
		} else {
			try {

				event = eventService.reconstruct(eventForm);

				eventService.save(event);

				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				if(oops instanceof IllegalArgumentException){
					result = createEditModelAndView(eventForm, "event.commit.maxEvent");
					
				}else{
					result = createEditModelAndView(eventForm, "event.commit.error");
				}				
			}
		}

		return result;

	}

	// Delete---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteEU")
	public ModelAndView delete(@Valid EventForm eventForm, BindingResult binding) {

		ModelAndView result;
		Event event;
		try {

			event = eventService.findOne(eventForm.getId());
			
			eventService.checkPrincipalByActor(event);
			
			eventService.delete(event);

			result = new ModelAndView("redirect:list.do");

		} catch (Throwable oops) {
			result = createEditModelAndView(eventForm, "event.commit.error");
		}
		
		return result;

	}

	// Join a
	// Event------------------------------------------------------------------
	@RequestMapping(value = "/joinEvent", method = RequestMethod.GET)
	public ModelAndView joinEvent(@RequestParam int eventId) {

		ModelAndView result;
		Event event;
		Collection<Event> events;
		boolean my = true;

		event = eventService.findOne(eventId);
		eventService.joinEvent(event);
		events = eventService.findAllEventsJoinUser();

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("my", my);
		result.addObject("requestURI", "event/user/list.do");

		return result;

	}

	// DisJoin a
	// Event------------------------------------------------------------------
	@RequestMapping(value = "/disjoinEvent", method = RequestMethod.GET)
	public ModelAndView DisjoinEvent(@RequestParam int eventId) {

		ModelAndView result;
		Event event;
		Collection<Event> events;
		User principal;
		Boolean showdisjoin = true;
		boolean my = true;
		

		event = eventService.findOne(eventId);
		eventService.DisjoinEvent(event);
		events = eventService.findAllEventsJoinUser();
		principal = userService.findByPrincipal();

		result = new ModelAndView("event/list");

		result.addObject("events", events);
		result.addObject("my", my);
		result.addObject("principal", principal);
		result.addObject("showdisjoin", showdisjoin);
		result.addObject("requestURI", "event/user/list.do");

		return result;

	}

	// Ancillary methods---------------------------------------------------

	protected ModelAndView createEditModelAndView(EventForm eventForm) {

		ModelAndView result;

		result = createEditModelAndView(eventForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(EventForm eventForm,
			String message) {

		ModelAndView result;
		Collection<String> sports;
		Collection<String> places;

		sports = eventService.sports();
		places = eventService.places();
		result = new ModelAndView("event/edit");

		result.addObject("eventForm", eventForm);
		result.addObject("sports", sports);
		result.addObject("places", places);
		result.addObject("message", message);

		return result;
	}

}