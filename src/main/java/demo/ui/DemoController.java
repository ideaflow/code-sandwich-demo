package demo.ui;

import demo.api.DemoInput;
import demo.api.DemoResult;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DemoController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("demoInput", new DemoInput("Some Value"));
        return "home";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @RequestMapping(value="/submitDemo", method=RequestMethod.POST)
    public String demoSubmit(@ModelAttribute("demoInput") DemoInput demoInput, Model model) {
        DemoResult result = new DemoResult(demoInput.getInputValue() + " Result");
        model.addAttribute("demoResult", result);
        return "result";
    }

}
