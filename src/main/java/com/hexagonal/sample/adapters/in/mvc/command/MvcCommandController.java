package com.hexagonal.sample.adapters.in.mvc.command;

import com.hexagonal.sample.adapters.in.common.dto.CreateUserRequest;
import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.common.dto.UpdateUserRequest;
import com.hexagonal.sample.ports.in.CommandUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mvc/users")
public class MvcCommandController {

    private final CommandUseCase useCase;

    public MvcCommandController(CommandUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public String create(
            @ModelAttribute CreateUserRequest request,
            RedirectAttributes redirectAttributes
    ) {
        String id = useCase.createUser(Mapper.toCreateCommand(request));
        redirectAttributes.addFlashAttribute("message", "User information saved successfully!");
        return "redirect:/mvc/users/search/id?id=" + id;
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute UpdateUserRequest request,
            RedirectAttributes redirectAttributes
    ) {
        useCase.updateUser(Mapper.toUpdateCommand(id, request));
        redirectAttributes.addFlashAttribute(
                "message", "User #" + id + " successfully updated"
        );
        return "redirect:/mvc/users/search/id?id=" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable String id,
            RedirectAttributes redirectAttributes
    ) {
        useCase.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted!");
        return "redirect:/mvc/users";
    }
}
