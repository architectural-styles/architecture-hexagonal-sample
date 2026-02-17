package com.hexagonal.sample.adapters.in.mvc.command;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.common.dto.Request;
import com.hexagonal.sample.ports.in.CommandUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mvc/users")
public class ActionsController {

    private final CommandUseCase useCase;

    public ActionsController(CommandUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public String create(
            @ModelAttribute Request request,
            RedirectAttributes redirectAttributes
    ) {
        String id = useCase.createUser(Mapper.toDomain(request));
        redirectAttributes.addFlashAttribute("message", "User information saved successfully!");
        return "redirect:/mvc/users/search/id?id=" + id;
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute Request request,
            RedirectAttributes redirectAttributes
    ) {
        useCase.updateUser(id, Mapper.toDomain(request));
        redirectAttributes.addFlashAttribute(
                "message", "Юзер #" + id + " успешно обновлен"
        );
        return "redirect:/mvc/users/search/id?id=" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        useCase.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Юзер удален!");
        return "redirect:/mvc/users";
    }

}
