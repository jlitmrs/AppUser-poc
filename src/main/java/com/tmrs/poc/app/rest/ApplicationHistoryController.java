package com.tmrs.poc.app.rest;

import com.tmrs.poc.app.exception.ApiError;
import com.tmrs.poc.app.jpa.entity.AppUser;
import com.tmrs.poc.app.jpa.entity.ApplicationHistory;
import com.tmrs.poc.app.model.HistorySearchModel;
import com.tmrs.poc.app.model.Page;
import com.tmrs.poc.app.service.ApplicationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ApplicationHistoryController", description = "Api's for Application History.  AppUser object has login information.")
@Validated
@RestController
@RequestMapping("/history")
public class ApplicationHistoryController {
    private static final Logger logger = LogManager.getLogger(AppUserController.class);

    @Autowired
    private ApplicationHistoryService historyService;


    @Operation(
            summary = "A list of all Application History.",
            description = "Get a ApplicationHistory List.")
    @ApiResponses({
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
    @GetMapping
    public ResponseEntity<List<ApplicationHistory>> getHistory() {
        List <ApplicationHistory> historyList = historyService.getAllHistory();
        return new ResponseEntity<List<ApplicationHistory>>(historyList, HttpStatus.OK);
    }

    @Operation(
            summary = "A list of all Application History.",
            description = "Get a ApplicationHistory List.")
    @ApiResponses({
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }) })
    @GetMapping("/search")
    public ResponseEntity<Page<ApplicationHistory>> searchHistory(@RequestBody HistorySearchModel model) {
        Page<ApplicationHistory> historyPage = historyService.searchHistory(model);
        return new ResponseEntity<Page<ApplicationHistory>>(historyPage, HttpStatus.OK);
    }

}
