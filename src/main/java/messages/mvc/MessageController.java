package messages.mvc;

import javax.validation.Valid;

import messages.data.Message;
import messages.data.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class MessageController {
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping
    public ModelAndView list() {
        Iterable<Message> messages = messageRepository.findAll();
        return new ModelAndView("list", "messages", messages);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Message message = messageRepository.findOne(id);
        return new ModelAndView("view", "message", message);
    }

    @RequestMapping(params="form", method=RequestMethod.GET)
    public String createForm(@ModelAttribute Message message) {
        return "form";
    }

    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView create(@Valid Message message, BindingResult result, RedirectAttributes redirect) {
        if(result.hasErrors()) {
            return new ModelAndView("form");
        }
        message = messageRepository.save(message);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
        return new ModelAndView("redirect:/{message.id}", "message.id", message.getId());
    }
}
