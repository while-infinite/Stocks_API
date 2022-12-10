package ru.totsystems.stocks_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.totsystems.stocks_api.dto.HistoryDto;
import ru.totsystems.stocks_api.model.History;
import ru.totsystems.stocks_api.service.HistoryService;

import java.util.Objects;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/upload")
    public String addHistory(@ModelAttribute HistoryDto historyDto, RedirectAttributes attributes){
        MultipartFile file = historyDto.getFile();
        if(file.isEmpty())
            attributes.addFlashAttribute("message", "Please select a file to upload.");

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        historyService.addHistory(historyDto.getFile(), historyDto.getSecId());
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        return "redirect:/stock";
    }

    @PutMapping("/updateHistory")
    public String updateHistory(@ModelAttribute HistoryDto historyDto, RedirectAttributes attributes){
        historyService.updateHistory(historyDto);
        attributes.addFlashAttribute("message", "You successfully update " + '!');
        return "redirect:/stock";
    }

    @GetMapping("/updateForm")
    public ModelAndView getUpdateForm(@ModelAttribute HistoryDto historyDto){
        ModelAndView model = new ModelAndView("update-history-file");
        model.addObject("historyDto", historyDto);
        return model;
    }
}
