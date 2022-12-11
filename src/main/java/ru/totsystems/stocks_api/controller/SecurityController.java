package ru.totsystems.stocks_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.totsystems.stocks_api.dto.FilesDto;
import ru.totsystems.stocks_api.dto.SecurityDto;
import ru.totsystems.stocks_api.mapper.SecurityMapper;
import ru.totsystems.stocks_api.model.Security;
import ru.totsystems.stocks_api.service.SecurityService;
import ru.totsystems.stocks_api.validate.FileValidator;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;
    private final SecurityMapper mapper;

    @PostMapping("/upload")
    public String addSecurityAndHistory(@RequestParam("collage") MultipartFile[] files, RedirectAttributes attributes) {
        Map<String, FilesDto> validate = FileValidator.validateRequest(files);
        if (validate.containsKey("Please select a file to upload.")) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/security";
        }

        if (validate.containsKey("Wrong file extension.")) {
            attributes.addFlashAttribute("message", "Wrong file extension.");
            return "redirect:/security";
        }
        FilesDto filesDto = validate.get("You successfully uploaded!");

        securityService.addSecurityAndHistory(filesDto);
        attributes.addFlashAttribute("message", "You successfully uploaded!");
        return "redirect:/security";
    }

    @PostMapping("/updateSecurity")
    public String updateSecurity(SecurityDto securityDto) {
        securityService.save(securityDto);
        return "redirect:/security";
    }

    @GetMapping("/uploadForm")
    public ModelAndView getUploadForm() {
        ModelAndView mav = new ModelAndView("upload-files-form");
        mav.addObject("filesDto", new FilesDto());
        return mav;
    }

    @GetMapping
    public ModelAndView getSecurityList() {
        ModelAndView mav = new ModelAndView("security-list");
        List<Security> securityList = securityService.getSecurityList();
        mav.addObject("securities", securityList);
        return mav;
    }

    @GetMapping("/updateForm")
    public ModelAndView getUpdateForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("update-security-form");
        Security security = securityService.getSecurity(id);
        SecurityDto securityDto = mapper.securityToSecurityDto(security);
        securityDto.setSecId(security.getHistory().getSecId());
        mav.addObject("securityDto", securityDto);
        return mav;

    }

    @DeleteMapping("/deleteSecurity")
    public String deleteSecurity(Long id) {
        securityService.deleteSecurity(id);
        return "redirect:/security";
    }
}