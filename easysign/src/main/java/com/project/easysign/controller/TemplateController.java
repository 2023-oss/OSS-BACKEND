package com.project.easysign.controller;

import com.project.easysign.domain.Template;
import com.project.easysign.dto.TemplateDTO;
import com.project.easysign.exception.AuthenticationFailedException;
import com.project.easysign.security.UserPrincipal;
import com.project.easysign.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService templateService;
    @PostMapping("/make")
    public ResponseEntity makeTemplate(@RequestBody String jsonData, @AuthenticationPrincipal UserPrincipal loginUser){
        if(loginUser == null){
            throw new AuthenticationFailedException("로그인 후 사용해주세요.");
        }
        log.info(jsonData);
        String status = templateService.makeTemplate(loginUser.getId(), jsonData);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
    @PostMapping("/update/{templateId}")
    public ResponseEntity updateTemplate(@PathVariable("templateId") Long templateId, @RequestBody String jsonData, @AuthenticationPrincipal UserPrincipal loginUser){
        if(loginUser == null){
            throw new AuthenticationFailedException("로그인 후 사용해주세요.");
        }
        String status = templateService.updateTemplate(templateId, loginUser.getId(), jsonData);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
    // QR로 접속하는 메소드
    @GetMapping(value = "/view/{templateId}",produces = "application/json; charset=utf8")
    public ResponseEntity<TemplateDTO> viewTemplate(@PathVariable("templateId") String templateId){
        TemplateDTO templateDTO = templateService.viewTemplate(templateId);

        return ResponseEntity.status(HttpStatus.OK).body(templateDTO);
    }
}
