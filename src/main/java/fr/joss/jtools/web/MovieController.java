package fr.joss.jtools.web;


import fr.joss.jtools.domain.Movie;
import fr.joss.jtools.service.MovieService;
import fr.joss.jtools.util.constants.MovieUsers;
import fr.joss.jtools.util.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur gérant les écrans relatifs à l'entité {@link Movie}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/movie")
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("movie_list");
        return mv.addObject("users", MovieUsers.values());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Movie> list() {
        return movieService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute Movie movie) {
        boolean isNew = movie.getId() == null;
        Movie mov = movieService.save(movie);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info(isNew ? "{} a ajouté le film {}" : "{} a modifié le film {}", username, mov.getTitle());
        String alertMsg = isNew ? "Film '" + mov.getTitle() + "' ajouté." : "Film '" + mov.getTitle() + "' modifié.";
        return ResponseMessage.getSuccessMessage(alertMsg, mov);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        Movie movie = movieService.findOne(id);
        movieService.delete(movie);
        return ResponseMessage.getSuccessMessage("Film '" + movie.getTitle() + "' supprimé.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie load(@PathVariable Long id) {
        return movieService.findOne(id);
    }

    @RequestMapping(value = "/titleunicity", method = RequestMethod.GET)
    @ResponseBody
    public boolean controlUnicity(@RequestParam(required = false) Long id, @RequestParam String title) {
        return movieService.isTitleAvailable(id, title);
    }

    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage switchSawBy(Long id, String field, boolean changeTo) {
        movieService.switchSaw(id, field, changeTo);
        return ResponseMessage.getSuccessMessage("Film mis à jour");
    }
}
