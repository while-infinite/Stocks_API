package ru.totsystems.stocks_api.controller;

import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.totsystems.stocks_api.dto.HistoryDto;
import ru.totsystems.stocks_api.service.HistoryService;

import java.util.Objects;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/uploadHistory")
    public String addHistory(@ModelAttribute HistoryDto historyDto, RedirectAttributes attributes) {
        MultipartFile file = historyDto.getFile();
        if (file == null || file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/history";
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = Files.getFileExtension(fileName);

        if (!"xml".equals(extension)) {
            attributes.addFlashAttribute("message", "Wrong file extension.");
            return "redirect:/history";
        }

        historyService.addHistory(historyDto.getFile(), historyDto.getSecId());
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        return "redirect:/security";
    }

    @PutMapping("/updateHistory")
    public String updateHistory(@ModelAttribute HistoryDto historyDto, RedirectAttributes attributes) {
        historyService.updateHistory(historyDto);
        attributes.addFlashAttribute("message", "You successfully update " + '!');
        return "redirect:/security";
    }

    @GetMapping("/updateForm")
    public ModelAndView getUpdateForm(@RequestParam String secId) {
        ModelAndView model = new ModelAndView("update-history-form");
        HistoryDto historyDto = new HistoryDto(secId);
        model.addObject("historyDto", historyDto);
        return model;
    }

    @PostMapping("/deleteSecurity")
    public String deleteSecurity(String secId) {
        historyService.deleteHistory(secId);
        return "redirect:/security";
    }
}
