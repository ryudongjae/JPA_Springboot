package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!"); //data라는 이름의 value는 hello!
        return "hello"; //화면 이름 (뒤에 .html이 자동으로 붙는다.)
    }
}
